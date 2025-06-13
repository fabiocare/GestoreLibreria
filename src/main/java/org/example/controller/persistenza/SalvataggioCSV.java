package org.example.controller.persistenza;

import org.example.model.Libreria;
import org.example.model.Libro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalvataggioCSV implements SalvataggioStrategy{

    @Override
    public void salva(Libreria libreria, String percorsoFile) throws IOException{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(percorsoFile))){
            for(Libro libro : libreria.getTuttiLibri()){
                String riga = String.format("%s;%s;%s;%s;%s;%d",
                        libro.getTitolo(),
                        libro.getAutore(),
                        libro.getIsbn(),
                        libro.getGenere() != null ? libro.getGenere().toString() : "",
                        libro.getStatoLettura() != null ? libro.getStatoLettura().toString() : "",
                        libro.getValutazione());
                writer.write(riga);
                writer.newLine();
            }
        }
    }

    @Override
    public void carica(String percorsoFile) throws IOException{
        List<Libro> libri = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))){
            String linea;
            while((linea = reader.readLine()) != null){
                String[] campi = linea.split(";");

                Libro.Builder builder = new Libro.Builder(campi[0], campi[1], campi[2]);

                if(campi.length > 3 && !campi[3].trim().isEmpty()){
                    builder.genere(Enum.valueOf(org.example.model.Genere.class, campi[3]));
                }

                if(campi.length > 4 && !campi[4].trim().isEmpty()){
                    builder.statoLettura(Enum.valueOf(org.example.model.StatoLettura.class, campi[4]));
                }

                if(campi.length > 5 && !campi[5].trim().isEmpty()){
                    builder.valutazione(Integer.parseInt(campi[5]));
                }

                Libro libro = builder.build();
                libri.add(libro);
            }
        }
        Libreria libreria = Libreria.getInstance();
        libreria.svuotaLibreria();
        for (Libro l: libri)
            libreria.aggiungiLibro(l);
    }
}
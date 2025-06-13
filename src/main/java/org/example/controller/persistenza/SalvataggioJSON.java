package org.example.controller.persistenza;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Libreria;
import org.example.model.Libro;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class SalvataggioJSON implements SalvataggioStrategy{
    private final Gson gson = new Gson();

    @Override
    public void salva(Libreria libreria, String percorsoFile) throws IOException{
        try(Writer writer = new FileWriter(percorsoFile)){
            gson.toJson(libreria.getTuttiLibri(), writer);
        }
    }

    @Override
    public void carica(String percorsoFile) throws IOException{
        try(Reader reader = new FileReader(percorsoFile)){
            Type listType = new TypeToken<List<Libro>>(){}.getType();
            List<Libro> libri = gson.fromJson(reader, listType);
            if (libri == null) {
                throw new IOException("Formato JSON non valido");
            }
            Libreria libreria = Libreria.getInstance();
            libreria.svuotaLibreria();
            for (Libro l: libri)
                libreria.aggiungiLibro(l);
        }
    }
}
package org.example.controller.query;

import org.example.model.Genere;
import org.example.model.Libreria;
import org.example.model.Libro;
import org.example.model.StatoLettura;

import java.util.Comparator;
import java.util.List;

public class LibreriaQueryFacade {
    private final Libreria libreria;

    public LibreriaQueryFacade(){
        this.libreria = Libreria.getInstance();
    }

    //Op
    public List<Libro> getTuttiLibri(){
        return new GetTuttiLibriQuery(libreria).esegui();
    }

    public List<Libro> cercaPerTitolo(String titolo){
        return new CercaPerTitoloQuery(libreria, titolo).esegui();
    }

    public List<Libro> cercaPerAutore(String autore){
        return new CercaPerAutoreQuery(libreria, autore).esegui();
    }

    public List<Libro> cercaPerIsbn(String isbn){
        return new CercaPerIsbnQuery(libreria, isbn).esegui();
    }

    public List<Libro> filtraPerGenere(Genere genere){
        return new FiltraPerGenereQuery(libreria, genere).esegui();
    }

    public List<Libro> filtraPerStatoLettura(StatoLettura stato){
        return new FiltraPerStatoLetturaQuery(libreria, stato).esegui();
    }

    public List<Libro> ordinaPerValutazione(){
        return new OrdinaLibriQuery(libreria, Comparator.comparing(Libro::getValutazione)).esegui();
    }
    
    public List<Libro> ordinaPerTitolo() {
        return new OrdinaLibriQuery(libreria, Comparator.comparing(Libro::getTitolo)).esegui();
    }

    public List<Libro> ordinaPerAutore(){
        return  new OrdinaLibriQuery(libreria, Comparator.comparing(Libro::getAutore)).esegui();
    }
}

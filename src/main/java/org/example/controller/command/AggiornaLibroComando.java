package org.example.controller.command;

import org.example.model.Libreria;
import org.example.model.Libro;

public class AggiornaLibroComando implements Comando{
    private final Libreria libreria;
    private final String isbn;
    private final Libro nuovoLibro;

    public AggiornaLibroComando(Libreria libreria, Libro nuovoLibro, String isbn){
        this.libreria = libreria;
        this.isbn = isbn;
        this.nuovoLibro = nuovoLibro;
    }

    @Override
    public void esegui(){
        libreria.aggiornaLibro(isbn, nuovoLibro);
    }
}

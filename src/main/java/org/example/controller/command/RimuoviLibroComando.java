package org.example.controller.command;

import org.example.model.Libreria;

public class RimuoviLibroComando implements Comando {
    private final Libreria libreria;
    private final String isbn;

    public RimuoviLibroComando(Libreria libreria, String isbn){
        this.libreria=libreria;
        this.isbn=isbn;
    }
    @Override
    public void esegui() {
        libreria.rimuoviLibro(isbn);
    }
}

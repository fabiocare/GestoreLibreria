package org.example.controller.command;

import org.example.model.Libreria;
import org.example.model.Libro;

public class RimuoviLibroComando implements Comando {
    private final Libreria libreria;
    private final Libro libro;

    public RimuoviLibroComando(Libreria libreria, Libro libro){
        this.libreria=libreria;
        this.libro=libro;
    }
    @Override
    public void esegui() {
        libreria.rimuoviLibro(libro.getIsbn());
    }
}

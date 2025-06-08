package org.example.controller.query;

import org.example.model.Libreria;
import org.example.model.Libro;

import java.util.List;

public class CercaPerAutoreQuery {
    private final Libreria libreria;
    private final String autore;

    public CercaPerAutoreQuery(Libreria libreria, String autore) {
        this.libreria = libreria;
        this.autore = autore;
    }

    public List<Libro> esegui() {
        return libreria.cercaPerAutore(autore);
    }
}

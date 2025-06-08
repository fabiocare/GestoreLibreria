package org.example.controller.query;

import org.example.model.Libreria;
import org.example.model.Libro;

import java.util.List;

public class CercaPerTitoloQuery {
    private final Libreria libreria;
    private final String titolo;

    public CercaPerTitoloQuery(Libreria libreria, String titolo) {
        this.libreria = libreria;
        this.titolo = titolo;
    }

    public List<Libro> esegui() {
        return libreria.cercaPerTitolo(titolo);
    }
}

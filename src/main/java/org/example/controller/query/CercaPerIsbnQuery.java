package org.example.controller.query;

import org.example.model.Libreria;
import org.example.model.Libro;

import java.util.List;

public class CercaPerIsbnQuery {
    private final Libreria libreria;
    private final String isbn;

    public CercaPerIsbnQuery(Libreria libreria, String isbn) {
        this.libreria = libreria;
        this.isbn = isbn;
    }

    public List<Libro> esegui() {
        return libreria.cercaPerIsbn(isbn);
    }
}

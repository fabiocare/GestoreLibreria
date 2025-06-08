package org.example.controller.query;

import org.example.model.Genere;
import org.example.model.Libreria;
import org.example.model.Libro;

import java.util.List;

public class FiltraPerGenereQuery {
    private final Libreria libreria;
    private final Genere genere;

    public FiltraPerGenereQuery(Libreria libreria, Genere genere) {
        this.libreria = libreria;
        this.genere = genere;
    }

    public List<Libro> esegui() {
        return libreria.filtraPerGenere(genere);
    }
}

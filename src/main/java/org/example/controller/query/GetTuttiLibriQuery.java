package org.example.controller.query;

import org.example.model.Libreria;
import org.example.model.Libro;

import java.util.List;

public class GetTuttiLibriQuery {
    private final Libreria libreria;

    public GetTuttiLibriQuery(Libreria libreria){
        this.libreria = libreria;
    }

    public List<Libro> esegui(){
        return libreria.getTuttiLibri();
    }
}

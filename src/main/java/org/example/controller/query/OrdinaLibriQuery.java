package org.example.controller.query;

import org.example.model.Libreria;
import org.example.model.Libro;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrdinaLibriQuery {

    private final Libreria libreria;
    private final Comparator<Libro> comparatore;

    public OrdinaLibriQuery(Libreria libreria, Comparator<Libro> comparatore){
        this.libreria = libreria;
        this.comparatore = comparatore;
    }

    public List<Libro> esegui(){
        return libreria.getTuttiLibri()
                .stream()
                .sorted(comparatore)
                .collect(Collectors.toList());
    }
}

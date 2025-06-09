package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class LibreriaMemento {
    private final List<Libro> stato;

    public LibreriaMemento(List<Libro> statoCorrente){
        this.stato = new ArrayList<>(statoCorrente);
    }
    public List<Libro> getStato(){
        return new ArrayList<>(stato);
    }
}

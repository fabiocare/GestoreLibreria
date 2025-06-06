package org.example.model;

import java.util.*;

public class Libreria {
    private static Libreria instance;
    private final List<Libro> libri;
    private final List<LibreriaObserver> observers;

    private Libreria(){
        this.libri= new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public static Libreria getInstance(){
        if (instance == null){
            instance= new Libreria();
        }
        return instance;
    }

    //Observer pattern
    public void aggiungiObserver(LibreriaObserver observer){
        observers.add(observer);
    }

    public void rimuoviObserver(LibreriaObserver observer){
        observers.remove(observer);
    }

    private void notificaObservers(){
        for(LibreriaObserver observer : observers){
            observer.onLibreriaModificata(Collections.unmodifiableList(libri));
        }
    }

    //Op principali
    public void aggiungiLibro(Libro libro){
        libri.add(libro);
        notificaObservers();
    }

    public void rimuoviLibro(String isbn){
        libri.removeIf(l -> l.getIsbn().equals(isbn));
        notificaObservers();
    }

    public void aggiornaLibro( String isbn, Libro nuovoLibro){
        for(int i= 0; i<libri.size(); i++){
            if(libri.get(i).getIsbn().equals(isbn)){
                libri.set(i, nuovoLibro);
                return;
            }
        }
    }

    public List<Libro> cercaPerTitolo(String titolo){
        return libri.stream().filter(l -> l.getTitolo().toLowerCase().contains(titolo.toLowerCase())).toList();
    }

    public List<Libro> cercaPerAutore(String autore){
        return libri.stream().filter(l -> l.getAutore().toLowerCase().contains(autore.toLowerCase())).toList();
    }

    public List<Libro> filtraPerGenere(Genere g){
        return libri.stream().filter(l -> l.getGenere() == g).toList();
    }

    public List<Libro> filtraPerStatoLettura(StatoLettura s){
        return libri.stream().filter(l -> l.getStatoLettura() == s).toList();
    }

    public List<Libro> getTuttiLibri(){
        return Collections.unmodifiableList(libri);
    }

    public void svuotaLibreria(){
        libri.clear();
        notificaObservers();
    }


}

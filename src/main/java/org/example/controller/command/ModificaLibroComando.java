package org.example.controller.command;

import org.example.model.Libreria;
import org.example.model.StatoLettura;

public class ModificaLibroComando implements Comando{
    private final Libreria libreria;
    private final String isbn;
    private final int nuovaValutazione;
    private final StatoLettura nuovoStato;

    public ModificaLibroComando(Libreria libreria, String isbn, StatoLettura nuovoStato, int nuovoValutazione){
        this.libreria = libreria;
        this.isbn = isbn;
        this.nuovaValutazione = nuovoValutazione;
        this.nuovoStato = nuovoStato;
    }

    @Override
    public void esegui(){
        libreria.modificaLibro(isbn, nuovoStato, nuovaValutazione);
    }
}

package org.example.controller.command;

import org.example.model.Libreria;

public class SvuotaLibreriaComando implements Comando{
    private final Libreria libreria;

    public SvuotaLibreriaComando(Libreria libreria){
        this.libreria = libreria;
    }

    @Override
    public void esegui(){
        libreria.svuotaLibreria();
    }
}

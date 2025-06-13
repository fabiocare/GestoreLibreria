package org.example.controller;

import org.example.controller.command.Comando;
import org.example.model.Libreria;
import org.example.model.LibreriaMemento;

import java.util.Stack;

public class GestoreComandi {
    private final Stack<LibreriaMemento> storicoStati = new Stack<>();
    private final Libreria libreria = Libreria.getInstance();

    public void eseguiComando(Comando comando){
        storicoStati.push(new LibreriaMemento(libreria.getTuttiLibri()));
        comando.esegui();
    }

    public void undo(){
        if(!storicoStati.isEmpty()){
            LibreriaMemento memento = storicoStati.pop();
            libreria.sostituisciLibri(memento.getStato());
        }
    }
}

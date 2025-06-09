package org.example.controller;

import org.example.controller.command.Comando;
import org.example.model.Libreria;
import org.example.model.LibreriaMemento;

import java.util.Stack;

public class GestoreComandi {
    private final Stack<Comando> storicoComandi = new Stack<>();
    private final Stack<LibreriaMemento> storicoStati = new Stack<>();
    private final Libreria libreria = Libreria.getInstance();

    public void eseguiComando(Comando comando){
        storicoStati.push(new LibreriaMemento(libreria.getTuttiLibri()));
        comando.esegui();
        storicoComandi.push(comando);
    }

    public void undo(){
        if(!storicoComandi.isEmpty() && !storicoStati.isEmpty()){
            storicoComandi.pop();
            LibreriaMemento memento = storicoStati.pop();
            libreria.sostituisciLibri(memento.getStato());
        }
    }
}

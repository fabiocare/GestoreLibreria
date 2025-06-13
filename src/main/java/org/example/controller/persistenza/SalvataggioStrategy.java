package org.example.controller.persistenza;

import org.example.model.Libreria;

import java.io.IOException;

public interface SalvataggioStrategy {
    void salva(Libreria libreria, String percorsoFile)throws IOException;
    void carica(String percorsoFile)throws IOException;
}

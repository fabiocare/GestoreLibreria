package org.example.controller.persistenza;

import org.example.model.Libreria;

import java.io.IOException;

public interface SalvataggioStrategy {
    void salva(Libreria libreria, String percorsoFile)throws IOException;
    Libreria carica(String percorsoFile)throws IOException;
}

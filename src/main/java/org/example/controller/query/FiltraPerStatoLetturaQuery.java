package org.example.controller.query;

import org.example.model.Libreria;
import org.example.model.Libro;
import org.example.model.StatoLettura;

import java.util.List;

public class FiltraPerStatoLetturaQuery {
    private final Libreria libreria;
    private final StatoLettura stato;

    public FiltraPerStatoLetturaQuery(Libreria libreria, StatoLettura stato) {
        this.libreria = libreria;
        this.stato = stato;
    }

    public List<Libro> esegui() {
        return libreria.filtraPerStatoLettura(stato);
    }
}

package org.example.model;
import java.util.List;
public interface LibreriaObserver {
    void onLibreriaModificata(List<Libro> nuoviLibri);
}

package org.example.controller;

import org.example.controller.command.AggiungiLibroComando;
import org.example.model.Genere;
import org.example.model.Libreria;
import org.example.model.Libro;
import org.example.model.StatoLettura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestoreComandiTest {
    private Libreria libreria;
    private GestoreComandi gestore;

    @BeforeEach
    void setUp(){
        libreria = Libreria.getInstance();
        libreria.svuotaLibreria();
        gestore = new GestoreComandi();
    }

    //test per l'undo()
    @Test
    void testUndoComando(){
        Libro libro1= new Libro.Builder("Robinson Crusoe", "Daniel Defoe", "9780582541566")
                .genere(Genere.ROMANZO)
                .valutazione(4)
                .statoLettura(StatoLettura.LETTO)
                .build();

        Libro libro2= new Libro.Builder("Il Piccolo Principe", "Antoine de Saint-Exupéry", "9780156013987")
                .genere(Genere.ROMANZO)
                .valutazione(5)
                .statoLettura(StatoLettura.DA_LEGGERE)
                .build();

        gestore.eseguiComando(new AggiungiLibroComando(libreria, libro1));
        gestore.eseguiComando(new AggiungiLibroComando(libreria, libro2));
        assertEquals(2, libreria.getTuttiLibri().size());
        gestore.undo();
        assertEquals(1, libreria.getTuttiLibri().size());
        assertTrue(libreria.getTuttiLibri().contains(libro1));
        assertFalse(libreria.getTuttiLibri().contains(libro2));
    }
}

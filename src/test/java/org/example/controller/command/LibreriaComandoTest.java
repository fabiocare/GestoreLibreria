package org.example.controller.command;

import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibreriaComandoTest {
    private Libreria libreria;

    @BeforeEach
    void setUp() {
        libreria = Libreria.getInstance();
        libreria.svuotaLibreria();
    }

    @Test
    void testAggiuntaLibro() {
        Libro libro = new Libro.Builder("Robinson Crusoe", "Daniel Defoe", "9780582541566")
                .genere(Genere.ROMANZO)
                .valutazione(4)
                .statoLettura(StatoLettura.LETTO)
                .build();

        Comando comando = new AggiungiLibroComando(libreria, libro);
        comando.esegui();

        assertTrue(libreria.getTuttiLibri().contains(libro));
    }

    @Test
    void testRimozioneLibro() {
        Libro libro = new Libro.Builder("Il Piccolo Principe", "Antoine de Saint-Exupéry", "9780156013987")
                .genere(Genere.ROMANZO)
                .valutazione(5)
                .statoLettura(StatoLettura.LETTO)
                .build();
        libreria.aggiungiLibro(libro);

        Comando comando = new RimuoviLibroComando(libreria, libro);
        comando.esegui();

        assertFalse(libreria.getTuttiLibri().contains(libro));
    }

    @Test
    void testAggiornaLibro() {
        Libro libroIniziale = new Libro.Builder("1984", "George Orwell", "9780451524935")
                .genere(Genere.FANTASCIENZA)
                .valutazione(3)
                .statoLettura(StatoLettura.DA_LEGGERE)
                .build();

        libreria.aggiungiLibro(libroIniziale);

        Libro libroAggiornato = new Libro.Builder("1984", "George Orwell", "9780451524935")
                .genere(Genere.FANTASCIENZA)
                .valutazione(5)
                .statoLettura(StatoLettura.LETTO)
                .build();

        Comando comando = new AggiornaLibroComando(libreria, libroAggiornato, libroIniziale.getIsbn());
        comando.esegui();

        Libro trovato = libreria.cercaPerTitolo("1984").getFirst();
        assertEquals(5, trovato.getValutazione());
        assertEquals(StatoLettura.LETTO, trovato.getStatoLettura());
    }
}

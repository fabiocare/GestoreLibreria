package org.example.controller.command;

import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.controller.GestoreComandi;
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

        Comando comando = new RimuoviLibroComando(libreria, "9780156013987");
        comando.esegui();

        assertFalse(libreria.getTuttiLibri().contains(libro));
    }

    @Test
    void testModificaLibro() {
        Libro libroIniziale = new Libro.Builder("1984", "George Orwell", "9780451524935")
                .genere(Genere.FANTASCIENZA)
                .valutazione(3)
                .statoLettura(StatoLettura.DA_LEGGERE)
                .build();

        libreria.aggiungiLibro(libroIniziale);

        Comando comando = new ModificaLibroComando(libreria, libroIniziale.getIsbn(), StatoLettura.LETTO, 5);
        comando.esegui();

        Libro modificato= libreria.cercaPerTitolo("1984").getFirst();
        assertEquals(5, modificato.getValutazione());
        assertEquals(StatoLettura.LETTO, modificato.getStatoLettura());
    }
}

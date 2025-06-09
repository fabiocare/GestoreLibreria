package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LibreriaTest {
    private Libreria libreria;
    private Libro libroBase;

    @BeforeEach
    void setUp(){
        libreria = Libreria.getInstance();
        libreria.svuotaLibreria();

        libroBase= new Libro.Builder("La Metamorfosi", "Franz Kafka", "9780393095333")
                .genere(Genere.FANTASY)
                .valutazione(5)
                .statoLettura(StatoLettura.LETTO)
                .build();

        libreria.aggiungiLibro(libroBase);
    }

    @Test
    void testAggiuntaLibro(){
        List<Libro> libri= libreria.getTuttiLibri();
        assertEquals(1, libri.size());
        assertEquals("La Metamorfosi", libri.getFirst().getTitolo());
    }

    @Test
    void testRimozioneLibro(){
        libreria.rimuoviLibro("9780393095333");
        assertTrue(libreria.getTuttiLibri().isEmpty());
    }

    @Test
    void testAggiornamentoLibro(){
        Libro nuovo= new Libro.Builder("La Metamorfosi", "Franz Kafka", "9780393095333")
                .valutazione(4)
                .genere(Genere.FANTASY)
                .statoLettura(StatoLettura.IN_LETTURA)
                .build();
        libreria.aggiornaLibro("9780393095333", nuovo);
        Libro aggiornato = libreria.getTuttiLibri().getFirst();
        assertEquals(4,aggiornato.getValutazione());
        assertEquals(StatoLettura.IN_LETTURA, aggiornato.getStatoLettura());
    }

    @Test
    void testFiltraPerGenere(){
        List<Libro> trovati = libreria.filtraPerGenere(Genere.FANTASY);
        assertEquals(1, trovati.size());
    }

    @Test
    void testCercaPerTitolo(){
        List<Libro> trovati= libreria.cercaPerTitolo("Le Metamorfosi");
        assertFalse(trovati.isEmpty());
        assertEquals("Franz Kafka", trovati.getFirst().getAutore());
    }

    @Test
    void testObserverNotificato(){
        boolean[] notificato = {false};
        LibreriaObserver observer = nuoviLibri -> notificato[0] = true;
        libreria.aggiungiObserver(observer);
        libreria.aggiungiLibro(new Libro.Builder("Deep Work", "Cal Newport", "9781455563869").build());
        assertTrue(notificato[0]);
    }
}

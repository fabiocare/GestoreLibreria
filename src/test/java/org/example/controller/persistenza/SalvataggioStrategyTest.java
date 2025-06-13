package org.example.controller.persistenza;

import org.example.model.Genere;
import org.example.model.Libreria;
import org.example.model.Libro;
import org.example.model.StatoLettura;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SalvataggioStrategyTest {
    private Libreria libreria;

    @BeforeEach
    void setUp(){
        libreria = Libreria.getInstance();
        libreria.svuotaLibreria();

        libreria.aggiungiLibro(new Libro.Builder("La Metamorfosi", "Franz Kafka", "9780393095333")
                .genere(Genere.ROMANZO)
                .valutazione(5)
                .statoLettura(StatoLettura.LETTO)
                .build());

        libreria.aggiungiLibro(new Libro.Builder("Deep Work", "Cal Newport", "9781455563869")
                .genere(Genere.ALTRO)
                .valutazione(4)
                .statoLettura(StatoLettura.DA_LEGGERE)
                .build());

        libreria.aggiungiLibro(new Libro.Builder("1984", "George Orwell", "9780140817744")
                .genere(Genere.FANTASCIENZA)
                .valutazione(5)
                .statoLettura(StatoLettura.IN_LETTURA)
                .build());

    }

    @Test
    public void testSalvataggioJSON() throws IOException {
        SalvataggioJSON salvataggio = new SalvataggioJSON();
        String nomeFile = "test.json";

        salvataggio.salva(libreria, nomeFile);

        salvataggio.carica(nomeFile);

        assertEquals(3, libreria.getTuttiLibri().size());

        Libro libroCaricato = libreria.getTuttiLibri().getFirst();
        assertEquals("La Metamorfosi", libroCaricato.getTitolo());
        assertEquals("Franz Kafka", libroCaricato.getAutore());
        assertEquals("9780393095333", libroCaricato.getIsbn());
        assertEquals(Genere.ROMANZO, libroCaricato.getGenere());
        assertEquals(StatoLettura.LETTO, libroCaricato.getStatoLettura());
        assertEquals(5, libroCaricato.getValutazione());
    }

    @Test
    void testSalvataggioCSV() throws IOException {
        SalvataggioCSV salvataggio = new SalvataggioCSV();
        String nomeFile = "test.csv";

        salvataggio.salva(libreria, nomeFile);

        salvataggio.carica(nomeFile);

        assertEquals(3, libreria.getTuttiLibri().size());

        Libro libroCaricato = libreria.getTuttiLibri().get(2);
        assertEquals("1984", libroCaricato.getTitolo());
        assertEquals("George Orwell", libroCaricato.getAutore());
        assertEquals("9780140817744", libroCaricato.getIsbn());
        assertEquals(Genere.FANTASCIENZA, libroCaricato.getGenere());
        assertEquals(StatoLettura.IN_LETTURA, libroCaricato.getStatoLettura());
        assertEquals(5, libroCaricato.getValutazione());
    }

    @Test
    void testCaricaFileInesistente_CSV() {
        SalvataggioCSV salvataggio = new SalvataggioCSV();
        String fileInesistente = "file_che_non_esiste.csv";

        assertThrows(IOException.class, () -> salvataggio.carica(fileInesistente));
    }

    @Test
    void testCaricaFileInesistente_JSON() {
        SalvataggioJSON salvataggio = new SalvataggioJSON();
        String fileInesistente = "file_che_non_esiste.json";

        assertThrows(IOException.class, () -> salvataggio.carica(fileInesistente));
    }
}

package org.example.controller.query;

import org.example.model.Genere;
import org.example.model.Libreria;
import org.example.model.Libro;
import org.example.model.StatoLettura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LibreriaQueryFacadeTest {
    private LibreriaQueryFacade facade;

    @BeforeEach
    void setUp() {
        Libreria libreria = Libreria.getInstance();
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

        facade = new LibreriaQueryFacade();
    }
    //ricerca
    @ParameterizedTest
    @CsvSource({
            "La Metamorfosi, Franz Kafka",
            "Deep Work, Cal Newport",
            "1984, George Orwell"
    })
    void testCercaPerTitolo(String titolo, String autoreAtteso) {
        List<Libro> ris = facade.cercaPerTitolo(titolo);
        assertEquals(1, ris.size());
        assertEquals(autoreAtteso, ris.getFirst().getAutore());
    }

    @ParameterizedTest
    @CsvSource({
            "Franz Kafka, La Metamorfosi",
            "Cal Newport, Deep Work",
            "George Orwell, 1984"
    })
    void testCercaPerAutore(String autore, String titoloAtteso) {
        List<Libro> ris = facade.cercaPerAutore(autore);
        assertEquals(1, ris.size());
        assertEquals(titoloAtteso, ris.getFirst().getTitolo());
    }

    @ParameterizedTest
    @EnumSource(Genere.class)
    void testFiltraPerGenere(Genere genere) {
        List<Libro> ris = facade.filtraPerGenere(genere);
        for (Libro libro : ris) {
            assertEquals(genere, libro.getGenere());
        }
    }

    @ParameterizedTest
    @EnumSource(StatoLettura.class)
    void testFiltraPerStatoLettura(StatoLettura statoLettura) {
        List<Libro> ris = facade.filtraPerStatoLettura(statoLettura);
        for (Libro libro : ris) {
            assertEquals(statoLettura, libro.getStatoLettura());
        }
    }

    //ordinamento
    @Test
    void testOrdinamentoPerTitolo() {
        List<Libro> ris = facade.ordinaPerTitolo();
        assertEquals("1984", ris.getFirst().getTitolo());
    }

    @Test
    void testOrdinamentoPerAutore() {
        List<Libro> ris = facade.ordinaPerAutore();
        assertEquals("Cal Newport", ris.getFirst().getAutore());
    }

    @Test
    void testOrdinamentoPerValutazione() {
        List<Libro> ris = facade.ordinaPerValutazione().reversed();
        assertEquals(5, ris.getFirst().getValutazione());
    }
}

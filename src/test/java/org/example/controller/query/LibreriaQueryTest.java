package org.example.controller.query;
import org.example.model.Genere;
import org.example.model.Libreria;
import org.example.model.Libro;
import org.example.model.StatoLettura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LibreriaQueryTest {
    private Libreria libreria;

    @BeforeEach
    void setUp(){
        libreria = Libreria.getInstance();
        libreria.svuotaLibreria();
        libreria.aggiungiLibro(new Libro.Builder("La Metamorfosi", "Franz Kafka", "9780393095333")
                .genere(Genere.FANTASY)
                .valutazione(5)
                .statoLettura(StatoLettura.LETTO)
                .build());

        libreria.aggiungiLibro(new Libro.Builder("Deep Work", "Cal Newport", "9781455563869")
                .genere(Genere.ALTRO)
                .valutazione(4)
                .statoLettura(StatoLettura.DA_LEGGERE)
                .build());
        libreria.aggiungiLibro(new Libro.Builder("1984", "Orwell", "9780140817744")
                .genere(Genere.FANTASCIENZA)
                .valutazione(5)
                .statoLettura(StatoLettura.IN_LETTURA)
                .build());
    }

    @ParameterizedTest
    @CsvSource({
            "Franz Kafka, 1",
            "Cal Newport, 1",
            "Orwell, 1",
            "Primo Levi, 0"
    })
    void testCercaPerAutore(String autore, int expectedSize){
        CercaPerAutoreQuery query = new CercaPerAutoreQuery(libreria, autore);
        List<Libro> ris = query.esegui();
        assertEquals(expectedSize, ris.size());
    }

    @ParameterizedTest
    @CsvSource({
            "La Metamorfosi, 1",
            "Deep Work, 1",
            "Se questo è un uomo, 0"
    })
    void testCercaPerTitolo(String titolo, int expectedSize){
        CercaPerTitoloQuery query = new CercaPerTitoloQuery(libreria, titolo);
        List<Libro> ris = query.esegui();
        assertEquals(expectedSize, ris.size());
    }

    @ParameterizedTest
    @EnumSource(Genere.class)
    void testFiltraPerGenere(Genere genere){
        FiltraPerGenereQuery query = new FiltraPerGenereQuery(libreria, genere);
        List<Libro> ris = query.esegui();
        for(Libro libro : ris){
            assertEquals(genere, libro.getGenere());
        }
    }

    @ParameterizedTest
    @EnumSource(StatoLettura.class)
    void testFiltraPerStatoLettura(StatoLettura s){
        FiltraPerStatoLetturaQuery query = new FiltraPerStatoLetturaQuery(libreria, s);
        List<Libro> ris = query.esegui();
        for(Libro libro : ris){
            assertEquals(s, libro.getStatoLettura());
        }
    }
}

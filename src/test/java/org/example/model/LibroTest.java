package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibroTest {

    @Test
    void testCostruzioneLibroConBuilder() {
        Libro libro = new Libro.Builder("Il Signore degli Anelli", "J.R.R. Tolkien", "9780007525546")
                .genere(Genere.FANTASY)
                .valutazione(5)
                .statoLettura(StatoLettura.LETTO)
                .build();

        assertEquals("Il Signore degli Anelli", libro.getTitolo());
        assertEquals("J.R.R. Tolkien", libro.getAutore());
        assertEquals("9780007525546", libro.getIsbn());
        assertEquals(Genere.FANTASY, libro.getGenere());
        assertEquals(5, libro.getValutazione());
        assertEquals(StatoLettura.LETTO, libro.getStatoLettura());
    }

    @Test
    void testValutazioneFuoriRange() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Libro.Builder("Libro", "Autore", "1234567890")
                        .valutazione(6) // non valido
                        .build()
        );
        assertEquals("La valutazione deve essere tra 0 e 5", exception.getMessage());
    }

    
}

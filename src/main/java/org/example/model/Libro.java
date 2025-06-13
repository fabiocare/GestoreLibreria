package org.example.model;

import java.util.Objects;

public class Libro {
    private final String titolo;
    private final String autore;
    private final String isbn;
    private final Genere genere;
    private int valutazione; // da 1 a 5
    private StatoLettura statoLettura;

    private Libro(Builder builder) {
        this.titolo = builder.titolo;
        this.autore = builder.autore;
        this.isbn = builder.isbn;
        this.genere = builder.genere;
        this.valutazione = builder.valutazione;
        this.statoLettura = builder.statoLettura;
    }

    // Getter
    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public String getIsbn() {
        return isbn;
    }

    public Genere getGenere() {
        return genere;
    }

    public int getValutazione() {
        return valutazione;
    }

    public StatoLettura getStatoLettura() {
        return statoLettura;
    }

    public void setStatoLettura(StatoLettura nuovoStato) {
        this.statoLettura = nuovoStato;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    // Builder static inner class
    public static class Builder {
        private final String titolo;
        private final String autore;
        private final String isbn;

        private Genere genere = Genere.ALTRO;
        private int valutazione = 0;
        private StatoLettura statoLettura = StatoLettura.DA_LEGGERE;

        public Builder(String titolo, String autore, String isbn) {
            this.titolo = Objects.requireNonNull(titolo);
            this.autore = Objects.requireNonNull(autore);
            this.isbn = Objects.requireNonNull(isbn);
        }

        public Builder genere(Genere genere) {
            this.genere = genere;
            return this;
        }

        public Builder valutazione(int valutazione) {
            if (valutazione < 0 || valutazione > 5) {
                throw new IllegalArgumentException("La valutazione deve essere tra 0 e 5");
            }
            this.valutazione = valutazione;
            return this;
        }

        public Builder statoLettura(StatoLettura statoLettura) {
            this.statoLettura = statoLettura;
            return this;
        }

        public Libro build() {
            return new Libro(this);
        }
    }

    @Override
    public String toString() {
        return String.format("%s di %s [%s] - %s, %d★, %s",
                titolo, autore, isbn, genere, valutazione, statoLettura);
    }
}

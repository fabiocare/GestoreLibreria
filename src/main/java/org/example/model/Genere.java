package org.example.model;

public enum Genere {
    ROMANZO("Romanzo"),
    FANTASY("Fantasy"),
    FANTASCIENZA("Fantascienza"),
    STORICO("Storico"),
    BIOGRAFIA("Biografia"),
    SCOLASTICO("Scolastico"),
    ALTRO("Altro");

    private final String descrizione;
    Genere (String descrizione){
        this.descrizione=descrizione;
    }

    public String getDescrizione(){
        return descrizione;
    }
}

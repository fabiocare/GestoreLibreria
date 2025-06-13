package org.example.view;

import org.example.controller.GestoreComandi;
import org.example.controller.command.*;
import org.example.controller.persistenza.SalvataggioCSV;
import org.example.controller.persistenza.SalvataggioJSON;
import org.example.controller.persistenza.SalvataggioStrategy;
import org.example.controller.query.*;
import org.example.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class InterfacciaUtente {
    private final GestoreComandi gestoreComandi = new GestoreComandi();
    private final Scanner scanner = new Scanner(System.in);
    private SalvataggioStrategy salvataggioStrategy;
    private final Libreria libreria = Libreria.getInstance();

    public void avvia() {
        while (true) {
            mostraMenu();
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> aggiungiLibro();
                case "2" -> rimuoviLibro();
                case "3" -> modificaLibro();
                case "4" -> visualizzaLibri();
                case "5" -> cercaLibri();
                case "6" -> salvaLibreria();
                case "7" -> caricaLibreria();
                case "8" -> undo();
                case "9" -> svuotaLibreria();
                case "0" -> {
                    if (libreria.getTuttiLibri().isEmpty()) {
                        System.out.println("Chiusura programma...");
                    } else {
                        System.out.println("La libreria contiene dei libri. Vuoi salvare prima di uscire? (Si/No)");

                        String risposta = scanner.nextLine().trim().toUpperCase();

                        if (risposta.equals("SI")) {
                            salvaLibreria();
                            System.out.println("Libreria salvata. Chiusura programma...");
                        } else {
                            System.out.println("Chiusura programma...");
                        }
                    }
                    return;
                }
                default -> System.out.println("Scelta non valida.");
            }
        }
    }


    private void mostraMenu() {
        System.out.println("""
           ╔══════════════════════════════════════════════╗
           ║          GESTORE LIBRERIA PERSONALE          ║
           ╚══════════════════════════════════════════════╝
           ║    1. Aggiungi un libro                      ║
           ║    2. Rimuovi un libro                       ║
           ║    3. Modifica libro                         ║
           ║    4. Visualizza tutti i libri               ║
           ║    5. Cerca libri                            ║
           ║    6. Salva libreria                         ║
           ║    7. Carica libreria                        ║
           ║    8. Undo ultima operazione                 ║
           ║    9. Svuota libreria                        ║
           ║    0. Esci                                   ║
           ╚══════════════════════════════════════════════╝
            Inserisci la tua scelta:
            """);
    }

    private void aggiungiLibro() {
        System.out.println("Inserisci titolo: ");
        String titolo = scanner.nextLine();
        System.out.println("Inserisci autore: ");
        String autore = scanner.nextLine();
        System.out.println("Inserisci ISBN: ");
        String isbn = scanner.nextLine();
        if (!isbn.matches("\\d+")) {
            System.out.println("ISBN non valido. Inserisci solo numeri.");
            return;
        }
        System.out.print("Inserisci genere: ");
        for (Genere g: Genere.values()) {
            System.out.print(g.ordinal() + 1 + "." + g.name() + " ");
        }
        System.out.println();
        int indexGenere = Integer.parseInt(scanner.nextLine()) - 1;
        Genere genere;
        if (indexGenere < 0 || indexGenere >= Genere.values().length) {
            System.out.println("Genere non valido.");
            return;
        }
        else {
            genere = Genere.values()[indexGenere];
        }

        System.out.println("Inserisci valutazione (1-5): ");
        int valutazione = Integer.parseInt(scanner.nextLine());
        if (valutazione < 0 || valutazione > 5) {
            System.out.println("Valutazione non valida. Deve essere tra 0 e 5.");
            return;
        }

        System.out.print("Inserisci stato di lettura:");
        for (StatoLettura s : StatoLettura.values()) {
            System.out.print(s.ordinal() + 1 + "." + s.name() + " ");
        }
        System.out.println();

        StatoLettura stato;
        int indexStatoLettura = Integer.parseInt(scanner.nextLine()) - 1;

        if (indexStatoLettura < 0 || indexStatoLettura >= StatoLettura.values().length) {
            System.out.println("Stato di lettura non valido.");
            return;
        } else {
            stato = StatoLettura.values()[indexStatoLettura];
        }

        Libro nuovoLibro = new Libro.Builder(titolo, autore, isbn)
                .genere(genere)
                .valutazione(valutazione)
                .statoLettura(stato)
                .build();

        gestoreComandi.eseguiComando(new AggiungiLibroComando(libreria, nuovoLibro));
        System.out.println("Libro aggiunto con successo!");
    }

    private void rimuoviLibro() {
        System.out.println("Inserisci ISBN del libro da rimuovere: ");
        String isbn = scanner.nextLine();
        Libro libro = new LibreriaQueryFacade().cercaPerIsbn(isbn).stream().findFirst().orElse(null);

        if (libro == null) {
            System.out.println("Libro non trovato.");
            return;
        }
        gestoreComandi.eseguiComando(new RimuoviLibroComando(libreria, isbn));
        System.out.println("Libro rimosso con successo.");
    }

    private void modificaLibro() {
        System.out.println("Inserisci ISBN del libro da modificare: ");
        String isbn = scanner.nextLine();
        Libro libro = new LibreriaQueryFacade().cercaPerIsbn(isbn).stream().findFirst().orElse(null);

        if (libro == null) {
            System.out.println("Libro non trovato.");
            return;
        }

        System.out.print("Inserisci stato di lettura:");
        for (StatoLettura s : StatoLettura.values()) {
            System.out.print(s.ordinal() + 1 + "." + s.name() + " ");
        }
        System.out.println();

        StatoLettura stato;
        int indexStatoLettura = Integer.parseInt(scanner.nextLine()) - 1;

        if (indexStatoLettura < 0 || indexStatoLettura >= StatoLettura.values().length) {
            System.out.println("Stato di lettura non valido.");
            return;
        } else {
            stato = StatoLettura.values()[indexStatoLettura];
        }

        System.out.println("Inserisci valutazione (1-5): ");
        int valutazione = Integer.parseInt(scanner.nextLine());
        if (valutazione < 0 || valutazione > 5) {
            System.out.println("Valutazione non valida. Deve essere tra 0 e 5.");
            return;
        }

        gestoreComandi.eseguiComando(new ModificaLibroComando(libreria, isbn, stato, valutazione));
        System.out.println("Libro aggiornato con successo");
    }

    private void visualizzaLibri() {
        List<Libro> libri = new GetTuttiLibriQuery(libreria).esegui();
        if (libri.isEmpty()) {
            System.out.println("La libreria è vuota.");
        } else {
            vistaTabella(libri);
        }
    }

    private void vistaTabella(List<Libro> libri) {
        System.out.printf("%-30s %-20s %-15s %-15s %-15s %-10s%n",
                "Titolo", "Autore", "ISBN", "Genere", "Stato Lettura", "Valutazione");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        for (Libro libro : libri) {
            System.out.printf("%-30s %-20s %-15s %-15s %-15s %-10d%n",
                    libro.getTitolo(),
                    libro.getAutore(),
                    libro.getIsbn(),
                    libro.getGenere() != null ? libro.getGenere() : "-",
                    libro.getStatoLettura() != null ? libro.getStatoLettura() : "-",
                    libro.getValutazione());
        }
    }

    private void cercaLibri() {
        System.out.println("Cerca per: 1. Titolo  2. Autore  3. Genere  4. Stato Lettura  5. Ordina per titolo");
        String scelta = scanner.nextLine();

        switch (scelta) {
            case "1" -> {
                System.out.println("Inserisci titolo: ");
                String titolo = scanner.nextLine();
                vistaTabella(new LibreriaQueryFacade().cercaPerTitolo(titolo));
            }
            case "2" -> {
                System.out.println("Inserisci autore: ");
                String autore = scanner.nextLine();
                vistaTabella(new LibreriaQueryFacade().cercaPerAutore(autore));
            }
            case "3" -> {
                System.out.println("Inserisci genere (ROMANZO, FANTASCIENZA, ALTRO): ");
                Genere genere = Genere.valueOf(scanner.nextLine().toUpperCase());
                vistaTabella(new LibreriaQueryFacade().filtraPerGenere(genere));
            }
            case "4" -> {
                System.out.println("Inserisci stato lettura (DA_LEGGERE, IN_LETTURA, LETTO): ");
                StatoLettura stato = StatoLettura.valueOf(scanner.nextLine().toUpperCase());
                vistaTabella(new LibreriaQueryFacade().filtraPerStatoLettura(stato));
            }
            case "5" -> vistaTabella(new LibreriaQueryFacade().ordinaPerTitolo());
            default -> System.out.println("Scelta non valida.");
        }
    }

    private void salvaLibreria() {
        System.out.println("Scegli formato di salvataggio (CSV/JSON): ");
        String formato = scanner.nextLine().trim().toUpperCase();

        if (formato.equals("CSV")) {
            salvataggioStrategy = new SalvataggioCSV();
        } else if (formato.equals("JSON")) {
            salvataggioStrategy = new SalvataggioJSON();
        } else {
            System.out.println("Formato non valido.");
            return;
        }

        System.out.println("Inserisci nome del file: ");
        String nomeFile = scanner.nextLine();

        try {
            salvataggioStrategy.salva(libreria, nomeFile);
            System.out.println("Salvataggio completato.");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    private void caricaLibreria() {
        System.out.println("Scegli formato di caricamento (CSV/JSON): ");
        String formato = scanner.nextLine().trim().toUpperCase();

        if (formato.equals("CSV")) {
            salvataggioStrategy = new SalvataggioCSV();
        } else if (formato.equals("JSON")) {
            salvataggioStrategy = new SalvataggioJSON();
        } else {
            System.out.println("Formato non valido.");
            return;
        }

        System.out.println("Inserisci nome del file: ");
        String nomeFile = scanner.nextLine();

        try {
            salvataggioStrategy.carica(nomeFile);
            System.out.println("Caricamento completato.");
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento: " + e.getMessage());
        }
    }


    private void undo() {
        gestoreComandi.undo();
        System.out.println("Ultima operazione annullata.");
    }

    private void svuotaLibreria() {
        gestoreComandi.eseguiComando(new SvuotaLibreriaComando(libreria));
        System.out.println("Libreria svuotata.");
    }

    public static void main(String[] args) {
        new InterfacciaUtente().avvia();
    }
}

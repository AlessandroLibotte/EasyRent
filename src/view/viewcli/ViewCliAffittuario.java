package view.viewcli;

import bean.AnnuncioBean;
import bean.PrenotazioneBean;
import control.AnnuncioController;
import control.PrenotazioneController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeParseException;
import java.util.Arrays;


public class ViewCliAffittuario {

    boolean quit;
    BufferedReader br;
    PrenotazioneController pc = PrenotazioneController.getInstance();

    public ViewCliAffittuario() {
        quit = false;
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public boolean mainMenu() throws IOException {

        while(!quit) {

            Printer.printMsgln("Welcome to EasyRent Affittuario");
            Printer.printMsgln("\t1) Cerca annuncio");
            Printer.printMsgln("\t2) Pagina Profilo");
            Printer.printMsgln("\t3) Visualizza Prenotazioni");
            Printer.printMsgln("\t4) Log off");
            Printer.printMsgln("\t5) Quit");

            Printer.printMsg(": ");
            String action = br.readLine();

            switch(action) {
                case "1":
                    searchMenu();
                    break;
                case "4":
                    return false;
                case "5":
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    public void searchMenu() throws IOException {

        String localita = "";
        String startDate = "";
        String endDate = "";
        int numOspiti = 1;
        boolean[] servizi = new boolean[0];

        while(!quit) {

            Printer.printMsgln("Inserisci parametri di ricerca");
            Printer.printMsgln("\t1) Località ["+ localita+ "]");
            Printer.printMsgln("\t2) Data inizio soggiorno ["+startDate+"]");
            Printer.printMsgln("\t3) Data fine soggiorno ["+ endDate+"]");
            Printer.printMsgln("\t4) Numero ospiti ["+numOspiti+"]");
            Printer.printMsgln("\t5) Servizi");
            Printer.printMsgln("\t6) Cerca");
            Printer.printMsgln("\t7) Annulla");

            Printer.printMsg(": ");
            String action = br.readLine();

            switch(action) {
                case "1":
                    Printer.printMsgln("Inserisci Località (Città o Stato)");
                    Printer.printMsg("\t: ");
                    localita = br.readLine();
                    break;
                case "2":
                    Printer.printMsgln("Inserisci Data inizio soggiorno (Formato: gg/MM/aaaa)");
                    Printer.printMsg("\t: ");
                    startDate = br.readLine();
                    break;
                case "3":
                    Printer.printMsgln("Inserisci Data fine soggiorno (Formato: gg/MM/aaaa)");
                    Printer.printMsg("\t: ");
                    endDate = br.readLine();
                    break;
                case "4":
                    Printer.printMsgln("Inserisci Numero ospiti");
                    Printer.printMsg("\t: ");
                    numOspiti = Integer.parseInt(br.readLine());
                    break;
                case "5":
                    Printer.printMsgln("Inserisci Servizi");
                    Printer.printMsg("\t: ");
                    break;
                case "6":
                    try {
                        searchResultsPage(pc.searchAnnunci(new PrenotazioneBean(localita, startDate, endDate, numOspiti)));
                    } catch (DateTimeParseException e){
                        Printer.printMsgln("Data inserita non valida");
                    }
                    break;
                case "7":
                    return;
            }

        }

    }

    public void searchResultsPage(PrenotazioneBean bean) throws IOException {

        while (!quit) {

            Printer.printMsgln("Risultati ricerca");
            int i = 1;
            for (String titolo : bean.getSearchResults()) {
                Printer.printMsgln("\t" + i + ") " + titolo);
                i++;
            }
            Printer.printMsgln("\t" + i + ") Back");

            Printer.printMsg(": ");
            int action = Integer.parseInt(br.readLine());

            if (action < 0 || action > i) continue;

            if (action == i) return;

            paginaAnnuncio(bean, bean.getSearchResults().get(action-1));
        }
    }

    public void paginaAnnuncio(PrenotazioneBean bean, String titolo) throws IOException {

        AnnuncioBean ann = AnnuncioController.getInstance().getAnnuncio(new AnnuncioBean(titolo));

        while(!quit) {

            Printer.printMsgln("Pagina Annuncio");
            Printer.printMsgln("\tTitolo: " + ann.getTitolo());
            Printer.printMsgln("\tIndirizzo: " + ann.getIndirizzo());
            Printer.printMsgln("\tDescrizione: " + ann.getDescrizione());
            Printer.printMsgln("\tServizi: " + Arrays.toString(ann.getServizi()));
            Printer.printMsgln("1) Prenota");
            Printer.printMsgln("2) Back");

            Printer.printMsg(": ");
            String action = br.readLine();

            switch (action) {
                case "1":
                    pc.Prenota(ann, bean);
                    break;
                case "2":
                    return;
                default:
                    break;
            }
        }
    }

}

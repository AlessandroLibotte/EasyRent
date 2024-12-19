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

import static view.viewcli.ViewCliUtils.dynamicMenu;


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
                case "3":
                    menuPrenotazioni();
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

    public void menuPrenotazioni() throws IOException {

        PrenotazioneBean bean = pc.getPrenotazioni();

        while(!quit) {

            Printer.printMsgln("Hai prenotazioni per i seguenti annunci:");

            int action = dynamicMenu(bean.getSearchResults());

            if (action < 0 || action > bean.getSearchResults().size()+1) continue;

            if (action == bean.getSearchResults().size()+1) return;

            paginaPrenotazione(bean.getSearchResults().get(action-1));
            return;

        }

    }

    public void paginaPrenotazione(String titolo) throws IOException {

        PrenotazioneBean pb = pc.getPrenotazioneInfo(new AnnuncioBean(titolo));

        Printer.printMsgln("Dettagli della prenotazione per l'annuncio " + titolo);
        Printer.printMsgln("\tData inizio soggiorno: " + pb.getStartDate());
        Printer.printMsgln("\tData fine soggiorno: " + pb.getEndDate());
        Printer.printMsgln("\tNumero ospiti: " + pb.getNumOspiti());
        Printer.printMsgln("1) Pagina dell'annuncio");
        Printer.printMsgln("2) Back");
        Printer.printMsg(": ");

        String action = br.readLine();

        switch(action) {
            case "1":
                paginaAnnuncio(null, titolo);
                break;
            case "2":
                return;
            default:
                break;
        }

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
            Printer.printMsgln("\t7) Back");

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

            int action = dynamicMenu(bean.getSearchResults());

            if (action < 0 || action > bean.getSearchResults().size()+1) continue;

            if (action == bean.getSearchResults().size()+1) return;

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
            if(bean != null) Printer.printMsgln("1) Prenota");
            Printer.printMsgln("2) Back");

            Printer.printMsg(": ");
            String action = br.readLine();

            switch (action) {
                case "1":
                    if(bean!= null) {
                        pc.prenota(ann, bean);
                        return;
                    } else break;
                case "2":
                    return;
                default:
                    break;
            }
        }
    }

}

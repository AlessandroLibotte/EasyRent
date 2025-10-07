package main.view.viewcli;

import main.bean.AnnuncioBean;
import main.bean.AnnuncioResultBean;
import main.bean.PrenotazioneBean;
import main.control.AnnuncioController;
import main.control.PrenotazioneController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static main.view.viewcli.ViewCliUtils.dynamicMenu;


public class ViewCliAffittuario {

    private final boolean quit;
    private final BufferedReader reader;
    private final PrenotazioneController prenotazioneController;
    private final AnnuncioController annuncioController;
    private final String currentUser;

    public ViewCliAffittuario(String email) {
        quit = false;
        reader = new BufferedReader(new InputStreamReader(System.in));
        prenotazioneController = new PrenotazioneController();
        annuncioController = new AnnuncioController();
        currentUser = email;
    }

    public boolean mainMenu() throws IOException {

        while(!quit) {

            ViewCliUtils.printMsgln("Welcome to EasyRent Affittuario");
            ViewCliUtils.printMsgln("\t1) Cerca annuncio");
            ViewCliUtils.printMsgln("\t2) Pagina Profilo");
            ViewCliUtils.printMsgln("\t3) Visualizza Prenotazioni");
            ViewCliUtils.printMsgln("\t4) Log off");
            ViewCliUtils.printMsgln("\t5) Quit");

            ViewCliUtils.printMsg(": ");
            String action = reader.readLine();

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

    private void menuPrenotazioni() throws IOException {

        PrenotazioneBean bean = prenotazioneController.getPrenotazioni(new PrenotazioneBean(currentUser));

        while(!quit) {

            ViewCliUtils.printMsgln("Hai prenotazioni per i seguenti annunci:");

            int action = dynamicMenu(bean.getPrenotanti());

            if (action < 0 || action > bean.getPrenotanti().size()+1) continue;

            if (action == bean.getPrenotanti().size()+1) return;

            paginaPrenotazione(bean.getPrenotanti().get(action-1));

        }

    }

    private void paginaPrenotazione(String titolo) throws IOException {

        PrenotazioneBean bean = prenotazioneController.getPrenotazioneInfo(new AnnuncioBean(titolo, currentUser));

        while(!quit) {

            ViewCliUtils.printMsgln("Dettagli della prenotazione per l'annuncio " + titolo);
            ViewCliUtils.printMsgln("\tData inizio soggiorno: " + bean.getStartDate());
            ViewCliUtils.printMsgln("\tData fine soggiorno: " + bean.getEndDate());
            ViewCliUtils.printMsgln("\tNumero ospiti: " + bean.getNumOspiti());
            ViewCliUtils.printMsgln("1) Pagina dell'annuncio");
            ViewCliUtils.printMsgln("2) Back");
            ViewCliUtils.printMsg(": ");

            String action = reader.readLine();

            switch (action) {
                case "1":
                    paginaAnnuncio(null, titolo, null);
                    break;
                case "2":
                    return;
                default:
                    break;
            }
        }

    }

    private void searchMenu() throws IOException {

        String localita = "";
        String startDate = "";
        String endDate = "";
        int numOspiti = 1;

        while(!quit) {

            ViewCliUtils.printMsgln("Inserisci parametri di ricerca");
            ViewCliUtils.printMsgln("\t1) Località ["+ localita+ "]");
            ViewCliUtils.printMsgln("\t2) Data inizio soggiorno ["+startDate+"]");
            ViewCliUtils.printMsgln("\t3) Data fine soggiorno ["+ endDate+"]");
            ViewCliUtils.printMsgln("\t4) Numero ospiti ["+numOspiti+"]");
            ViewCliUtils.printMsgln("\t5) Servizi");
            ViewCliUtils.printMsgln("\t6) Cerca");
            ViewCliUtils.printMsgln("\t7) Back");

            ViewCliUtils.printMsg(": ");
            String action = reader.readLine();

            switch(action) {
                case "1":
                    ViewCliUtils.printMsgln("Inserisci Località (Città o Stato)");
                    ViewCliUtils.printMsg("\t: ");
                    localita = reader.readLine();
                    break;
                case "2":
                    ViewCliUtils.printMsgln("Inserisci Data inizio soggiorno (Formato: gg/MM/aaaa)");
                    ViewCliUtils.printMsg("\t: ");
                    startDate = reader.readLine();
                    break;
                case "3":
                    ViewCliUtils.printMsgln("Inserisci Data fine soggiorno (Formato: gg/MM/aaaa)");
                    ViewCliUtils.printMsg("\t: ");
                    endDate = reader.readLine();
                    break;
                case "4":
                    ViewCliUtils.printMsgln("Inserisci Numero ospiti");
                    ViewCliUtils.printMsg("\t: ");
                    numOspiti = Integer.parseInt(reader.readLine());
                    break;
                case "5":
                    ViewCliUtils.printMsgln("Inserisci Servizi");
                    ViewCliUtils.printMsg("\t: ");
                    break;
                case "6":
                    try {
                        PrenotazioneBean prenBean = new PrenotazioneBean(localita, startDate, endDate, numOspiti);
                        searchResultsPage(annuncioController.searchAnnunci(prenBean), prenBean);
                    } catch (DateTimeParseException e){
                        ViewCliUtils.printMsgln("Data inserita non valida");
                    }
                    break;
                case "7":
                    return;
                default:
                    break;
            }

        }

    }

    private void searchResultsPage(AnnuncioResultBean bean, PrenotazioneBean prenBean) throws IOException {

        while (!quit) {

            ViewCliUtils.printMsgln("Risultati ricerca");

            int action = dynamicMenu(bean.getTitoliAnnunci());

            if (action < 0 || action > bean.getTitoliAnnunci().size()+1) continue;

            if (action == bean.getTitoliAnnunci().size()+1) return;

            paginaAnnuncio(bean, bean.getTitoliAnnunci().get(action-1), prenBean);
        }
    }

    private void paginaAnnuncio(AnnuncioResultBean bean, String titolo, PrenotazioneBean prenBean) throws IOException {

        AnnuncioBean annBean = annuncioController.getAnnuncio(new AnnuncioBean(titolo));

        while(!quit) {

            ViewCliUtils.printMsgln("Pagina Annuncio");
            ViewCliUtils.printMsgln("\tTitolo: " + annBean.getTitolo());
            ViewCliUtils.printMsgln("\tIndirizzo: " + annBean.getIndirizzo());
            ViewCliUtils.printMsgln("\tDescrizione: " + annBean.getDescrizione());
            ViewCliUtils.printMsgln("\tServizi: " + Arrays.toString(annBean.getServizi()));
            if(bean != null) ViewCliUtils.printMsgln("1) Prenota");
            ViewCliUtils.printMsgln("2) Back");

            ViewCliUtils.printMsg(": ");
            String action = reader.readLine();

            switch (action) {
                case "1":
                    if(bean != null) {
                        prenotazioneController.prenota(annBean, prenBean);
                        ViewCliUtils.printMsgln("Prenotazione riuscita");
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

package main.view.viewcli;

import main.bean.AnnuncioBean;
import main.bean.PrenotazioneBean;
import main.control.AnnuncioController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import static main.view.viewcli.ViewCliUtils.dynamicMenu;

public class ViewCliLocatore {

    private final boolean quit;
    private final BufferedReader reader;
    private final AnnuncioController annuncioController;
    private final String currentUser;

    public ViewCliLocatore(String email) {
        quit = false;
        reader = new BufferedReader(new InputStreamReader(System.in));
        annuncioController = new AnnuncioController();
        currentUser = email;
    }

    public boolean mainMenu() throws IOException {

        while(!quit) {

            ViewCliUtils.printMsgln("Welcome to EasyRent Locatore");
            ViewCliUtils.printMsgln("\t1) Visualizza i tuoi annunci");
            ViewCliUtils.printMsgln("\t2) Crea nuovo annuncio");
            ViewCliUtils.printMsgln("\t3) Cerca compagnia");
            ViewCliUtils.printMsgln("\t4) View Profile Info");
            ViewCliUtils.printMsgln("\t5) Log off");
            ViewCliUtils.printMsgln("\t6) Quit");
            ViewCliUtils.printMsg(": ");

            String action = reader.readLine();

            switch(action) {
                case "1":
                    menuAnnunci();
                    break;
                case "2":
                    creaAnnuncioMenu();
                    break;
                case "5":
                    return false;
                case "6":
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    private void creaAnnuncioMenu() throws IOException {

        String titolo = "";
        String indirizzo = "";
        String descrizione = "";
        String servizi = "";
        int maxOspiti = 1;
        double prezzo = 0;


        while(!quit) {

            ViewCliUtils.printMsgln("Crea nuovo Annuncio ");

            ViewCliUtils.printMsgln("\t1) Titolo: " + titolo);
            ViewCliUtils.printMsgln("\t2) Indirizzo: " + indirizzo);
            ViewCliUtils.printMsgln("\t3) Descrizione: " + descrizione);
            ViewCliUtils.printMsgln("\t4) Servizi: " + servizi);
            ViewCliUtils.printMsgln("\t5) Massimo numero ospiti: " + maxOspiti);
            ViewCliUtils.printMsgln("\t6) Prezzo per notte: " + prezzo);

            ViewCliUtils.printMsgln("\t7) Crea");

            ViewCliUtils.printMsgln("\t8) Annulla");
            ViewCliUtils.printMsg(": ");

            String action = reader.readLine();

            switch(action) {
                case "1":
                    ViewCliUtils.printMsgln("Titolo:");
                    ViewCliUtils.printMsg("\t: ");
                    titolo = reader.readLine();
                    break;
                case "2":
                    ViewCliUtils.printMsgln("Indirizzo:");
                    ViewCliUtils.printMsg("\t: ");
                    indirizzo = reader.readLine();
                    break;
                case "3":
                    ViewCliUtils.printMsgln("Descrizione:");
                    ViewCliUtils.printMsg("\t: ");
                    descrizione = reader.readLine();
                    break;
                case "4":
                    ViewCliUtils.printMsgln("Servizi:");
                    ViewCliUtils.printMsg("\t: ");
                    break;
                case "5":
                    ViewCliUtils.printMsgln("Massimo numero ospiti:");
                    ViewCliUtils.printMsg("\t: ");
                    maxOspiti = Integer.parseInt(reader.readLine());
                    break;
                case "6":
                    ViewCliUtils.printMsgln("Prezzo per notte:");
                    ViewCliUtils.printMsg("\t: ");
                    prezzo = Double.parseDouble(reader.readLine());
                    break;
                case "7":

                    AnnuncioBean newAnnuncio = new AnnuncioBean(titolo);

                    newAnnuncio.setOwner(currentUser);
                    newAnnuncio.setIndirizzo(indirizzo);
                    newAnnuncio.setDescrizione(descrizione);
                    newAnnuncio.setServiziString(servizi);
                    newAnnuncio.setMaxOspiti(maxOspiti);
                    newAnnuncio.setPrice(prezzo);

                    creaAnnuncio(newAnnuncio);
                    return;
                case "8":
                    return;
                default:
                    break;
            }
        }
    }

    private void creaAnnuncio(AnnuncioBean annuncioBean) {

        if (annuncioController.creaAnnuncio(annuncioBean)) {
            ViewCliUtils.printMsgln("Annuncio creato");
        } else ViewCliUtils.printMsgln("Annuncio gia esistente");

    }

    private void menuAnnunci() throws IOException {

        while(!quit) {

            AnnuncioBean bean  = new AnnuncioBean();
            bean.setOwner(currentUser);

            ArrayList<String> titles = (ArrayList<String>) annuncioController.getAllAnnunci(bean).getTitoliAnnunci();
            ViewCliUtils.printMsgln("I tuoi Annunci: ");

            int action = dynamicMenu(titles);

            if (action <= 0 || action > titles.size()+1) continue;

            if (action == titles.size()+1) return;

            paginaAnnuncio(titles.get(action-1));

        }
    }

    private void paginaAnnuncio(String titolo) throws IOException {

        AnnuncioBean annuncioBean = annuncioController.getAnnuncio(new AnnuncioBean(titolo));

        while(!quit) {

            ViewCliUtils.printMsgln("Pagina Annuncio");
            ViewCliUtils.printMsgln("\tTitolo: " + annuncioBean.getTitolo());
            ViewCliUtils.printMsgln("\tIndirizzo: " + annuncioBean.getIndirizzo());
            ViewCliUtils.printMsgln("\tDescrizione: " + annuncioBean.getDescrizione());
            ViewCliUtils.printMsgln("\tServizi: " + Arrays.toString(annuncioBean.getServizi()));
            ViewCliUtils.printMsgln("\tMassimo numero ospiti: " + annuncioBean.getMaxOspiti());
            ViewCliUtils.printMsgln("1) Visualizza Prenotazioni");
            ViewCliUtils.printMsgln("2) Elimina annuncio");
            ViewCliUtils.printMsgln("3) Back");

            ViewCliUtils.printMsg(": ");
            String action = reader.readLine();

            switch (action) {
                case "1":
                    menuPrenotazioni(annuncioBean.getTitolo());
                    break;
                case "2":
                    annuncioController.eliminaAnnuncio(annuncioBean);
                    return;
                case "3":
                    return;
                default:
                    break;
            }
        }
    }

    private void menuPrenotazioni(String titolo) throws IOException {

        ArrayList<String> prenotazioni = (ArrayList<String>) annuncioController.getPrenotazioniAnnuncio(new AnnuncioBean(titolo)).getPrenotanti();

        while(!quit) {

            ViewCliUtils.printMsgln("Prenotazioni per l'annuncio " + titolo +": ");

            int action = dynamicMenu(prenotazioni);

            if (action <= 0 || action > prenotazioni.size()+1) continue;

            if (action == prenotazioni.size()+1) return;

            paginaPrenotazione(titolo, prenotazioni.get(action-1));
        }
    }

    private void paginaPrenotazione(String titolo, String prenotante) throws IOException {

        PrenotazioneBean bean = annuncioController.getPrenotazioneInfo(new AnnuncioBean(titolo, prenotante));

        if(bean == null) return;

        while(!quit) {

            ViewCliUtils.printMsgln("Dettagli della prenotazione per l'annuncio " + titolo);
            ViewCliUtils.printMsgln("\tPrenotante: " + prenotante);
            ViewCliUtils.printMsgln("\tData inizio soggiorno: " + bean.getStartDate());
            ViewCliUtils.printMsgln("\tData fine soggiorno: " + bean.getEndDate());
            ViewCliUtils.printMsgln("\tNumero ospiti: " + bean.getNumOspiti());
            ViewCliUtils.printMsgln("1) Back");
            ViewCliUtils.printMsg(": ");

            String action = reader.readLine();

            if (action.equals("1")) {
                return;
            }
        }

    }

}

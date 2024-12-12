package view.viewcli;

import bean.AnnuncioBean;
import control.AnnuncioController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Vector;

public class ViewCliLocatore {

    boolean quit;
    BufferedReader br;
    AnnuncioController ac;

    public ViewCliLocatore() {
        quit = false;
        br = new BufferedReader(new InputStreamReader(System.in));
        ac = AnnuncioController.getInstance();
    }

    public boolean mainMenu() throws IOException {

        while(!quit) {

            Printer.printMsgln("Welcome to EasyRent Locatore");
            Printer.printMsgln("\t1) Visualizza i tuoi annunci");
            Printer.printMsgln("\t2) Crea nuovo annuncio");
            Printer.printMsgln("\t3) Cerca compagnia");
            Printer.printMsgln("\t4) View Profile Info");
            Printer.printMsgln("\t5) Log off");
            Printer.printMsgln("\t6) Quit");
            Printer.printMsg(": ");

            String action = br.readLine();

            switch(action) {
                case "1":
                    menuAnnunci();
                    break;
                case "2":
                    creaAnnuncio();
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

    public void creaAnnuncio() throws IOException {

        String titolo = "";
        String indirizzo = "";
        String descrizione = "";
        boolean[] servizi = new boolean[0];
        
        while(!quit) {

            Printer.printMsgln("Crea nuovo Annuncio ");
            Printer.printMsgln("\t1) Titolo: " + titolo);
            Printer.printMsgln("\t2) Indirizzo: " + indirizzo);
            Printer.printMsgln("\t3) Descrizione: " + descrizione);
            Printer.printMsgln("\t4) Servizi: " + Arrays.toString(servizi));
            Printer.printMsgln("\t5) Crea");
            Printer.printMsgln("\t6) Annulla");
            Printer.printMsg(": ");

            String action = br.readLine();

            switch(action) {
                case "1":
                    Printer.printMsgln("Titolo:");
                    Printer.printMsg("\t:");
                    titolo = br.readLine();
                    break;
                case "2":
                    Printer.printMsgln("Indirizzo:");
                    Printer.printMsg("\t:");
                    indirizzo = br.readLine();
                    break;
                case "3":
                    Printer.printMsgln("Descrizione:");
                    Printer.printMsg("\t:");
                    descrizione = br.readLine();
                    break;
                case "4":
                    Printer.printMsgln("Servizi:");
                    Printer.printMsg("\t:");
                    break;
                case "5":
                    if (ac.creaAnnuncio(new AnnuncioBean(titolo, indirizzo, descrizione, servizi))){
                        Printer.printMsgln("Annuncio creato");
                    } else Printer.printMsgln("Annuncio gia esistente");
                    return;
                case "6":
                    return;
                default:
                    break;
            }
        }

    }

    void menuAnnunci() throws IOException {

        while(!quit) {

            Vector<String> titles = ac.getCurrentUserAnnunci().getAnnunci();
            int i = 1;
            Printer.printMsgln("\tI tuoi Annunci: ");
            for (String t: titles) {
                Printer.printMsgln("\t" + i + ") " + t);
                i++;
            }
            Printer.printMsgln("\t"+ i+ ") Back");

            Printer.printMsg(": ");
            int action = Integer.parseInt(br.readLine());

            if (action <= 0 || action > i) continue;

            if (action == i) return;

            //paginaAnnuncio(titles.get(action));

        }
    }

}
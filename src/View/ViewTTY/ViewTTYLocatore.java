package View.ViewTTY;

import Bean.annuncioBean;
import Bean.loginBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Vector;

public class ViewTTYLocatore {

    boolean quit;
    BufferedReader br;
    annuncioBean ab;
    loginBean lb;

    public ViewTTYLocatore(loginBean lb) {
        this.lb = lb;
        quit = false;
        br = new BufferedReader(new InputStreamReader(System.in));
        ab = new annuncioBean();
    }

    public boolean mainMenu() throws IOException {

        while(!quit) {

            System.out.println("Welcome to EasyRent Locatore" + lb.getUsername());
            System.out.println("\t1) Visualizza i tuoi annunci");
            System.out.println("\t2) Crea nuovo annuncio");
            System.out.println("\t3) Cerca compagnia");
            System.out.println("\t4) View Profile Info");
            System.out.println("\t5) Log off");
            System.out.println("\t6) Quit");

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

        while(!quit) {

            System.out.println("\t1) Titolo: " + ab.getTitolo());
            System.out.println("\t2) Indirizzo: " + ab.getIndirizzo());
            System.out.println("\t3) Descrizione: " + ab.getDescrizione());
            System.out.println("\t4) Servizi: " + Arrays.toString(ab.getServizi()));
            System.out.println("\t5) Crea");
            System.out.println("\t6) Annulla");

            String action = br.readLine();

            switch(action) {
                case "1":
                    System.out.println("Titolo:");
                    System.out.println("\t:");
                    ab.setTitolo(br.readLine());
                    break;
                case "2":
                    System.out.println("Indirizzo:");
                    System.out.println("\t:");
                    ab.setIndirizzo(br.readLine());
                    break;
                case "3":
                    System.out.println("Descrizione:");
                    System.out.println("\t:");
                    ab.setDescrizione(br.readLine());
                    break;
                case "4":
                    System.out.println("Servizi:");
                    System.out.println("\t:");
                    break;
                case "5":
                    ab.creaAnnuncio(lb.getId());
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

            Vector<String> titles = lb.getAnnTitles();
            int i = 1;
            System.out.println("\tI tuoi Annunci: ");
            for (String t: titles) {
                System.out.println("\t" + i + ")" + t);
                i++;
            }
            System.out.println("\t"+ i+ ") Back");

            System.out.print(": ");
            int action = Integer.parseInt(br.readLine());

            if (action <= 0 || action > i) continue;

            if (action == i) return;

            //paginaAnnuncio(titles.get(action));

        }
    }

}

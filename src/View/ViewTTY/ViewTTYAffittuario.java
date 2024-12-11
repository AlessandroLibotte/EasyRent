package View.ViewTTY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ViewTTYAffittuario {

    boolean quit;
    BufferedReader br;

    public ViewTTYAffittuario() {
        quit = false;
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public boolean mainMenu() throws IOException {

        String indirizzo = "Roma";
        boolean[] filtri = new boolean[6];

        while(!quit) {

            System.out.println("Welcome to EasyRent Affittuario");
            System.out.println("\t1) Set search parameters [" + indirizzo + Arrays.toString(filtri) +"]");
            System.out.println("\t2) Search");
            System.out.println("\t3) View Profile Info");
            System.out.println("\t4) Log off");
            System.out.println("\t5) Quit");

            String action = br.readLine();

            switch(action) {
                case "1":
                case "2":
                case "3":
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

    public void cercaAnnuncio(){
        return;
    }
}

package View.ViewTTY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Bean.loginBean;
import Control.LoginController;

public class ViewTTYLogin {

    boolean quit;
    BufferedReader br;
    LoginController lc;

    public ViewTTYLogin() {
        quit = false;
        br = new BufferedReader(new InputStreamReader(System.in));
        lc = LoginController.getInstance();
    }

    public void loginMenu() throws IOException {

        String username = "";
        String password = "";

        while(!quit) {

            Printer.printMsg("EasyRent Login");
            Printer.printMsg("\t1) Enter Username [" + username + "]");
            Printer.printMsg("\t2) Enter Password [" + password + "]");
            Printer.printMsg("\t3) Login");
            Printer.printMsg("\t4) Register");
            Printer.printMsg("\t5) Quit");
            System.out.print(": ");

            String action = br.readLine();

            switch(action) {
                case "1":
                    Printer.printMsg("Enter Username");
                    System.out.print("\t: ");
                    username = br.readLine();
                    break;
                case "2":
                    Printer.printMsg("Enter Password");
                    System.out.print("\t: ");
                    password = br.readLine();
                    break;
                case "3":
                    int val = lc.validate(new loginBean(username, password));
                    if (val == 1){
                        Printer.printMsg("Login Successful Affittuario");
                    }
                    else if (val == 2){
                        Printer.printMsg("Login Successful Locatore");
                    }
                    else Printer.printMsg("Invalid Username or Password");
                    break;
                case "4":
                    registerMenu();
                    break;
                case "5":
                    quit = true;
                    break;
                default:
                    break;
            }

        }

    }

    void registerMenu() throws IOException {

        String nome = "";
        String cognome = "";
        String email = "";
        String password = "";
        String telefono = "";
        int role = 0;

        while(!quit) {

            Printer.printMsg("Register");
            Printer.printMsg("\t1) Enter Name [" + nome + "]");
            Printer.printMsg("\t2) Enter Surname [" + cognome + "]");
            Printer.printMsg("\t3) Enter Email [" + email + "]");
            Printer.printMsg("\t4) Enter Password [" + password + "]");
            Printer.printMsg("\t5) Enter Phone Number [" + telefono + "]");
            System.out.print("\t6) Select Role [");
            switch (role){
                case 0:
                    System.out.print("Affittuario");
                    break;
                case 1:
                    System.out.print("Locatore");
                    break;
            }
            Printer.printMsg("]");
            Printer.printMsg("\t7) Register");
            Printer.printMsg("\t8) Back");

            String action = br.readLine();

            switch(action) {
                case "1":
                    Printer.printMsg("Enter Name");
                    System.out.print("\t: ");
                    nome = br.readLine();
                    break;
                case "2":
                    Printer.printMsg("Enter Surname");
                    System.out.print("\t: ");
                    cognome = br.readLine();
                    break;
                case "3":
                    Printer.printMsg("Enter Email");
                    System.out.print("\t: ");
                    email = br.readLine();
                    break;
                case "4":
                    Printer.printMsg("Enter Password");
                    System.out.print("\t: ");
                    password = br.readLine();
                    break;
                case "5":
                    Printer.printMsg("Enter Phone Number");
                    System.out.print("\t: ");
                    telefono = br.readLine();
                    break;
                case "6":
                    Printer.printMsg("Select Role [0: affittuario, 1: locatore]");
                    System.out.print("\t: ");
                    role = Integer.parseInt(br.readLine());
                    if (role != 0 && role != 1) role = 0;
                    break;
                case "7":
                    if (lc.register(new loginBean(nome, cognome, email, password, telefono, role))) {
                        Printer.printMsg("Registration Successful");
                        return;
                    }
                    else Printer.printMsg("Registration unsuccessful");
                    break;
                case "8":
                    return;
                default:
                    break;
            }
        }
    }

}

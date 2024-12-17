package view.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import bean.LoginBean;
import control.LoginController;

public class ViewCliLogin {

    boolean quit;
    BufferedReader br;
    LoginController lc;

    public ViewCliLogin() {
        quit = false;
        br = new BufferedReader(new InputStreamReader(System.in));
        lc = LoginController.getInstance();
    }

    public void loginMenu() throws IOException {

        String username = "";
        String password = "";

        while(!quit) {

            Printer.printMsgln("EasyRent Login");
            Printer.printMsgln("\t1) Enter Username [" + username + "]");
            Printer.printMsgln("\t2) Enter Password [" + password + "]");
            Printer.printMsgln("\t3) Login");
            Printer.printMsgln("\t4) Register");
            Printer.printMsgln("\t5) Quit");
            Printer.printMsg(": ");

            String action = br.readLine();

            switch(action) {
                case "1":
                    Printer.printMsgln("Enter Username");
                    Printer.printMsg("\t: ");
                    username = br.readLine();
                    break;
                case "2":
                    Printer.printMsgln("Enter Password");
                    Printer.printMsg("\t: ");
                    password = br.readLine();
                    break;
                case "3":
                    int val = lc.validate(new LoginBean(username, password));
                    if (val == 1){
                        Printer.printMsgln("Login Successful Affittuario");
                        ViewCliAffittuario view = new ViewCliAffittuario();
                        quit = view.mainMenu();
                    }
                    else if (val == 2){
                        Printer.printMsgln("Login Successful Locatore");
                        ViewCliLocatore view = new ViewCliLocatore();
                        quit = view.mainMenu();
                    }
                    else Printer.printMsgln("Invalid Username or Password");
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

            Printer.printMsgln("Register");
            Printer.printMsgln("\t1) Enter Email [" + email + "]");
            Printer.printMsgln("\t2) Enter Password [" + password + "]");
            Printer.printMsg("\t3) Select Role [");
            Printer.printMsg(getCurrentRole(role));
            Printer.printMsgln("]");
            Printer.printMsgln("\t4) Personal info (optional)");
            Printer.printMsgln("\t5) Register");
            Printer.printMsgln("\t6) Back");
            Printer.printMsg(": ");

            String action = br.readLine();

            switch(action) {
                case "1":
                    Printer.printMsgln("Enter Email");
                    Printer.printMsg("\t: ");
                    email = br.readLine();
                    break;
                case "2":
                    Printer.printMsgln("Enter Password");
                    Printer.printMsg("\t: ");
                    password = br.readLine();
                    break;
                case "3":
                    Printer.printMsgln("Select Role [0: affittuario, 1: locatore]");
                    Printer.printMsg("\t: ");
                    role = Integer.parseInt(br.readLine());
                    if (role != 0 && role != 1) role = 0;
                    break;
                case "4":
                    String[] personalInfo = registerPersonalDataMenu(nome, cognome, telefono);
                    if (Arrays.equals(personalInfo, new String[0])) break;
                    nome = personalInfo[0];
                    cognome = personalInfo[1];
                    telefono = personalInfo[2];
                    break;
                case "5":
                    if (lc.register(new LoginBean(nome, cognome, email, password, telefono, role))) {
                        Printer.printMsgln("Registration Successful");
                        return;
                    }
                    else Printer.printMsgln("Registration unsuccessful");
                    break;
                case "6":
                    return;
                default:
                    break;
            }
        }
    }

    String[] registerPersonalDataMenu(String nome, String cognome, String telefono) throws IOException {

        while(!quit) {

            Printer.printMsgln("Personal Data");
            Printer.printMsgln("\t1) Enter Name [" + nome + "]");
            Printer.printMsgln("\t2) Enter Surname [" + cognome + "]");
            Printer.printMsgln("\t3) Enter Phone Number [" + telefono + "]");
            Printer.printMsgln("\t4) Confirm");
            Printer.printMsgln("\t5) Back");
            Printer.printMsg(": ");

            String action = br.readLine();

            switch (action) {
                case "1":
                    Printer.printMsgln("Enter Name");
                    Printer.printMsg("\t: ");
                    nome = br.readLine();
                    break;
                case "2":
                    Printer.printMsgln("Enter Surname");
                    Printer.printMsg("\t: ");
                    cognome = br.readLine();
                    break;
                case "3":
                    Printer.printMsgln("Enter Phone Number");
                    Printer.printMsg("\t: ");
                    telefono = br.readLine();
                    break;
                case "4":
                    return new String[]{nome, cognome, telefono};
                case "5":
                    return new String[0];
                default:
                    break;
            }
        }
        return new String[0];
    }

    String getCurrentRole(int role){
        return switch (role) {
            case 0 -> "Affittuario";
            case 1 -> "Locatore";
            default -> "Invalid Role";
        };
    }

}

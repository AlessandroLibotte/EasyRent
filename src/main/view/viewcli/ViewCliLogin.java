package main.view.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import main.bean.LoginBean;
import main.control.LoginController;

public class ViewCliLogin {

    private boolean quit;
    private BufferedReader reader;
    private LoginController loginController;

    public ViewCliLogin() {
        quit = false;
        reader = new BufferedReader(new InputStreamReader(System.in));
        loginController = new LoginController();
    }

    public void loginMenu() throws IOException {

        String email = "";
        String password = "";

        while(!quit) {

            ViewCliUtils.printMsgln("EasyRent Login");
            ViewCliUtils.printMsgln("\t1) Enter Username [" + email + "]");
            ViewCliUtils.printMsgln("\t2) Enter Password [" + password + "]");
            ViewCliUtils.printMsgln("\t3) Login");
            ViewCliUtils.printMsgln("\t4) Register");
            ViewCliUtils.printMsgln("\t5) Quit");
            ViewCliUtils.printMsg(": ");

            String action = reader.readLine();

            if (action == null) return;

            switch(action) {
                case "1":
                    ViewCliUtils.printMsgln("Enter Username");
                    ViewCliUtils.printMsg("\t: ");
                    email = reader.readLine();
                    break;
                case "2":
                    ViewCliUtils.printMsgln("Enter Password");
                    ViewCliUtils.printMsg("\t: ");
                    password = reader.readLine();
                    break;
                case "3":
                    int val = loginController.validate(new LoginBean(email, password));
                    if (val == 1){
                        ViewCliUtils.printMsgln("Login Successful Affittuario");
                        ViewCliAffittuario view = new ViewCliAffittuario(email);
                        quit = view.mainMenu();
                    }
                    else if (val == 2){
                        ViewCliUtils.printMsgln("Login Successful Locatore");
                        ViewCliLocatore view = new ViewCliLocatore(email);
                        quit = view.mainMenu();
                    }
                    else ViewCliUtils.printMsgln("Invalid Username or Password");
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

    private void registerMenu() throws IOException {

        String nome = "";
        String cognome = "";
        String email = "";
        String password = "";
        String telefono = "";
        int role = 0;

        while(!quit) {

            ViewCliUtils.printMsgln("Register");
            ViewCliUtils.printMsgln("\t1) Enter Email [" + email + "]");
            ViewCliUtils.printMsgln("\t2) Enter Password [" + password + "]");
            ViewCliUtils.printMsg("\t3) Select Role [");
            ViewCliUtils.printMsg(getCurrentRole(role));
            ViewCliUtils.printMsgln("]");
            ViewCliUtils.printMsgln("\t4) Personal info (optional)");
            ViewCliUtils.printMsgln("\t5) Register");
            ViewCliUtils.printMsgln("\t6) Back");
            ViewCliUtils.printMsg(": ");

            String action = reader.readLine();

            switch(action) {
                case "1":
                    ViewCliUtils.printMsgln("Enter Email");
                    ViewCliUtils.printMsg("\t: ");
                    email = reader.readLine();
                    break;
                case "2":
                    ViewCliUtils.printMsgln("Enter Password");
                    ViewCliUtils.printMsg("\t: ");
                    password = reader.readLine();
                    break;
                case "3":
                    ViewCliUtils.printMsgln("Select Role [0: affittuario, 1: locatore]");
                    ViewCliUtils.printMsg("\t: ");
                    role = Integer.parseInt(reader.readLine());
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
                    if (loginController.register(new LoginBean(nome, cognome, email, password, telefono, role))) {
                        ViewCliUtils.printMsgln("Registration Successful");
                        return;
                    }
                    else ViewCliUtils.printMsgln("Registration unsuccessful");
                    break;
                case "6":
                    return;
                default:
                    break;
            }
        }
    }

    private String[] registerPersonalDataMenu(String nome, String cognome, String telefono) throws IOException {

        while(!quit) {

            ViewCliUtils.printMsgln("Personal Data");
            ViewCliUtils.printMsgln("\t1) Enter Name [" + nome + "]");
            ViewCliUtils.printMsgln("\t2) Enter Surname [" + cognome + "]");
            ViewCliUtils.printMsgln("\t3) Enter Phone Number [" + telefono + "]");
            ViewCliUtils.printMsgln("\t4) Confirm");
            ViewCliUtils.printMsgln("\t5) Back");
            ViewCliUtils.printMsg(": ");

            String action = reader.readLine();

            switch (action) {
                case "1":
                    ViewCliUtils.printMsgln("Enter Name");
                    ViewCliUtils.printMsg("\t: ");
                    nome = reader.readLine();
                    break;
                case "2":
                    ViewCliUtils.printMsgln("Enter Surname");
                    ViewCliUtils.printMsg("\t: ");
                    cognome = reader.readLine();
                    break;
                case "3":
                    ViewCliUtils.printMsgln("Enter Phone Number");
                    ViewCliUtils.printMsg("\t: ");
                    telefono = reader.readLine();
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

    private String getCurrentRole(int role){
        return switch (role) {
            case 0 -> "Affittuario";
            case 1 -> "Locatore";
            default -> "Invalid Role";
        };
    }

}

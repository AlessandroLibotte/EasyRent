package main.view.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import main.bean.LoginBean;
import main.bean.RegistrationBean;
import main.control.LoginController;
import main.model.RegisterResult;
import main.model.Role;

public class ViewCliLogin {

    private boolean quit;
    private final BufferedReader reader;
    private final LoginController loginController;

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
                    Role result = loginController.validate(new LoginBean(email, password));
                    switch(result) {
                        case AFFITTUARIO:
                            ViewCliUtils.printMsgln("Login Successful Affittuario");
                            ViewCliAffittuario viewAff = new ViewCliAffittuario(email);
                            quit = viewAff.mainMenu();
                            break;
                        case LOCATORE:
                            ViewCliUtils.printMsgln("Login Successful Locatore");
                            ViewCliLocatore viewLoc = new ViewCliLocatore(email);
                            quit = viewLoc.mainMenu();
                            break;
                        case INVALID:
                            ViewCliUtils.printMsgln("Invalid Username or Password");
                            break;
                    }
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
        Role role = Role.INVALID;

        while(!quit) {

            ViewCliUtils.printMsgln("Register");
            ViewCliUtils.printMsgln("\t1) Enter Email [" + email + "]");
            ViewCliUtils.printMsgln("\t2) Enter Password [" + password + "]");
            ViewCliUtils.printMsg("\t3) Select Role [");
            ViewCliUtils.printMsg(role.toString());
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
                    ViewCliUtils.printMsgln("Select Role [affittuario, locatore]");
                    ViewCliUtils.printMsg("\t: ");
                    role = Role.fromString(reader.readLine());
                    break;
                case "4":
                    String[] personalInfo = registerPersonalDataMenu(nome, cognome, telefono);
                    if (Arrays.equals(personalInfo, new String[0])) break;
                    nome = personalInfo[0];
                    cognome = personalInfo[1];
                    telefono = personalInfo[2];
                    break;
                case "5":
                    RegisterResult result = loginController.register(new RegistrationBean(nome, cognome, email, password, telefono, role));
                    switch (result) {
                        case SUCCESS:
                            ViewCliUtils.printMsgln("Registration Successful");
                            return;
                        case INVALID:
                            ViewCliUtils.printMsgln("Registration unsuccessful");
                            break;
                        case EXISTS:
                            ViewCliUtils.printMsgln("User already exists");
                            break;
                    }
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

}

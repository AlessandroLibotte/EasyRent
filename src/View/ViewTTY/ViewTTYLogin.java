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

            System.out.println("EasyRent Login");
            System.out.println("\t1) Enter Username [" + username + "]");
            System.out.println("\t2) Enter Password [" + password + "]");
            System.out.println("\t3) Login");
            System.out.println("\t4) Register");
            System.out.println("\t5) Quit");
            System.out.print(": ");

            String action = br.readLine();

            switch(action) {
                case "1":
                    System.out.println("Enter Username");
                    System.out.print("\t: ");
                    username = br.readLine();
                    break;
                case "2":
                    System.out.println("Enter Password");
                    System.out.print("\t: ");
                    password = br.readLine();
                    break;
                case "3":
                    int val = lc.validate(new loginBean(username, password));
                    if (val == 1){
                        System.out.println("Login Successful Affittuario");
                    }
                    else if (val == 2){
                        System.out.println("Login Successful Locatore");
                    }
                    else System.out.println("Invalid Username or Password");
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

            System.out.println("Register");
            System.out.println("\t1) Enter Name [" + nome + "]");
            System.out.println("\t2) Enter Surname [" + cognome + "]");
            System.out.println("\t3) Enter Email [" + email + "]");
            System.out.println("\t4) Enter Password [" + password + "]");
            System.out.println("\t5) Enter Phone Number [" + telefono + "]");
            System.out.print("\t6) Select Role [");
            switch (role){
                case 0:
                    System.out.print("Affittuario");
                    break;
                case 1:
                    System.out.print("Locatore");
                    break;
            }
            System.out.println("]");
            System.out.println("\t7) Register");
            System.out.println("\t8) Back");

            String action = br.readLine();

            switch(action) {
                case "1":
                    System.out.println("Enter Name");
                    System.out.print("\t: ");
                    nome = br.readLine();
                    break;
                case "2":
                    System.out.println("Enter Surname");
                    System.out.print("\t: ");
                    cognome = br.readLine();
                    break;
                case "3":
                    System.out.println("Enter Email");
                    System.out.print("\t: ");
                    email = br.readLine();
                    break;
                case "4":
                    System.out.println("Enter Password");
                    System.out.print("\t: ");
                    password = br.readLine();
                    break;
                case "5":
                    System.out.println("Enter Phone Number");
                    System.out.print("\t: ");
                    telefono = br.readLine();
                    break;
                case "6":
                    System.out.println("Select Role [0: affittuario, 1: locatore]");
                    System.out.print("\t: ");
                    role = Integer.parseInt(br.readLine());
                    if (role != 0 && role != 1) role = 0;
                    break;
                case "7":
                    if (lc.register(new loginBean(nome, cognome, email, password, telefono, role))) {
                        System.out.println("Registration Successful");
                        return;
                    }
                    else System.out.println("Registration unsuccessful");
                    break;
                case "8":
                    return;
                default:
                    break;
            }
        }

    }

}

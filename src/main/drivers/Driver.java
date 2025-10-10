package main.drivers;

import main.persistence.DaoFactory;
import main.view.viewcli.ViewCliLogin;
import java.io.IOException;

public class Driver {

    public static void main(String[] args) throws IOException {

        DaoFactory.setPersistenceProvider("in memory");

        ViewCliLogin login = new ViewCliLogin();
        login.loginMenu();
    }
}
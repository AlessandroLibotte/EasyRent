package main.drivers;

import main.persistence.PersistenceProvider;
import main.view.viewcli.ViewCliLogin;
import main.persistence.DaoFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Driver {

    public static void main(String[] args) throws IOException {

        setPersistenceProvider("in memory");

        ViewCliLogin login = new ViewCliLogin();
        login.loginMenu();
    }

    private static void setPersistenceProvider(String provider) {
        for (PersistenceProvider p : PersistenceProvider.values()) {
            if (p.getName().equals(provider)) {
                try {
                    DaoFactory.setInstance(p.getDaoFactoryClass().getConstructor().newInstance());
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                        | IllegalAccessException e) {
                    throw new IllegalStateException("Invalid Provider");
                }
                return;
            }
        }
    }
}
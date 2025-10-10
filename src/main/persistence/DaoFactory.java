package main.persistence;

import java.lang.reflect.InvocationTargetException;

public abstract class DaoFactory {

    private static DaoFactory instance = null;

    public static void setInstance(DaoFactory instance) {
        DaoFactory.instance = instance;
    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public abstract UserDao getUserDao();
    public abstract AnnuncioDao getAnnuncioDao();
    public abstract ImmobileDao getImmobileDao();
    public abstract PrenotazioneDao getPrenotazioneDao();

    public static void setPersistenceProvider(String provider) {
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


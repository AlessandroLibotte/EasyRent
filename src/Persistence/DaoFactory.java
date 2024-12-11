package Persistence;

import java.lang.reflect.InvocationTargetException;

public abstract class DaoFactory {

    private static DaoFactory instance = null;
    private static PersistenceProvider persistenceProvider = null;

    public static void setPersistenceProvider(PersistenceProvider provider) {
        persistenceProvider = provider;
    }

    public static DaoFactory getInstance() throws NoImplementationForPersistenceProviderException {
        if (instance == null) {
            try {
                instance = persistenceProvider.getDaoFactoryClass().getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                     InvocationTargetException | NoSuchMethodException | SecurityException e) {
                throw new NoImplementationForPersistenceProviderException(persistenceProvider, e);
            }
        }
        return instance;
    }

    public abstract UserDao getUserDao();
    public abstract AnnuncioDao getAnnuncioDao();
}


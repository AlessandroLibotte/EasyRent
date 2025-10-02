package main.persistence;

import main.persistence.memory.InMemoryDaoFactory;

import java.lang.reflect.InvocationTargetException;

public enum PersistenceProvider {

    IN_MEMORY("in memory", InMemoryDaoFactory.class);

    private final String name;
    private final Class<? extends DaoFactory> daoFactoryClass;

    PersistenceProvider(String name, Class<? extends DaoFactory> daoFactoryClass) {
        this.name = name;
        this.daoFactoryClass = daoFactoryClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends DaoFactory> getDaoFactoryClass() {
        return daoFactoryClass;
    }

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
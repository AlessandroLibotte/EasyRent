package Persistence.InMemory;

import Persistence.DaoFactory;

public class InMemoryDaoFactory extends DaoFactory {

    public InMemoryUserDao getUserDao() {
        return InMemoryUserDao.getInstance();
    }

    public InMemoryAnnuncioDao getAnnuncioDao() {
        return InMemoryAnnuncioDao.getInstance();
    }

}

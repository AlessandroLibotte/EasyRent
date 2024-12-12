package persistence.memory;

import persistence.DaoFactory;

public class InMemoryDaoFactory extends DaoFactory {

    public InMemoryUserDao getUserDao() {
        return InMemoryUserDao.getInstance();
    }

    public InMemoryAnnuncioDao getAnnuncioDao() {
        return InMemoryAnnuncioDao.getInstance();
    }

}

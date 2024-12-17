package persistence.memory;

import persistence.DaoFactory;

public class InMemoryDaoFactory extends DaoFactory {

    public InMemoryUserDao getUserDao() {
        return InMemoryUserDao.getInstance();
    }

    public InMemoryAnnuncioDao getAnnuncioDao() {
        return InMemoryAnnuncioDao.getInstance();
    }

    public InMemoryImmobileDao getImmobileDao() {
        return InMemoryImmobileDao.getInstance();
    }

    public InMemoryPrenotazioneDao getPrenotazioneDao() {
        return InMemoryPrenotazioneDao.getInstance();
    }

}

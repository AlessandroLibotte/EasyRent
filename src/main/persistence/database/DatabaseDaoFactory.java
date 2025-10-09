package main.persistence.database;

import main.persistence.DaoFactory;
import main.persistence.exceptions.DaoFactoryException;

import java.sql.SQLException;

public class DatabaseDaoFactory extends DaoFactory {

    public DatabaseUserDao getUserDao()  {
        try{
            return DatabaseUserDao.getInstance("USERS", "EMAIL");
        } catch (SQLException e) {
            throw new DaoFactoryException(e.getMessage());
        }
    }

    public DatabaseAnnuncioDao getAnnuncioDao() {
        try {
            return DatabaseAnnuncioDao.getInstance("ANNUNCI", "TITOLO");
        } catch (SQLException e) {
            throw new DaoFactoryException(e.getMessage());
        }
    }

    public DatabaseImmobileDao getImmobileDao() {
        try {
            return DatabaseImmobileDao.getInstance("IMMOBILI", "INDIRIZZO");
        } catch (SQLException e) {
            throw new DaoFactoryException(e.getMessage());
        }
    }

    public DatabasePrenotazioneDao getPrenotazioneDao() {
        try {
            return DatabasePrenotazioneDao.getInstance("PRENOTAZIONI", "ID");
        } catch (SQLException e) {
            throw new DaoFactoryException(e.getMessage());
        }

    }
}

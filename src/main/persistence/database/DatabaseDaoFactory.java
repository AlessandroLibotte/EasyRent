package main.persistence.database;

import main.persistence.DaoFactory;

import java.sql.SQLException;

public class DatabaseDaoFactory extends DaoFactory {

    public DatabaseUserDao getUserDao() {
        try {
            return DatabaseUserDao.getInstance("USERS", "EMAIL");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DatabaseAnnuncioDao getAnnuncioDao() {
        try {
            return DatabaseAnnuncioDao.getInstance("ANNUNCI", "TITOLO");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DatabaseImmobileDao getImmobileDao() {
        try {
            return DatabaseImmobileDao.getInstance("IMMOBILI", "INDIRIZZO");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DatabasePrenotazioneDao getPrenotazioneDao() {
        try {
            return DatabasePrenotazioneDao.getInstance("PRENOTAZIONI", "ID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

package main.persistence.memory;

import main.model.Prenotazione;
import main.persistence.PrenotazioneDao;

public class InMemoryPrenotazioneDao extends InMemoryDao<String, Prenotazione> implements PrenotazioneDao {

    private static InMemoryPrenotazioneDao instance;

    private InMemoryPrenotazioneDao() {}

    public static InMemoryPrenotazioneDao getInstance() {
        if (instance == null) {
            instance = new InMemoryPrenotazioneDao();
        }
        return instance;
    }

    public String getKey(Prenotazione prenotazione){
        return prenotazione.getPrenotante();
    }


}

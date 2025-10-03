package main.persistence.memory;

import main.model.Prenotazione;
import main.persistence.PrenotazioneDao;

public class InMemoryPrenotazioneDao extends InMemoryDao<Integer, Prenotazione> implements PrenotazioneDao {

    private static InMemoryPrenotazioneDao instance;

    public static InMemoryPrenotazioneDao getInstance() {
        if (instance == null) {
            instance = new InMemoryPrenotazioneDao();
        }
        return instance;
    }

    public Integer getKey(Prenotazione prenotazione){
        return prenotazione.getId();
    }


}

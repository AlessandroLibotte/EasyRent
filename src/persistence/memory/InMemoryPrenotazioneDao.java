package persistence.memory;

import model.Prenotazione;
import persistence.PrenotazioneDao;

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

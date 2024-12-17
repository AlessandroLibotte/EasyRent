package persistence;

import model.Prenotazione;

public interface PrenotazioneDao extends Dao<String, Prenotazione> {

    Prenotazione create(String prenotante);

}

package main.persistence;

import main.model.Prenotazione;

public interface PrenotazioneDao extends Dao<String, Prenotazione> {

    default Prenotazione create(String prenotante){
        return new Prenotazione(prenotante);
    }

}

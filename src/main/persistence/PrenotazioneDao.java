package main.persistence;

import main.model.Prenotazione;

public interface PrenotazioneDao extends Dao<Integer, Prenotazione> {

    default Prenotazione create(Integer id){
        return new Prenotazione(id);
    }

    default Prenotazione create(String titolo){
        return new Prenotazione(titolo);
    }

}

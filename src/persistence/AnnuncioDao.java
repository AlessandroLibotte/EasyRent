package persistence;

import model.Annuncio;

public interface AnnuncioDao extends Dao<String, Annuncio>{

    default Annuncio create(String titolo){
        return new Annuncio(titolo);
    }
}

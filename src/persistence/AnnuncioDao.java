package persistence;

import model.Annuncio;

public interface AnnuncioDao extends Dao<String, Annuncio>{

    Annuncio create(String titolo);
}

package Persistence;

import Model.Annuncio;

public interface AnnuncioDao extends Dao<String, Annuncio>{

    Annuncio create(String titolo);

}

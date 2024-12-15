package persistence;

import model.Immobile;

public interface ImmobileDao extends Dao<String, Immobile>{

    Immobile create(String indirizzo);

}

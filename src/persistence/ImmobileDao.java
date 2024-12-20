package persistence;

import model.Immobile;

public interface ImmobileDao extends Dao<String, Immobile>{

    default Immobile create(String indirizzo){
        return new Immobile(indirizzo);
    }

}

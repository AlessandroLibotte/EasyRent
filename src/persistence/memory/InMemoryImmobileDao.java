package persistence.memory;

import model.Immobile;
import persistence.ImmobileDao;

public class InMemoryImmobileDao extends InMemoryDao<String, Immobile> implements ImmobileDao {

    private static InMemoryImmobileDao instance;

    private InMemoryImmobileDao() {}

    public static InMemoryImmobileDao getInstance() {
        if (instance == null) {
            instance = new InMemoryImmobileDao();
        }
        return instance;
    }

    public String getKey(Immobile immobile) {
        return immobile.getIndirizzo();
    }

    public Immobile create(String key){
        return new Immobile(key);
    }

}

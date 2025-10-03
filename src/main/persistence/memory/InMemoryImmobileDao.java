package main.persistence.memory;

import main.model.Immobile;
import main.persistence.ImmobileDao;

import java.util.List;

public class InMemoryImmobileDao extends InMemoryDao<String, Immobile> implements ImmobileDao {

    private static InMemoryImmobileDao instance;

    public static InMemoryImmobileDao getInstance() {
        if (instance == null) {
            instance = new InMemoryImmobileDao();
        }
        return instance;
    }

    public String getKey(Immobile immobile) {
        return immobile.getIndirizzo();
    }

    @Override
    public List<Immobile> loadAllWhere(String column, String value) {
        return null;
    }
}

package main.persistence;

import java.util.List;

public interface Dao<K, V> {

    V create(K id);

    V load(K id);
    void store(V entity);
    void delete(K id);
    boolean exists(K id);
    List<V> loadAll();
    List<V> loadAllWhere(String column, String value);

}

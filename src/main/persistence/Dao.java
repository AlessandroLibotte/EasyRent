package main.persistence;

import java.util.List;

public interface Dao<K, E> {

    E create(K id);

    E load(K id);
    void store(E entity);
    void delete(K id);
    boolean exists(K id);
    List<E> loadAll();

}

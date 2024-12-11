package Persistence;

public interface Dao<ID, E> {

    E create(ID id);

    E load(ID id);
    void store(E entity);
    void delete(ID id);
    boolean exists(ID id);

}

package main.persistence.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.persistence.Dao;

public abstract class InMemoryDao<K, V> implements Dao<K, V> {

    private final Map<K, V> memory = new HashMap<>();

    protected void store(K key, V value) {
        memory.put(key, value);
    }

    @Override
    public void delete(K id) {
        memory.remove(id);
    }

    @Override
    public boolean exists(K id) {
        return memory.containsKey(id);
    }

    @Override
    public V load(K id) {
        return memory.get(id);
    }

    @Override
    public void store(V entity) {
        K key = getKey(entity);
        store(key, entity);
    }

    public List<V> loadAll(){
        return new ArrayList<>(memory.values());
    }

    protected abstract K getKey(V value);
}
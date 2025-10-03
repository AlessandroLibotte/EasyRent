package main.persistence.memory;

import main.model.Annuncio;
import main.persistence.AnnuncioDao;

import java.util.List;

public class InMemoryAnnuncioDao extends InMemoryDao<String, Annuncio> implements AnnuncioDao {

        private static InMemoryAnnuncioDao instance;

        public static InMemoryAnnuncioDao getInstance() {
            if(instance == null) {
                instance = new InMemoryAnnuncioDao();
            }
            return instance;
        }

        public String getKey(Annuncio annuncio) {
            return annuncio.getTitolo();
        }

    @Override
    public List<Annuncio> loadAllWhere(String column, String value) {
        return null;
    }
}

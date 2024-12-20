package persistence.memory;

import model.Annuncio;
import persistence.AnnuncioDao;

public class InMemoryAnnuncioDao extends InMemoryDao<String, Annuncio> implements AnnuncioDao {

        private static InMemoryAnnuncioDao instance;

        private InMemoryAnnuncioDao() {}

        public static InMemoryAnnuncioDao getInstance() {
            if(instance == null) {
                instance = new InMemoryAnnuncioDao();
            }
            return instance;
        }

        public String getKey(Annuncio annuncio) {
            return annuncio.getTitolo();
        }



}

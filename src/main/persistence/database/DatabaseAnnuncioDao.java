package main.persistence.database;

import main.model.Annuncio;
import main.persistence.AnnuncioDao;
import main.persistence.DaoFactory;
import main.persistence.ImmobileDao;
import main.persistence.PrenotazioneDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAnnuncioDao extends DatabaseDao<String, Annuncio> implements AnnuncioDao {

    private static DatabaseAnnuncioDao instance;

    public static DatabaseAnnuncioDao getInstance(String tableName, String primaryKeyColumn) throws SQLException {
        if (instance == null) {
            instance = new DatabaseAnnuncioDao(tableName, primaryKeyColumn);
        }
        return instance;
    }

    public DatabaseAnnuncioDao(String tableName, String primaryKeyColumn) throws SQLException {
        super(tableName, primaryKeyColumn);
    }

    @Override
    public String getKey(Annuncio annuncio) {
        return annuncio.getTitolo();
    }

    @Override
    protected Annuncio mapResultSet(ResultSet rs) throws SQLException {

        Annuncio ann = create(rs.getString("titolo"));
        ann.setOwner(rs.getString("owner"));
        ann.setDescrizione(rs.getString("descrizione"));
        ann.setVoto(rs.getInt("voto"));
        ann.setPrezzoPerNotte(rs.getDouble("prezzo"));

        ImmobileDao immDao = DaoFactory.getInstance().getImmobileDao();
        ann.setImmobile(immDao.load(rs.getString("indirizzo_immobile")));

        PrenotazioneDao prenDao =  DaoFactory.getInstance().getPrenotazioneDao();
        ann.setPrenotazioni(prenDao.loadAllWhere("titolo_annuncio", ann.getTitolo()));

        return ann;
    }

    @Override
    protected void setPreparedStatementForStore(PreparedStatement ps, Annuncio entity) throws SQLException {
        ps.setString(1, entity.getTitolo());
        ps.setString(2, entity.getOwner());
        ps.setString(3, entity.getDescrizione());
        ps.setInt(4, entity.getVoto());
        ps.setDouble(5, entity.getPrezzoPerNotte());
        ps.setString(6, entity.getImmobile().getIndirizzo());
    }

    @Override
    protected void insert(Annuncio entity) throws SQLException {
        String sql = "Merge INTO ANNUNCI KEY(TITOLO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            setPreparedStatementForStore(ps, entity);
            ps.executeUpdate();
        }
    }
}

package main.persistence.database;

import main.model.Prenotazione;
import main.persistence.PrenotazioneDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DatabasePrenotazioneDao extends DatabaseDao<String, Prenotazione> implements PrenotazioneDao {

    private static DatabasePrenotazioneDao instance;

    public static DatabasePrenotazioneDao getInstance(String tableName, String primaryKeyColumn) throws SQLException {
        if (instance == null) {
            instance = new DatabasePrenotazioneDao(tableName, primaryKeyColumn);
        }
        return instance;
    }

    public DatabasePrenotazioneDao(String tableName, String primaryKeyColumn) throws SQLException {
        super(tableName, primaryKeyColumn);
    }

    @Override
    public String getKey(Prenotazione prenotazione){
        return prenotazione.getPrenotante();
    }

    @Override
    protected Prenotazione mapResultSet(ResultSet rs) throws SQLException {
        Prenotazione pren = create(rs.getString("prenotnte"));
        pren.setStartDate(LocalDate.parse(rs.getString("startdate")));
        pren.setEndDate(LocalDate.parse(rs.getString("enddate")));
        pren.setNumOspiti(Integer.parseInt(rs.getString("numospiti")));
        return pren;
    }

    @Override
    protected void setPreparedStatementForStore(PreparedStatement ps, Prenotazione entity) throws SQLException {
        ps.setString(1, entity.getPrenotante());
        ps.setString(2, entity.getStartDate().toString());
        ps.setString(3, entity.getEndDate().toString());
        ps.setInt(4, entity.getNumOspiti());
    }

    @Override
    protected void insert(Prenotazione entity) throws SQLException {
        String sql = "MERGE INTO PRENOTAZIONI KEY(PRENOTANTE) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            setPreparedStatementForStore(ps, entity);
            ps.executeUpdate();
        }
    }

}

package main.persistence.database;

import main.model.Immobile;
import main.persistence.ImmobileDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseImmobileDao extends DatabaseDao<String, Immobile> implements ImmobileDao {

    private static DatabaseImmobileDao instance;

    public static DatabaseImmobileDao getInstance(String tableName, String primaryKeyColumn) throws SQLException {
        if (instance == null) {
            instance = new DatabaseImmobileDao(tableName, primaryKeyColumn);
        }
        return instance;
    }

    public DatabaseImmobileDao(String tableName, String primaryKeyColumn) throws SQLException {
        super(tableName, primaryKeyColumn);
    }

    @Override
    public String getKey(Immobile immobile) { return immobile.getIndirizzo(); }

    @Override
    protected Immobile mapResultSet(ResultSet rs) throws SQLException {
        Immobile imm = create(rs.getString("indirizzo"));
        imm.setServizi(rs.getString("servizi").split(" "));
        imm.setMaxOspiti(rs.getInt("maxospiti"));
        return imm;
    }

    @Override
    protected void setPreparedStatementForStore(PreparedStatement ps, Immobile immobile) throws SQLException {
        ps.setString(1, immobile.getIndirizzo());
        ps.setString(2, String.join(" ",immobile.getServizi()));
        ps.setInt(3, immobile.getMaxOspiti());
    }

    @Override
    protected void insert(Immobile entity) throws SQLException {
        String sql = "MERGE INTO IMMOBILI KEY(INDIRIZZO) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            setPreparedStatementForStore(ps, entity);
            ps.executeUpdate();
        }
    }
}

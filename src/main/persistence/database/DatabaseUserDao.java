package main.persistence.database;

import main.model.Affittuario;
import main.model.Locatore;
import main.model.Role;
import main.model.User;
import main.persistence.UserDao;

import java.sql.*;

public class DatabaseUserDao extends DatabaseDao<String, User> implements UserDao {

    private static DatabaseUserDao instance;

    public static DatabaseUserDao getInstance(String tableName, String primaryKeyColumn) throws SQLException {
        if(instance == null){
            instance = new DatabaseUserDao(tableName, primaryKeyColumn);
        }
        return instance;
    }

    public DatabaseUserDao(String tableName, String primaryKeyColumn) throws SQLException {
        super(tableName, primaryKeyColumn);
    }

    @Override
    protected String getKey(User user){
        return user.getEmail();
    }

    @Override
    protected User mapResultSet(ResultSet rs) throws SQLException {

        User user = create(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setNome(rs.getString("nome"));
        user.setCognome(rs.getString("cognome"));
        user.setTelefono(rs.getString("telefono"));

        switch(Role.fromString(rs.getString("ruolo"))) {
            case Role.AFFITTUARIO -> {
                return new Affittuario(user);
            }
            case Role.LOCATORE -> {
                return new Locatore(user);
            }
            case Role.INVALID ->  throw new RuntimeException();
        }
        return null;
    }

    @Override
    protected void setPreparedStatementForStore(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(1, entity.getEmail());
        ps.setString(2, entity.getPassword());
        ps.setString(3, entity.getNome());
        ps.setString(4, entity.getCognome());
        ps.setString(5, entity.getTelefono());

        if (entity instanceof Affittuario) ps.setString(6, "affittuario");
        else ps.setString(6, "locatore");
    }

    @Override
    protected void insert(User entity) throws SQLException {
        String sql = "MERGE INTO users KEY(email) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            setPreparedStatementForStore(ps, entity);
            ps.executeUpdate();
        }
    }

}

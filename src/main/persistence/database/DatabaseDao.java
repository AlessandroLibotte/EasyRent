package main.persistence.database;
import main.persistence.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseDao<K, V> implements Dao<K, V> {

    protected final Connection conn;
    protected String tableName;
    protected String primaryKeyColumn;

    public DatabaseDao(String tableName, String primaryKeyColumn) throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:~/easyrent", "", "");
        this.tableName = tableName;
        this.primaryKeyColumn = primaryKeyColumn;
    }

    protected abstract K getKey(V entity);

    protected abstract V mapResultSet(ResultSet rs) throws SQLException;

    @Override
    public V load(K id) {
        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void store(V entity) {
        try {
            insert(entity);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void setPreparedStatementForStore(PreparedStatement ps, V entity) throws SQLException;

    protected abstract void insert(V entity) throws SQLException;

    public void delete(K id) {
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(K id) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<V> loadAll() {
        List<V> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<V> loadAllWhere(String column, String value) {
        List<V> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE "+ column +"=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}

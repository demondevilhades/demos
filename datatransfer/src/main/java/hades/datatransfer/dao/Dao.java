package hades.datatransfer.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Dao implements AutoCloseable {

    protected Connection conn = null;

    @Override
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
        conn = null;
    }
}

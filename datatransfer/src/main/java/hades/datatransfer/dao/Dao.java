package hades.datatransfer.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Dao {

    private Connection conn = null;

    public Dao() throws SQLException {
        initConn();
    }

    @Override
    protected void finalize() throws Throwable {
        closeConn();
    }

    public abstract void initConn() throws SQLException;

    public void closeConn() throws SQLException {
        if (conn != null) {
            conn.close();
        }
        conn = null;
    }

    protected void setConn(Connection conn) {
        this.conn = conn;
    }

    protected Connection getConn() {
        return conn;
    }
}

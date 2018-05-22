package hades.datatransfer.dao.output;

import hades.datatransfer.Output;
import hades.datatransfer.dao.MysqlDao;

import java.sql.SQLException;
import java.sql.Statement;

public class MysqlSQLOutputDao extends MysqlDao implements Output<String> {

    protected Statement stmt = null;

    public MysqlSQLOutputDao() throws Exception {
        conn = initInputAndGet();
        stmt = conn.createStatement();
    }

    @Override
    public void close() throws SQLException {
        stmt.close();
        conn.close();
    }

    @Override
    public void output(String sql) throws SQLException {
        stmt.executeUpdate(sql);
    }
}

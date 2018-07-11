package hades.conn;

import hades.datatransfer.dao.MysqlDao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlLocalInfileOutputDao extends MysqlDao {
    protected Connection conn = null;

    public MysqlLocalInfileOutputDao() throws SQLException {
        conn = outputDs.getConnection();
    }

    public int insert(List<List<Object>> data, String loadDataSql) throws SQLException {
        StringBuilder sb = new StringBuilder();
        for (List<Object> row : data) {
            boolean isFirst = true;
            for (Object obj : row) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append("\t");
                }
                sb.append(obj.toString());
            }
            sb.append("\n");
        }
        return insert(new ByteArrayInputStream(sb.toString().getBytes()), loadDataSql);
    }

    public int insert(InputStream is, String loadDataSql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement(loadDataSql);
            if (pstmt.isWrapperFor(com.mysql.cj.jdbc.StatementImpl.class)) {
                com.mysql.cj.jdbc.PreparedStatement mysqlPstmt = pstmt
                        .unwrap(com.mysql.cj.jdbc.PreparedStatement.class);
                mysqlPstmt.setLocalInfileInputStream(is);
                result = mysqlPstmt.executeUpdate();
            }
            return result;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }
}

package hades.bg.dao;

import hades.bg.bean.ColumnInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class DBAbstractDao extends Dao {
    
    public abstract Map<String, List<ColumnInfo>> getTableInfoMap(String str) throws SQLException;

    protected void processingSelect(RsOperator rsOperator, String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rsOperator.operator(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                close(conn, pstmt, rs);
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    interface RsOperator {
        public void operator(ResultSet rs) throws SQLException;
    }
}

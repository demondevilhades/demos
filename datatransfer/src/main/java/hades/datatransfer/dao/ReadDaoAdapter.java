package hades.datatransfer.dao;

import hades.datatransfer.util.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * tips:thread unsafe
 * 
 * @author zs
 */
public abstract class ReadDaoAdapter<T> extends Dao {
    private static final Logger logger = LoggerFactory.getLogger(ReadDaoAdapter.class.getName());

    public ReadDaoAdapter() throws SQLException {
        super();
    }

    @Override
    public final void initConn() throws SQLException {
        setConn(DBUtil.getDbUtil().getReadConn());
    }

    protected abstract String getRetriveSql();

    protected abstract T getData(ResultSet rs);

    public List<T> batchRetrive(int firstIndex, int size) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<T> datas = new LinkedList<T>();
        try {
            pstmt = getConn().prepareStatement(getRetriveSql());
            pstmt.setInt(1, firstIndex);
            pstmt.setInt(2, size);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                datas.add(getData(rs));
            }
            return datas;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
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
}

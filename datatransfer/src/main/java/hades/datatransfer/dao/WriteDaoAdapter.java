package hades.datatransfer.dao;

import hades.datatransfer.util.DBUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * tips:thread unsafe
 * 
 * @author zs
 */
public abstract class WriteDaoAdapter<T> extends Dao {
    private static final Logger logger = LoggerFactory.getLogger(WriteDaoAdapter.class.getName());

    public WriteDaoAdapter() throws SQLException {
        super();
    }

    @Override
    public final void initConn() throws SQLException {
        setConn(DBUtil.getDbUtil().getWriteConn());
    }

    protected abstract String getBatchInsertSql();

    protected abstract void setData(PreparedStatement pstmt, T data);

    public void batchInsert(List<T> datas) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            getConn().setAutoCommit(false);
            pstmt = getConn().prepareStatement(getBatchInsertSql());

            for (T data : datas) {
                setData(pstmt, data);
                pstmt.addBatch();
            }

            int[] result = pstmt.executeBatch();
            int resultNum = 0;
            for (int i : result) {
                resultNum += i;
            }
            if (datas.size() == resultNum) {
                getConn().commit();
            } else {
                getConn().rollback();
                throw new SQLException("[datas] batch insert num : " + datas.size() + "\tcommit num : " + resultNum);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void batchInsertAutoCommit(List<T> datas) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            getConn().setAutoCommit(true);
            pstmt = getConn().prepareStatement(getBatchInsertSql());

            for (T data : datas) {
                setData(pstmt, data);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}

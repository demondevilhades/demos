package hades.conn;

import hades.datatransfer.dao.OracleDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleInputDao<T> extends OracleDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Connection conn = null;

    protected final String sql;
    protected final String startRowid;
    protected int batchSize = 100000;
    protected int flushConnBatch = 0;

    public OracleInputDao(String sql, int batchSize, String rowid, int flushConnBatch) throws Exception {
        this.sql = sql;
        this.startRowid = rowid;
        this.batchSize = batchSize > 0 ? batchSize : this.batchSize;
        this.flushConnBatch = flushConnBatch > 0 ? flushConnBatch : this.flushConnBatch;

        conn = inputDs.getConnection();
    }

    public static interface RowMapper<T> {
        public abstract T mapRow(ResultSet rs) throws SQLException;
    }

    public static interface BatchSelectCallBack<T> {
        public abstract void processing(List<T> result) throws Exception;
    }

    public void batchSelect(RowMapper<T> rowMapper, BatchSelectCallBack<T> batchSelectCallBack) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<T> result = new ArrayList<T>(batchSize);

        int resultSize = 0;
        int batch = 0;
        String rowid = startRowid;

        try {
            if (StringUtils.isEmpty(rowid)) {
                StringBuilder sb = new StringBuilder();
                sb.append(sql).append(" AND ROWNUM <= ").append(batchSize).append(" ORDER BY ROWID ");
                pstmt = conn.prepareStatement(sb.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    rowid = rs.getString("ROWID");
                    result.add(rowMapper.mapRow(rs));
                    resultSize++;
                }
                logger.info("batch=" + ++batch + " last_rowid=" + rowid + " resultSize=" + resultSize);
                rs.close();
                pstmt.close();
                batchSelectCallBack.processing(result);
                result.clear();
            } else {
                resultSize = batchSize;
            }

            if (resultSize == batchSize) {
                StringBuilder sb = new StringBuilder();
                sb.append(sql).append(" AND ROWID > ? AND ROWNUM <= ").append(batchSize).append(" ORDER BY ROWID ");
                pstmt = conn.prepareStatement(sb.toString());
                while (resultSize == batchSize && rowid != null) {
                    pstmt.setString(1, rowid);
                    rs = pstmt.executeQuery();
                    rowid = null;
                    resultSize = 0;
                    while (rs.next()) {
                        rowid = rs.getString("ROWID");
                        result.add(rowMapper.mapRow(rs));
                        resultSize++;
                    }
                    logger.info("batch=" + ++batch + " last_rowid=" + rowid + " resultSize=" + resultSize);
                    rs.close();
                    batchSelectCallBack.processing(result);
                    result.clear();
                    if (flushConnBatch > 0 && batch % flushConnBatch == 0) {
                        pstmt.close();
                        conn.close();
                        conn = inputDs.getConnection();
                        pstmt = conn.prepareStatement(sb.toString());
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("batch=" + batch++ + " last_rowid=" + rowid);
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

    public void batchSelectNew(RowMapper<T> rowMapper, BatchSelectCallBack<T> batchSelectCallBack) throws Exception {
        String rowid = startRowid;
        StringBuilder sb = new StringBuilder(sql);
        if (StringUtils.isNotEmpty(startRowid)) {
            sb.append(" AND ROWID > ").append(rowid);
        }
        sb.append(" ORDER BY ROWID ");
        logger.info("batchSize=" + batchSize + " rowid=" + rowid);
        logger.info(sql);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<T> result = new ArrayList<T>(batchSize);

        int resultSize = 0;
        int batch = 0;

        try {
            pstmt = conn.prepareStatement(sb.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            pstmt.setFetchSize(batchSize);
            pstmt.setFetchDirection(ResultSet.FETCH_FORWARD);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
                rowid = rs.getString("ROWID");
                resultSize++;

                if(resultSize == batchSize){
                    logger.info("batch=" + ++batch + " last_rowid=" + rowid + " resultSize=" + resultSize);
                    batchSelectCallBack.processing(result);
                    result.clear();
                    resultSize = 0;
                }
            }
            if(resultSize > 0){
                logger.info("batch=" + ++batch + " last_rowid=" + rowid + " resultSize=" + resultSize);
                batchSelectCallBack.processing(result);
            }
        } catch (SQLException e) {
            logger.error("batch=" + batch++ + " last_rowid=" + rowid);
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

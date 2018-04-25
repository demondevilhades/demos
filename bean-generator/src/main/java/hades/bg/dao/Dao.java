package hades.bg.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcConstants;

public abstract class Dao {
    
    public static final String ORACLE = JdbcConstants.ORACLE;
    public static final String MYSQL = JdbcConstants.MYSQL;

    public static void init() throws Exception {
        DB.init();
    }

    public static String getDbType() throws SQLException {
        return DB.db.dataSource.getDbType();
    }

    protected static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    protected DataSource getDataSource() {
        return DB.db.dataSource;
    }

    protected Connection getConn() throws SQLException {
        return DB.db.dataSource.getConnection();
    }

    private static class DB {

        static final DB db = new DB();
        private static final Logger LOGGER = LoggerFactory.getLogger(DB.class);

        private DruidDataSource dataSource = null;

        private DB() {
        }

        public static void init() throws Exception {
            if (db.dataSource == null) {
                LOGGER.info("DB.init start");
                Properties properties = new Properties();
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(DB.class.getClassLoader().getResource("jdbc.properties").getFile());
                    properties.load(fis);
                } catch (IOException e) {
                    throw e;
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                }
                db.dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
                LOGGER.info("DB.init end");
            }
        }
    }
}

package hades.datatransfer.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DBUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class.getName());
    static DBUtil dbUtil = null;

    private DataSource writeDs = null;
    private DataSource readDs = null;

    abstract void init(String readJDBC, String writeJDBC) throws Exception;

    abstract void closeDs();

    DataSource getReadDs() {
        return readDs;
    }

    protected void setReadDs(DataSource readDs) {
        this.readDs = readDs;
    }

    DataSource getWriteDs() {
        return writeDs;
    }

    protected void setWriteDs(DataSource writeDs) {
        this.writeDs = writeDs;
    }

    public Connection getWriteConn() throws SQLException {
        return writeDs.getConnection();
    }

    public Connection getReadConn() throws SQLException {
        return readDs.getConnection();
    }

    public static DBUtil getDbUtil() {
        return dbUtil;
    }

    static DBUtil initDBUtil(String readJDBC, String writeJDBC) {
        DBUtil dbUtil = null;
        try {
            DBBinder dbBinder = (DBBinder) Class.forName("hades.datatransfer.util.DBBinderImpl").newInstance();
            @SuppressWarnings("unchecked")
            Class<DBUtil> dbUtilImplClass = (Class<DBUtil>) dbBinder.getDBUtilImplClass();
            dbUtil = dbUtilImplClass.newInstance();
            dbUtil.init(readJDBC, writeJDBC);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            logger.error("init db error : impl", e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return dbUtil;
    }
}

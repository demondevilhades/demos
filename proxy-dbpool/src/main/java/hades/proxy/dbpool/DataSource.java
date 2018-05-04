package hades.proxy.dbpool;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DataSource implements ConnectionPoolDataSource, javax.sql.DataSource, Closeable {

    final Map<String, DruidDataSource> druidDataSourceMap = new HashMap<String, DruidDataSource>();

    private boolean isClosed = false;
    private boolean flush = false;
    private ReentrantReadWriteLock dsLock = new ReentrantReadWriteLock();
    
    protected DataSource(){
    }

    DataSource(Map<String, Map<String, String>> proxyMap, boolean flush) throws Exception {
        try {
            DB.LOCK.readLock().lock();
            flushDSMap(proxyMap);
            if (flush) {
                this.flush = flush;
                DB.dsList.add(this);
            }
        } finally {
            DB.LOCK.readLock().unlock();
        }
    }

    void flushDSMap(Map<String, Map<String, String>> proxyMap) throws Exception {
        dsLock.writeLock().lock();
        try {
            if (!isClosed) {
                for (Map.Entry<String, DruidDataSource> entry : druidDataSourceMap.entrySet()) {
                    entry.getValue().close();
                }
                druidDataSourceMap.clear();
                for (Map.Entry<String, Map<String, String>> entry : proxyMap.entrySet()) {
                    String proxyName = entry.getKey();
                    DruidDataSource druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(entry
                            .getValue());
                    druidDataSourceMap.put(proxyName, druidDataSource);
                }
            }
        } finally {
            dsLock.writeLock().unlock();
        }
    }

    public DruidDataSource getCurrDDS() {
        dsLock.readLock().lock();
        try {
            return druidDataSourceMap.get(DBConfig.getProxy());
        } finally {
            dsLock.readLock().unlock();
        }
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getCurrDDS().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getCurrDDS().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getCurrDDS().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return getCurrDDS().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getCurrDDS().getParentLogger();
    }

    @Override
    public PooledConnection getPooledConnection() throws SQLException {
        return getCurrDDS().getPooledConnection();
    }

    @Override
    public PooledConnection getPooledConnection(String user, String password) throws SQLException {
        return getCurrDDS().getPooledConnection(user, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getCurrDDS().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getCurrDDS().isWrapperFor(iface);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getCurrDDS().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getCurrDDS().getConnection(username, password);
    }

    @Override
    public void close() throws IOException {
        if (!isClosed) {
            dsLock.writeLock().lock();
            try {
                for (Map.Entry<String, DruidDataSource> entry : druidDataSourceMap.entrySet()) {
                    entry.getValue().close();
                }
                if (flush) {
                    DB.dsList.remove(this);
                }
                isClosed = true;
            } finally {
                dsLock.writeLock().unlock();
            }
        }
    }
}

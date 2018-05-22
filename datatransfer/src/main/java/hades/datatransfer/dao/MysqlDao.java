package hades.datatransfer.dao;

import java.sql.Connection;

import hades.datatransfer.util.ConfUtil;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public abstract class MysqlDao extends Dao {
    static DruidDataSource inputDs = null;
    static DruidDataSource outputDs = null;

    public synchronized static Connection initInputAndGet() throws Exception {
        if (inputDs != null) {
            inputDs = (DruidDataSource) DruidDataSourceFactory.createDataSource(ConfUtil.inputJdbcProperties);
        }
        return inputDs.getConnection();
    }

    public synchronized static Connection initOutput() throws Exception {
        if (outputDs != null) {
            outputDs = (DruidDataSource) DruidDataSourceFactory.createDataSource(ConfUtil.outputJdbcProperties);
        }
        return outputDs.getConnection();
    }

    public synchronized static void closeInput() {
        if (inputDs != null) {
            inputDs.close();
            inputDs = null;
        }
    }

    public synchronized static void closeOutput() {
        if (outputDs != null) {
            outputDs.close();
            outputDs = null;
        }
    }
}

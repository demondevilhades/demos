package hades.datatransfer.dao;

import hades.datatransfer.util.MysqlUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public abstract class MysqlDao extends Dao {

    static DruidDataSource inputDataSource = null;
    static DruidDataSource outputDataSource = null;

    public MysqlDao() {
        super(inputDataSource, outputDataSource);
    }

    public static void initDataSource() throws Exception {
        if (inputDataSource == null) {
            synchronized (MysqlDao.class) {
                if (inputDataSource == null) {
                    inputDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(MysqlUtils.INPUT_PROP);
                }
            }
        }
        if (outputDataSource == null) {
            synchronized (MysqlDao.class) {
                if (outputDataSource == null) {
                    outputDataSource = (DruidDataSource) DruidDataSourceFactory
                            .createDataSource(MysqlUtils.OUTPUT_PROP);
                }
            }
        }
    }

    public static void closeDataSource() throws Exception {
        if (inputDataSource != null) {
            synchronized (MysqlDao.class) {
                if (inputDataSource != null) {
                    inputDataSource.close();
                    inputDataSource = null;
                }
            }
        }
        if (outputDataSource != null) {
            synchronized (MysqlDao.class) {
                if (outputDataSource != null) {
                    outputDataSource.close();
                    outputDataSource = null;
                }
            }
        }
    }
}

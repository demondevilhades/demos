package hades.datatransfer.dao;

import hades.datatransfer.util.OracleUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public abstract class OracleDao extends Dao {

    static DruidDataSource inputDataSource = null;
    static DruidDataSource outputDataSource = null;

    public OracleDao() {
        super(inputDataSource, outputDataSource);
    }

    public static void initDataSource() throws Exception {
        if (inputDataSource == null) {
            synchronized (OracleDao.class) {
                if (inputDataSource == null) {
                    inputDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(OracleUtils.INPUT_PROP);
                }
            }
        }
        if (outputDataSource == null) {
            synchronized (OracleDao.class) {
                if (outputDataSource == null) {
                    outputDataSource = (DruidDataSource) DruidDataSourceFactory
                            .createDataSource(OracleUtils.OUTPUT_PROP);
                }
            }
        }
    }

    public static void closeDataSource() throws Exception {
        if (inputDataSource != null) {
            synchronized (OracleDao.class) {
                if (inputDataSource != null) {
                    inputDataSource.close();
                    inputDataSource = null;
                }
            }
        }
        if (outputDataSource != null) {
            synchronized (OracleDao.class) {
                if (outputDataSource != null) {
                    outputDataSource.close();
                    outputDataSource = null;
                }
            }
        }
    }
}

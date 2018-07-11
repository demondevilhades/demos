package hades.datatransfer.dao;

import com.alibaba.druid.pool.DruidDataSource;

public abstract class Dao implements AutoCloseable {
    protected DruidDataSource inputDs = null;
    protected DruidDataSource outputDs = null;

    public Dao(DruidDataSource inputDs, DruidDataSource outputDs) {
        this.inputDs = inputDs;
        this.outputDs = outputDs;
    }
}

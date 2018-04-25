package hades.bg;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hades.bg.bean.ColumnInfo;
import hades.bg.dao.Dao;
import hades.bg.dao.MysqlDao;
import hades.bg.dao.OracleDao;
import hades.bg.util.Config;

public class App {
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() throws Exception {
        Config.init();
        Dao.init();
        ClassWriter classWriter = new ClassWriter();
        Map<String, List<ColumnInfo>> tableInfoMap;
        if (Dao.ORACLE == Dao.getDbType()) {
            tableInfoMap = runOracle();
        } else if (Dao.MYSQL == Dao.getDbType()) {
            tableInfoMap = runMysql();
        } else {
            throw new RuntimeException("DbType=" + Dao.getDbType());
        }
        classWriter.write(tableInfoMap, Config.get("basePackage"));
    }

    private Map<String, List<ColumnInfo>> runMysql() throws SQLException {
        String table_schema = Config.get("table_schema").toUpperCase();
        MysqlDao dao = new MysqlDao();
        return dao.getTableInfoMap(table_schema);
    }

    private Map<String, List<ColumnInfo>> runOracle() throws SQLException {
        String owner = Config.get("owner").toUpperCase();
        OracleDao dao = new OracleDao();
        return dao.getTableInfoMap(owner);
    }

    public static void main(String[] args) throws Exception {
        new App().run();
    }
}

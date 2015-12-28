package test.stock.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MysqlConnextion {

    private static HikariDataSource ds = null;

    public static void init() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/financial?characterEncoding=utf-8");
        config.setDriverClassName("com.mysql.jdbc.Driver");
//        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
//        config.addDataSourceProperty("serverName", "localhost");
//        config.addDataSourceProperty("port", "3306");
//        config.addDataSourceProperty("databaseName", "financial");
        config.addDataSourceProperty("user", "root");
        config.addDataSourceProperty("password", "root");

//        config.addDataSourceProperty("useUnicode", "true");
//        config.addDataSourceProperty("characterEncoding", "utf8");
        ds = new HikariDataSource(config);
    }

    public static Connection getConn() throws SQLException {
        return ds.getConnection();
    }

    public static void closeDs() {
        ds.close();
    }
}

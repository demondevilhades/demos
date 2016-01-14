package hades.datatransfer.util;

import java.sql.Connection;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDBUtil extends DBUtil {

    HikariCPDBUtil() {
    }

    @Override
    void init(String readJDBC, String writeJDBC) throws Exception {
        HikariDataSource ds = null;

        Connection connection = null;
        Properties propfile = new Properties();
        try {
            propfile.load(HikariCPDBUtil.class.getResourceAsStream(writeJDBC));
            HikariConfig config = new HikariConfig(propfile);
            config.validate();

            ds = new HikariDataSource(config);
            connection = ds.getConnection();
            setWriteDs(ds);
            connection.close();

            propfile = new Properties();
            propfile.load(HikariCPDBUtil.class.getResourceAsStream(readJDBC));
            config = new HikariConfig(propfile);
            config.validate();
            ds = new HikariDataSource(config);
            connection = ds.getConnection();
            setReadDs(ds);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    void closeDs() {
        if (getWriteDs() != null) {
            ((HikariDataSource) getWriteDs()).close();
        }
        if (getReadDs() != null) {
            ((HikariDataSource) getReadDs()).close();
        }
    }
}

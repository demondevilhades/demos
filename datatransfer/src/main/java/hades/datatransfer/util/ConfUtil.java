package hades.datatransfer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfUtil.class.getName());

    private static InputStream defConfPath = ConfUtil.class.getResourceAsStream("/conf/app.properties");
    private static final Properties properties = new Properties();
    private static String WRITE_JDBC = null;
    private static String READ_JDBC = null;

    private ConfUtil() {
    }

    private static void init() {
        try {
            properties.load(defConfPath);
            WRITE_JDBC = properties.getProperty("WRITE_JDBC");
            READ_JDBC = properties.getProperty("READ_JDBC");
        } catch (IOException e) {
            logger.error("/conf/app.properties load error!");
        }
    }

    public static void test() {

    }

    static {
        init();
        DBUtil.dbUtil = DBUtil.initDBUtil(READ_JDBC, WRITE_JDBC);
        if (DBUtil.dbUtil == null) {
            logger.error("init error!");
            System.exit(2);
        }
    }
}

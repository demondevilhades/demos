package hades.datatransfer.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MysqlUtils {
    private static final Logger logger = LoggerFactory.getLogger(MysqlUtils.class.getName());

    public static final Properties INPUT_PROP = new Properties();
    public static final Properties OUTPUT_PROP = new Properties();
    private static String OUTPUT_JDBC = "/conf/mysql_jdbc.input.properties";
    private static String INPUT_JDBC = "/conf/mysql_jdbc.output.properties";

    private MysqlUtils() {
    }

    public static void init() {
        try {
            INPUT_PROP.load(MysqlUtils.class.getResourceAsStream(INPUT_JDBC));
            OUTPUT_PROP.load(MysqlUtils.class.getResourceAsStream(OUTPUT_JDBC));
        } catch (IOException e) {
            logger.error("", e);
        }
    }

//    static {
//        init();
//    }
}

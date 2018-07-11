package hades.datatransfer.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OracleUtils {
    private static final Logger logger = LoggerFactory.getLogger(OracleUtils.class.getName());

    public static final Properties INPUT_PROP = new Properties();
    public static final Properties OUTPUT_PROP = new Properties();
    private static String OUTPUT_JDBC = "/conf/oracle_jdbc.input.properties";
    private static String INPUT_JDBC = "/conf/oracle_jdbc.output.properties";

    private OracleUtils() {
    }

    public static void init() {
        try {
            INPUT_PROP.load(OracleUtils.class.getResourceAsStream(INPUT_JDBC));
            OUTPUT_PROP.load(OracleUtils.class.getResourceAsStream(OUTPUT_JDBC));
        } catch (IOException e) {
            logger.error("", e);
        }
    }

//    static {
//        init();
//    }
}

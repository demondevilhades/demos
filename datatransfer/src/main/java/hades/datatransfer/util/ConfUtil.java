package hades.datatransfer.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfUtil.class.getName());

    public static final Properties appProperties = new Properties();
    public static final Properties inputJdbcProperties = new Properties();
    public static final Properties outputJdbcProperties = new Properties();
    private static String APP = "/conf/app.properties";
    private static String OUTPUT_JDBC = "/conf/jdbc.input.properties";
    private static String INPUT_JDBC = "/conf/jdbc.output.properties";

    private ConfUtil() {
    }

    public static void init() {
        try {
            appProperties.load(ConfUtil.class.getResourceAsStream(APP));
            inputJdbcProperties.load(ConfUtil.class.getResourceAsStream(OUTPUT_JDBC));
            outputJdbcProperties.load(ConfUtil.class.getResourceAsStream(INPUT_JDBC));
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    static {
        init();
    }
}

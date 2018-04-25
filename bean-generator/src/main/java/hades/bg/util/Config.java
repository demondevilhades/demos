package hades.bg.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    private static final Properties properties = new Properties();
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    public static void init() throws Exception {
        LOGGER.info("Config.init start");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(Config.class.getClassLoader().getResource("app.properties").getFile());
            properties.load(fis);
            LOGGER.info("Config.init end");
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}

package test.webpage2pic.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
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
            fis = new FileInputStream(ResourcesUtils.getResource("app.properties").getFile());
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
        String property = properties.getProperty(key);
        if (property == null) {
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                if (key.toUpperCase().equals(((String) entry.getKey()).toUpperCase())) {
                    if (property == null) {
                        property = (String) entry.getValue();
                    } else {
                        return null;
                    }
                }
            }
        }
        return property;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}

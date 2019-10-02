package test.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ResourcesUtils {

    public static String getResourceFile(String str) {
        return ResourcesUtils.class.getClassLoader().getResource(str).getFile();
    }

    public static URL getResource(String str) {
        return ResourcesUtils.class.getClassLoader().getResource(str);
    }

    public static Properties load(String propName) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(ResourcesUtils.getResourceFile(propName + ".properties"));
            properties.load(fis);
            return properties;
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
}

package com.hades.leetcode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataUtils {

    private static final Properties PROP = initAndGet();

    public static Properties initAndGet() {
        FileInputStream fis = null;
        Properties prop = new Properties();
        try {
            fis = new FileInputStream(DataUtils.class.getClassLoader().getResource("data.properties").getFile());
            prop.load(fis);
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(1);
        return null;
    }

    public static int[] readIntArr(String key) {
        String prop = PROP.getProperty(key);
        String[] split = prop.split(",");
        int[] result = new int[split.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(split[i]);
        }
        return result;
    }
}

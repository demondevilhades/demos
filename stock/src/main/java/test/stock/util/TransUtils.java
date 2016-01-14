package test.stock.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class TransUtils {

    public static <T> T map2Bean(Map<String, Object> map, Class<? extends T> clazz) {
        T bean = null;
        try {
            bean = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        String key;
        Method[] methods = clazz.getMethods();
        F: for (Map.Entry<String, Object> entry : map.entrySet()) {
            key = entry.getKey();
            for (Method method : methods) {
                if (method.getName().equalsIgnoreCase("set" + key)) {
                    try {
                        method.invoke(bean, entry.getValue());
                        continue F;
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    }
                }
            }
        }
        return bean;
    }
}

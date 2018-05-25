package hades.asm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectionUtils {

    public static Object newInstance(Class<?> clazz) throws ReflectiveOperationException {
        return clazz.newInstance();
    }

    public static Object newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object[] initargs)
            throws ReflectiveOperationException {
        Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
        constructor.setAccessible(true);
        return constructor.newInstance(initargs);
    }

    public static Object invockMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object o,
            Object[] args) throws ReflectiveOperationException {
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(o, args);
    }
}

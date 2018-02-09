package hades.robot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectMethodUtil<T> {

    private final Class<T> clazz;
    private final Method method;

    public ReflectMethodUtil(Class<T> clazz, String methodName, Class<?>... parameterTypes)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException {
        this(clazz.getName(), methodName, parameterTypes);
    }

    @SuppressWarnings("unchecked")
    public ReflectMethodUtil(String className, String methodName, Class<?>... parameterTypes)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException {
        clazz = (Class<T>) Class.forName(className);
        method = clazz.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
    }

    public Object invockMethod(T t, Object... args) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return method.invoke(t, args);
    }
}

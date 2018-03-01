package hades.asm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class HandleUtils {

    public static Object newInstance(Class<?> clazz) throws Throwable {
        return MethodHandles.lookup().findConstructor(clazz, MethodType.methodType(void.class)).invoke();
    }

    public static Object newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object[] initargs) throws Throwable {
        return MethodHandles.lookup().findConstructor(clazz, MethodType.methodType(void.class, parameterTypes))
                .invoke(initargs);
    }

    public static Object invockMethod(Class<?> clazz, String name, Class<?> rtype, Class<?>[] ptypes,
            Object... initargs) throws Throwable {
        MethodType methodType = ptypes == null ? MethodType.methodType(rtype) : MethodType.methodType(rtype, ptypes);
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(clazz, name, methodType);
        if (ptypes == null) {
            return methodHandle.invoke(initargs[0]);
        } else {
            return methodHandle.invoke(initargs);
        }
    }

    /**
     * invoke private method
     * 
     * @param clazz
     * @param name
     * @param rtype
     * @param ptypes
     * @param specialCaller
     * @param initargs
     * @return
     * @throws Throwable
     */
    public static Object invockMethod(Class<?> clazz, String name, Class<?> rtype, Class<?>[] ptypes,
            Class<?> specialCaller, Object... initargs) throws Throwable {
        MethodType methodType = ptypes == null ? MethodType.methodType(rtype) : MethodType.methodType(rtype, ptypes);
        MethodHandle methodHandle = MethodHandles.lookup().findSpecial(clazz, name, methodType, specialCaller);
        if (ptypes == null) {
            return methodHandle.invoke(initargs[0]);
        } else {
            return methodHandle.invoke(initargs);
        }
    }
}

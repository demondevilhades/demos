package hades.asm;

public class ClassLoaderUtils {

    private static final ThreadLocal<MyClassLoader> mclThreadLocal = new ThreadLocal<MyClassLoader>();

    public static Class<?> defineClass(final String name, final byte[] bs) {
        if (mclThreadLocal.get() == null) {
            mclThreadLocal.set(new MyClassLoader(Thread.currentThread().getContextClassLoader()));
        }
        MyClassLoader myClassLoader = mclThreadLocal.get();
        return myClassLoader.defineClass(name, bs);
    }

    static class MyClassLoader extends ClassLoader {
        MyClassLoader(ClassLoader classLoader) {
            super(classLoader);
        }

        public Class<?> defineClass(final String name, final byte[] bs) {
            return this.defineClass(name, bs, 0, bs.length);
        }
    }
}

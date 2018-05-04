package hades.proxy.dbpool;

public class DBConfig {
    public static final long FLUSH_PERIOD = 1000;
    public static final String PROXY = "proxy";
    public static final String DEF_PROXY = "defProxy";
    public static final String TIMER_NAME = "DBTimer";

    private static final ThreadLocal<String> PROXY_THREAD_LOCAL = new ThreadLocal<String>();
    static String DEF_PROXY_NAME = null;

    public static void setProxy(String proxy) {
        PROXY_THREAD_LOCAL.set(proxy);
    }

    public static String getProxy() {
        String proxy = PROXY_THREAD_LOCAL.get();
        return proxy == null ? DEF_PROXY_NAME : proxy;
    }

    public static void init(String proxyFilePath, boolean flush) throws Exception {
        DB.init(proxyFilePath, flush);
    }
}

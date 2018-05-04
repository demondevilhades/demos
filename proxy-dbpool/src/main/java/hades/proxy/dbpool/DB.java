package hades.proxy.dbpool;

import hades.proxy.dbpool.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DB {

    private static final Logger LOGGER = LoggerFactory.getLogger(DB.class);

    private static Timer timer = null;

    private static final Map<String, Map<String, String>> proxyMap = new HashMap<String, Map<String, String>>();
    static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    static final List<DataSource> dsList = new ArrayList<DataSource>();

    private static boolean inited = false;

    static synchronized void init(String proxyFilePath, boolean flush) throws Exception {
        if (!inited) {
            flushProxyMap(proxyFilePath);
            if (flush) {
                startProxyMonitor(proxyFilePath);
            }
        }
    }

    static Map<String, Map<String, String>> getProxymap() {
        if (inited) {
            return proxyMap;
        } else {
            throw new RuntimeException("proxy need init");
        }
    }

    private static void flushProxyMap(String proxyFilePath) throws Exception {
        try {
            LOCK.writeLock().lock();
            File proxyFile = new File(proxyFilePath);
            if (!proxyFile.exists()) {
                LOGGER.error("init failed : proxy file not found.");
            } else {
                proxyMap.clear();
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(proxyFilePath);
                    Properties properties = new Properties();
                    properties.load(fis);
                    String proxyStr = properties.getProperty(DBConfig.PROXY);
                    if (StringUtils.isNotEmpty(proxyStr)) {
                        String[] split = proxyStr.split(",");
                        for (String proxyKey : split) {
                            proxyKey = proxyKey.trim();
                            String keyStart = proxyKey + ".";
                            Map<String, String> map = new HashMap<String, String>();
                            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                                String entryKey = (String) entry.getKey();
                                if (entryKey.startsWith(keyStart)) {
                                    map.put(entryKey.replaceFirst(keyStart, ""), (String) entry.getValue());
                                }
                            }
                            proxyMap.put(proxyKey, map);
                        }
                    }
                    DBConfig.DEF_PROXY_NAME = properties.getProperty(DBConfig.DEF_PROXY);
                    flushDS();
                    inited = true;
                } finally {
                    if (fis != null) {
                        fis.close();
                    }
                }
            }
        } finally {
            LOCK.writeLock().unlock();
        }
    }

    private static void flushDS() throws Exception {
        for (DataSource dataSource : dsList) {
            dataSource.flushDSMap(proxyMap);
        }
    }

    private synchronized static void startProxyMonitor(String proxyFilePath) {
        File proxyFile = new File(proxyFilePath);
        if (!proxyFile.exists()) {
            LOGGER.error("start proxy monitor failed : proxy file not found.");
        } else if (timer == null) {
            ProxyFileMonitor proxyFileMonitor = new ProxyFileMonitor(proxyFilePath);
            timer = new Timer(DBConfig.TIMER_NAME, true);
            timer.schedule(proxyFileMonitor, DBConfig.FLUSH_PERIOD, DBConfig.FLUSH_PERIOD);
            LOGGER.info("start proxy monitor : " + proxyFilePath + ", " + DBConfig.FLUSH_PERIOD);
        }
    }

    static class ProxyFileMonitor extends TimerTask {

        private String proxyFilePath;

        public ProxyFileMonitor(String proxyFilePath) {
            this.proxyFilePath = proxyFilePath;
        }

        @Override
        public void run() {
            try {
                flushProxyMap(proxyFilePath);
            } catch (Exception e) {
                LOGGER.error("proxy flush failed.", e);
                timer.cancel();
            }
        }
    }
}

package hades.proxy.dbpool;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

public class DataSourceFactory implements ObjectFactory {

    public static DataSource createDataSource() throws Exception {
        return new DataSource(DB.getProxymap(), false);
    }

    public static DataSource createDataSource(Map<String, Map<String, String>> proxyMap) throws Exception {
        return new DataSource(proxyMap, false);
    }

    public static DataSource createDataSource(boolean flush) throws Exception {
        return new DataSource(DB.getProxymap(), flush);
    }

    public static DataSource createDataSource(Map<String, Map<String, String>> proxyMap, boolean flush)
            throws Exception {
        return new DataSource(proxyMap, flush);
    }

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
            throws Exception {
        return createDataSource();
    }
}

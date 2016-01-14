package hades.datatransfer.util;

public class DBBinderImpl implements DBBinder {

    @Override
    public String getDBUtilImplClassStr() {
        return HikariCPDBUtil.class.getName();
    }

    @Override
    public Class<?> getDBUtilImplClass() {
        return HikariCPDBUtil.class;
    }
}

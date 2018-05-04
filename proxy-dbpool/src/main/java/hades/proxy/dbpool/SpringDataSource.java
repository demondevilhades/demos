package hades.proxy.dbpool;

public class SpringDataSource extends DataSource {

    private String jdbcProp = null;
    private boolean flush = false;
    private boolean inited = false;

    public SpringDataSource() {
    }

    public void init() throws Exception {
        if (jdbcProp != null) {
            if (jdbcProp.startsWith("classpath:")) {
                jdbcProp = DBTest.class.getClassLoader().getResource(jdbcProp.replaceFirst("classpath:", "")).getFile();
            }
            DB.init(jdbcProp, flush);
            flushDSMap(DB.getProxymap());
            if (flush) {
                DB.dsList.add(this);
            }
            inited = true;
        }
    }

    public boolean isInited() {
        return inited;
    }

    public String getJdbcProp() {
        return jdbcProp;
    }

    public void setJdbcProp(String jdbcProp) {
        this.jdbcProp = jdbcProp;
    }

    public boolean isFlush() {
        return flush;
    }

    public void setFlush(boolean flush) {
        this.flush = flush;
    }
}

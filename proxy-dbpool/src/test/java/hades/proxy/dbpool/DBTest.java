package hades.proxy.dbpool;

import java.sql.Connection;

import org.junit.Test;

public class DBTest {

    @Test
    public void test() throws Exception {
        String fileStr = DBTest.class.getClassLoader().getResource("jdbc.properties").getFile();
        DBConfig.init(fileStr, true);
        DataSource dataSource = DataSourceFactory.createDataSource(true);
        Thread.sleep(1000);
        testDS(dataSource);
        Thread.sleep(2000);
        testDS(dataSource);
        Thread.sleep(1000);
        dataSource.close();
    }
    
    private void testDS(DataSource dataSource) throws Exception{
        Connection conn = dataSource.getConnection();
        conn.close();
        DBConfig.setProxy("test1");
        conn = dataSource.getConnection();
        conn.close();
//        DBConfig.setProxy("test2");
//        conn = dataSource.getConnection();
//        conn.close();
        DBConfig.setProxy("test0");
        conn = dataSource.getConnection();
        conn.close();
    }
}

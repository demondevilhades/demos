package hades.proxy.dbpool;

import java.sql.Connection;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/dataSource.xml"})
public class SpringTest {
    
    @Resource
    JdbcTemplate jdbcTemplate;

    @Before
    public void before() throws Exception {
    }

    @Test
    public void test() throws Exception {
        javax.sql.DataSource dataSource = jdbcTemplate.getDataSource();
        Assert.assertNotNull(dataSource);
        testDS(dataSource);
    }

    private void testDS(javax.sql.DataSource dataSource) throws Exception {
        Connection conn = dataSource.getConnection();
        conn.close();
        DBConfig.setProxy("test1");
        conn = dataSource.getConnection();
        conn.close();
        DBConfig.setProxy("test0");
        conn = dataSource.getConnection();
        conn.close();
    }
}

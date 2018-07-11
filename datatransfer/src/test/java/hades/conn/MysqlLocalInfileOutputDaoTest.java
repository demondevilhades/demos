package hades.conn;

import hades.datatransfer.dao.MysqlDao;
import hades.datatransfer.util.MysqlUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MysqlLocalInfileOutputDaoTest {

    @Test
    public void test() throws Exception {
        MysqlUtils.init();
        MysqlDao.initDataSource();
        try (MysqlLocalInfileOutputDao dao = new MysqlLocalInfileOutputDao();) {
            String sql = "LOAD DATA LOCAL INFILE 'sql.csv' IGNORE INTO TABLE test.test (a, b, c, d)";
            Object[] row;

            List<List<Object>> data = new ArrayList<List<Object>>();
            {
                row = new Object[]{1, 2, 3, 4};
                data.add(Arrays.asList(row));
            }
            {
                row = new Object[]{2, 3, 4, 5};
                data.add(Arrays.asList(row));
            }

            Assert.assertEquals(dao.insert(data, sql), 2);
        }
    }
}

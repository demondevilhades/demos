package test.stock.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;

import test.stock.clint.impl.wmcloud.FundParam;

public class FundTest {

    private Fund fund = new Fund();

    @Before
    public void before() {
        MysqlConnextion.init();
    }

    @After
    public void after() {
        MysqlConnextion.closeDs();
    }

    @Test
    public void test() {
        fund.select();
    }

//    @Test
    public void init() {
        test.stock.clint.impl.wmcloud.Fund fundBean = new test.stock.clint.impl.wmcloud.Fund();
        JSONArray jsonArray = fundBean.getFundJson(null, FundParam.ListStatusCd.UN.value(), null, null, null);
        fund.insert(jsonArray, "fund", "fid");
    }
}

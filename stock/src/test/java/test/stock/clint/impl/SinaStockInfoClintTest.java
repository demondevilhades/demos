package test.stock.clint.impl;

import org.junit.Test;

import test.stock.bean.SingleStockInfo;
import test.stock.clint.IStockInfoClint;

public class SinaStockInfoClintTest {

    private IStockInfoClint stockInfoClint = new SinaStockInfoClint();

    @Test
    public void test() {
        // stockInfoClint.getSingleInfo("000001");
        SingleStockInfo singleStockInfo = stockInfoClint.getSingleInfo("600406");
        System.out.println(singleStockInfo);
    }
}

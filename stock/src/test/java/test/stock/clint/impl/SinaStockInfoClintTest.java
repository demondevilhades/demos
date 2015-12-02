package test.stock.clint.impl;

import org.junit.Test;

import test.stock.clint.IStockInfoClint;

public class SinaStockInfoClintTest {

    private IStockInfoClint stockInfoClint = new SinaStockInfoClint();

    @Test
    public void test() {
        stockInfoClint.getSingleInfo("000001");
        stockInfoClint.getSingleInfo("600406");
    }
}

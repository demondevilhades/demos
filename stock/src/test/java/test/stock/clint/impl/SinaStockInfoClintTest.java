package test.stock.clint.impl;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import test.stock.bean.SingleStockInfo;
import test.stock.clint.IStockInfoClint;

public class SinaStockInfoClintTest {

    private IStockInfoClint stockInfoClint = new SinaStockInfoClint();

    @Test
    public void testSingle() {
        SingleStockInfo singleStockInfo = stockInfoClint.getSingleInfo("600406");
        System.out.println(singleStockInfo);
    }

    @Test
    public void testMulti() {
        List<String> strs = new LinkedList<String>();
        strs.add("000001");
        strs.add("600406");
        List<SingleStockInfo> multiInfo = stockInfoClint.getMultiInfo(strs);
        for (SingleStockInfo singleStockInfo : multiInfo) {
            System.out.println(singleStockInfo);
        }
    }
}

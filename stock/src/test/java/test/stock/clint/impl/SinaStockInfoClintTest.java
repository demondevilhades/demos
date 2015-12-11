package test.stock.clint.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.stock.bean.SingleCompositeIndex;
import test.stock.bean.SingleStockInfo;
import test.stock.clint.IStockInfoClint;

/**
 * 新浪链接测试类
 * 
 * @author zs
 */
public class SinaStockInfoClintTest {

    private IStockInfoClint stockInfoClint = new SinaStockInfoClint();

    @Before
    public void setUp() {
    }

    @Test
    public void getSingleStockInfoTest() {
        SingleStockInfo singleStockInfo = stockInfoClint.getSingleStockInfo("600406");
        Assert.assertNotNull(singleStockInfo.getName());
        System.out.println(singleStockInfo);
    }

    @Test
    public void getMultiStockInfoTest() {
        List<String> strs = new LinkedList<String>();
        strs.add("000001");
        strs.add("600406");
        List<SingleStockInfo> multiInfo = stockInfoClint.getMultiStockInfo(strs);
        Assert.assertNotNull(multiInfo.get(0).getName());
        for (SingleStockInfo singleStockInfo : multiInfo) {
            System.out.println(singleStockInfo);
        }
    }

    @Test
    public void getSingleCompositeIndexTest() {
        SingleCompositeIndex singleCompositeIndex = stockInfoClint.getSingleCompositeIndex("000001", true);
        Assert.assertNotNull(singleCompositeIndex.getName());
        System.out.println(singleCompositeIndex);
    }

    @Test
    public void getMultiCompositeIndexTest() {
        List<String> strs = new LinkedList<String>();
        strs.add("000001");
        strs.add("000002");
        List<SingleCompositeIndex> multiInfo = stockInfoClint.getMultiCompositeIndex(strs, true);
        Assert.assertNotNull(multiInfo.get(0).getName());
        for (SingleCompositeIndex singleCompositeIndex : multiInfo) {
            System.out.println(singleCompositeIndex);
        }
    }

    @Test
    public void getInfoByDayTest() {
        List<String[]> infoByDay = stockInfoClint.getInfoByDay("2011-07-08", "600900");
        Assert.assertNotNull(infoByDay);
        for (String[] strs : infoByDay) {
            System.out.println(Arrays.toString(strs));
        }
    }
}

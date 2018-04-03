package hades.data.analyse;

import hades.data.analyse.data.DataArrayList;
import hades.data.analyse.data.DataResult;
import hades.data.analyse.formula.macd.EMA;
import hades.data.analyse.formula.macd.EMARounding;
import hades.data.analyse.formula.macd.MACD;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class AppTest {

    private DataArrayList data;

    @Before
    public void setUp() {
        data = new DataArrayList();
        for (int i = 1; i <= 20; i++) {
            data.add(new DataResult(new BigDecimal(i)));
        }
        for (int i = 20; i >= 1; i--) {
            data.add(new DataResult(new BigDecimal(i)));
        }
        for (int i = 1; i <= 20; i++) {
            data.add(new DataResult(new BigDecimal(i)));
        }
    }

    @Test
    public void test() {
        EMA ema = new EMARounding(new BigDecimal(12));
        MACD macd = new MACD(new BigDecimal(9), new BigDecimal(12), new BigDecimal(26));
        System.out.println(ema.calc(data));
        System.out.println(macd.calc(data));
    }
}

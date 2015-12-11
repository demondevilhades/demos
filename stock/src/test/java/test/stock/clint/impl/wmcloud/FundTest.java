package test.stock.clint.impl.wmcloud;

import org.junit.Test;

public class FundTest {

    private Fund fund = new Fund();

    @Test
    public void getFundJsonTest() {
        System.out.println(fund.getFundJson("161207"));
    }
}

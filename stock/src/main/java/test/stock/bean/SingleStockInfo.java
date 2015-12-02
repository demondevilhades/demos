package test.stock.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SingleStockInfo implements Serializable {

    private String code;
    private String name;
    private String openingPrice;
    private String closingPrice;
    private String currentPrice;
    private String highestPriceOfToday;
    private String lowestPriceOfToday;
    /**
     * 竞买价(买1)
     */
    private String bidPrice;
    /**
     * 竞卖价(卖1)
     */
    private String auctionPrice;
    /**
     * 成交股票数
     */
    private String transNum;
    /**
     * 成交额(元)
     */
    private String turnover;

    // bidNum 申请股数, bidPrice 买报价, sellNum 申报股数, sellPrice 卖报价
    // start
    private String bidNum1;
    private String bidPrice1;
    private String bidNum2;
    private String bidPrice2;
    private String bidNum3;
    private String bidPrice3;
    private String bidNum4;
    private String bidPrice4;
    private String bidNum5;
    private String bidPrice5;
    private String sellNum1;
    private String sellPrice1;
    private String sellNum2;
    private String sellPrice2;
    private String sellNum3;
    private String sellPrice3;
    private String sellNum4;
    private String sellPrice4;
    private String sellNum5;
    private String sellPrice5;
    // end

    private String date;
    private String time;
}

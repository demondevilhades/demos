package test.stock.bean;

import java.io.Serializable;

/**
 * 股票信息实体
 * 
 * @author HaDeS
 */
@SuppressWarnings("serial")
public class SingleStockInfo implements Serializable {
    private String code;
    private String name;
    /**
     * 今开
     */
    private String openingPrice;
    /**
     * 昨收
     */
    private String closingPrice;
    /**
     * 当前价格
     */
    private String currentPrice;
    /**
     * 今日最高
     */
    private String highestPrice;
    /**
     * 今日最低
     */
    private String lowestPrice;
    /**
     * 竞买价(买1)
     */
    private String bidPrice;
    /**
     * 竞卖价(卖1)
     */
    private String sellPrice;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(String openingPrice) {
        this.openingPrice = openingPrice;
    }

    public String getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(String closingPrice) {
        this.closingPrice = closingPrice;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getBidNum1() {
        return bidNum1;
    }

    public void setBidNum1(String bidNum1) {
        this.bidNum1 = bidNum1;
    }

    public String getBidPrice1() {
        return bidPrice1;
    }

    public void setBidPrice1(String bidPrice1) {
        this.bidPrice1 = bidPrice1;
    }

    public String getBidNum2() {
        return bidNum2;
    }

    public void setBidNum2(String bidNum2) {
        this.bidNum2 = bidNum2;
    }

    public String getBidPrice2() {
        return bidPrice2;
    }

    public void setBidPrice2(String bidPrice2) {
        this.bidPrice2 = bidPrice2;
    }

    public String getBidNum3() {
        return bidNum3;
    }

    public void setBidNum3(String bidNum3) {
        this.bidNum3 = bidNum3;
    }

    public String getBidPrice3() {
        return bidPrice3;
    }

    public void setBidPrice3(String bidPrice3) {
        this.bidPrice3 = bidPrice3;
    }

    public String getBidNum4() {
        return bidNum4;
    }

    public void setBidNum4(String bidNum4) {
        this.bidNum4 = bidNum4;
    }

    public String getBidPrice4() {
        return bidPrice4;
    }

    public void setBidPrice4(String bidPrice4) {
        this.bidPrice4 = bidPrice4;
    }

    public String getBidNum5() {
        return bidNum5;
    }

    public void setBidNum5(String bidNum5) {
        this.bidNum5 = bidNum5;
    }

    public String getBidPrice5() {
        return bidPrice5;
    }

    public void setBidPrice5(String bidPrice5) {
        this.bidPrice5 = bidPrice5;
    }

    public String getSellNum1() {
        return sellNum1;
    }

    public void setSellNum1(String sellNum1) {
        this.sellNum1 = sellNum1;
    }

    public String getSellPrice1() {
        return sellPrice1;
    }

    public void setSellPrice1(String sellPrice1) {
        this.sellPrice1 = sellPrice1;
    }

    public String getSellNum2() {
        return sellNum2;
    }

    public void setSellNum2(String sellNum2) {
        this.sellNum2 = sellNum2;
    }

    public String getSellPrice2() {
        return sellPrice2;
    }

    public void setSellPrice2(String sellPrice2) {
        this.sellPrice2 = sellPrice2;
    }

    public String getSellNum3() {
        return sellNum3;
    }

    public void setSellNum3(String sellNum3) {
        this.sellNum3 = sellNum3;
    }

    public String getSellPrice3() {
        return sellPrice3;
    }

    public void setSellPrice3(String sellPrice3) {
        this.sellPrice3 = sellPrice3;
    }

    public String getSellNum4() {
        return sellNum4;
    }

    public void setSellNum4(String sellNum4) {
        this.sellNum4 = sellNum4;
    }

    public String getSellPrice4() {
        return sellPrice4;
    }

    public void setSellPrice4(String sellPrice4) {
        this.sellPrice4 = sellPrice4;
    }

    public String getSellNum5() {
        return sellNum5;
    }

    public void setSellNum5(String sellNum5) {
        this.sellNum5 = sellNum5;
    }

    public String getSellPrice5() {
        return sellPrice5;
    }

    public void setSellPrice5(String sellPrice5) {
        this.sellPrice5 = sellPrice5;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SingleStockInfo [code=" + code + ", name=" + name + ", openingPrice=" + openingPrice
                        + ", closingPrice=" + closingPrice + ", currentPrice=" + currentPrice + ", highestPrice="
                        + highestPrice + ", lowestPrice=" + lowestPrice + ", bidPrice=" + bidPrice + ", sellPrice="
                        + sellPrice + ", transNum=" + transNum + ", turnover=" + turnover + ", bidNum1=" + bidNum1
                        + ", bidPrice1=" + bidPrice1 + ", bidNum2=" + bidNum2 + ", bidPrice2=" + bidPrice2
                        + ", bidNum3=" + bidNum3 + ", bidPrice3=" + bidPrice3 + ", bidNum4=" + bidNum4 + ", bidPrice4="
                        + bidPrice4 + ", bidNum5=" + bidNum5 + ", bidPrice5=" + bidPrice5 + ", sellNum1=" + sellNum1
                        + ", sellPrice1=" + sellPrice1 + ", sellNum2=" + sellNum2 + ", sellPrice2=" + sellPrice2
                        + ", sellNum3=" + sellNum3 + ", sellPrice3=" + sellPrice3 + ", sellNum4=" + sellNum4
                        + ", sellPrice4=" + sellPrice4 + ", sellNum5=" + sellNum5 + ", sellPrice5=" + sellPrice5
                        + ", date=" + date + ", time=" + time + "]";
    }
}

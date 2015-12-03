package test.stock.bean;

import java.io.Serializable;

/**
 * 综合指数信息实体
 * 
 * @author zs
 */
@SuppressWarnings("serial")
public class SingleCompositeIndex implements Serializable {

    private String code;
    private String name;
    /**
     * 当前点数
     */
    private String currentPoint;
    /**
     * 当前价格
     */
    private String currentPrice;
    /**
     * 涨跌率
     */
    private String changeRate;
    /**
     * 成交量(手)
     */
    private String volume;
    /**
     * 成交额(万元)
     */
    private String turnover;

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

    public String getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(String currentPoint) {
        this.currentPoint = currentPoint;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    @Override
    public String toString() {
        return "SingleCompositeIndex [code=" + code + ", name=" + name + ", currentPoint=" + currentPoint
                        + ", currentPrice=" + currentPrice + ", changeRate=" + changeRate + ", volume=" + volume
                        + ", turnover=" + turnover + "]";
    }
}

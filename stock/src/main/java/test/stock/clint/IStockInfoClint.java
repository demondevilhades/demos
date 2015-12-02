package test.stock.clint;

import test.stock.bean.SingleStockInfo;

/**
 * 信息链接
 * 
 * @author zs
 */
public interface IStockInfoClint {

    /**
     * 获取信息
     * 
     * @return
     */
    public SingleStockInfo getSingleInfo(String stockCode);
}

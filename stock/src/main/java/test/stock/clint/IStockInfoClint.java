package test.stock.clint;

import java.util.List;

import test.stock.bean.SingleStockInfo;

/**
 * 信息链接
 * 
 * @author zs
 */
public interface IStockInfoClint {

    /**
     * 获取单个信息
     * 
     * @param stockCode
     * @return
     */
    public SingleStockInfo getSingleInfo(String stockCode);

    /**
     * 获取多个信息
     * 
     * @param stockCodeList
     * @return
     */
    public List<SingleStockInfo> getMultiInfo(List<String> stockCodeList);
}

package test.stock.clint;

import java.util.List;

import test.stock.bean.SingleCompositeIndex;
import test.stock.bean.SingleStockInfo;

/**
 * 信息链接
 * 
 * @author zs
 */
public interface IStockInfoClint {

    /**
     * 获取单个股票信息
     * 
     * @param stockCode
     *            代码
     * @return
     */
    public SingleStockInfo getSingleStockInfo(String stockCode);

    /**
     * 获取多个股票信息
     * 
     * @param stockCodeList
     *            代码
     * @return
     */
    public List<SingleStockInfo> getMultiStockInfo(List<String> stockCodeList);

    /**
     * 获取单个综合指数信息
     * 
     * @param indexCode
     *            代码
     * @param type
     *            类型 true:上证,false:深证
     * @return
     */
    public SingleCompositeIndex getSingleCompositeIndex(String indexCode, boolean type);

    /**
     * 获取多个综合指数信息(上证)
     * 
     * @param stockCodeList
     *            代码
     * @param indexCode
     *            类型 true:上证,false:深证
     * @return
     */
    public List<SingleCompositeIndex> getMultiCompositeIndex(List<String> indexCodeList, boolean type);
}

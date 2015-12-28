package test.stock.clint.impl.wmcloud;

import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;

import test.stock.bean.SingleCompositeIndex;
import test.stock.bean.SingleStockInfo;
import test.stock.clint.IStockInfoClient;

public class StockInfoClientImpl implements IStockInfoClient {
    private CloseableHttpClient httpClient = null;

    private static final String URL = "https://api.wmcloud.com:443/data/v1/";

    @Override
    public SingleStockInfo getSingleStockInfo(String stockCode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SingleStockInfo> getMultiStockInfo(List<String> stockCodeList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SingleCompositeIndex getSingleCompositeIndex(String indexCode, boolean type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SingleCompositeIndex> getMultiCompositeIndex(List<String> indexCodeList, boolean type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String[]> getInfoByDay(String date, String code) {
        // TODO Auto-generated method stub
        return null;
    }

}

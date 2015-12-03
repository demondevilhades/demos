package test.stock.clint.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import test.stock.bean.SingleCompositeIndex;
import test.stock.bean.SingleStockInfo;
import test.stock.clint.IStockInfoClint;

/**
 * 新浪信息链接
 * 
 * @author zs
 */
public class SinaStockInfoClint implements IStockInfoClint {

    private static final String URL = "http://hq.sinajs.cn/list=";

    public SinaStockInfoClint() {
    }

    @Override
    public SingleStockInfo getSingleStockInfo(String stockCode) {
        String reqUrl = URL + Type.SH.value() + stockCode;
        String info = httpGet(reqUrl);
        info = info.substring(info.indexOf("\"") + 1, info.lastIndexOf("\""));
        SingleStockInfo singleStockInfo = transStr2StockInfoBean(info);
        singleStockInfo.setCode(stockCode);
        return singleStockInfo;
    }

    @Override
    public List<SingleStockInfo> getMultiStockInfo(List<String> stockCodeList) {
        List<SingleStockInfo> stockInfoList = new LinkedList<SingleStockInfo>();
        StringBuilder sb = new StringBuilder(URL);
        for (String stockCode : stockCodeList) {
            sb.append(Type.SH.value()).append(stockCode).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String info = httpGet(sb.toString());
        String[] singleInfo = info.split("\n");
        if (singleInfo.length == stockCodeList.size()) {
            SingleStockInfo singleStockInfo = null;
            String str = null;
            for (int i = 0; i < singleInfo.length; i++) {
                str = singleInfo[i];
                str = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
                singleStockInfo = transStr2StockInfoBean(str);
                singleStockInfo.setCode(stockCodeList.get(i));
                stockInfoList.add(singleStockInfo);
            }
        }
        return stockInfoList;
    }

    @Override
    public SingleCompositeIndex getSingleCompositeIndex(String indexCode, boolean type) {
        String typeS = type ? Type.S_SH.value() : Type.S_SZ.value();
        String reqUrl = URL + typeS + indexCode;
        String info = httpGet(reqUrl);
        info = info.substring(info.indexOf("\"") + 1, info.lastIndexOf("\""));
        SingleCompositeIndex singleCompositeIndex = transStr2CompositeIndexBean(info);
        singleCompositeIndex.setCode(indexCode);
        return singleCompositeIndex;
    }

    @Override
    public List<SingleCompositeIndex> getMultiCompositeIndex(List<String> indexCodeList, boolean type) {
        String typeS = type ? Type.S_SH.value() : Type.S_SZ.value();
        List<SingleCompositeIndex> compositeIndexList = new LinkedList<SingleCompositeIndex>();
        StringBuilder sb = new StringBuilder(URL);
        for (String indexCode : indexCodeList) {
            sb.append(typeS).append(indexCode).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String info = httpGet(sb.toString());
        String[] singleInfo = info.split("\n");
        if (singleInfo.length == indexCodeList.size()) {
            SingleCompositeIndex singleCompositeIndex = null;
            String str = null;
            for (int i = 0; i < singleInfo.length; i++) {
                str = singleInfo[i];
                str = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
                singleCompositeIndex = transStr2CompositeIndexBean(str);
                singleCompositeIndex.setCode(indexCodeList.get(i));
                compositeIndexList.add(singleCompositeIndex);
            }
        }
        return compositeIndexList;
    }

    /**
     * http get 链接
     * 
     * @param reqUrl
     * @return
     */
    private String httpGet(String reqUrl) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(reqUrl);
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 转换为综合指数实体
     * 
     * @param info
     * @return
     */
    private SingleCompositeIndex transStr2CompositeIndexBean(String info) {
        SingleCompositeIndex singleCompositeIndex = new SingleCompositeIndex();
        String[] params = info.split(",");
        if (params.length == 6) {
            singleCompositeIndex.setName(params[0]);
            singleCompositeIndex.setCurrentPoint(params[1]);
            singleCompositeIndex.setCurrentPrice(params[2]);
            singleCompositeIndex.setChangeRate(params[3]);
            singleCompositeIndex.setVolume(params[4]);
            singleCompositeIndex.setTurnover(params[5]);
        }
        return singleCompositeIndex;
    }

    /**
     * 转换为股票信息实体
     * 
     * @param info
     * @return
     */
    private SingleStockInfo transStr2StockInfoBean(String info) {
        SingleStockInfo singleStockInfo = new SingleStockInfo();
        String[] params = info.split(",");
        if (params.length == 33) {
            singleStockInfo.setName(params[0]);
            singleStockInfo.setOpeningPrice(params[1]);
            singleStockInfo.setClosingPrice(params[2]);
            singleStockInfo.setCurrentPrice(params[3]);
            singleStockInfo.setHighestPrice(params[4]);
            singleStockInfo.setLowestPrice(params[5]);
            singleStockInfo.setBidPrice(params[6]);
            singleStockInfo.setSellPrice(params[7]);
            singleStockInfo.setTransNum(params[8]);
            singleStockInfo.setTurnover(params[9]);

            singleStockInfo.setBidNum1(params[10]);
            singleStockInfo.setBidPrice1(params[11]);
            singleStockInfo.setBidNum2(params[12]);
            singleStockInfo.setBidPrice2(params[13]);
            singleStockInfo.setBidNum3(params[14]);
            singleStockInfo.setBidPrice3(params[15]);
            singleStockInfo.setBidNum4(params[16]);
            singleStockInfo.setBidPrice4(params[17]);
            singleStockInfo.setBidNum5(params[18]);
            singleStockInfo.setBidPrice5(params[19]);

            singleStockInfo.setSellNum1(params[20]);
            singleStockInfo.setSellPrice1(params[21]);
            singleStockInfo.setSellNum2(params[22]);
            singleStockInfo.setSellPrice2(params[23]);
            singleStockInfo.setSellNum3(params[24]);
            singleStockInfo.setSellPrice3(params[25]);
            singleStockInfo.setSellNum4(params[26]);
            singleStockInfo.setSellPrice4(params[27]);
            singleStockInfo.setSellNum5(params[28]);
            singleStockInfo.setSellPrice5(params[29]);

            singleStockInfo.setDate(params[30]);
            singleStockInfo.setTime(params[31]);
        }
        return singleStockInfo;
    }

    /**
     * 信息类型
     * 
     * @author zs
     */
    public enum Type {
        /**
         * 股票
         */
        SH("sh"),
        /**
         * 上证指数
         */
        S_SH("s_sh"),
        /**
         * 深证指数
         */
        S_SZ("s_sz");
        private String str;

        private Type(String str) {
            this.str = str;
        }

        public String value() {
            return str;
        }
    }
}

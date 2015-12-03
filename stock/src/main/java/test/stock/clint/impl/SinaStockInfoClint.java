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

import test.stock.bean.SingleStockInfo;
import test.stock.clint.IStockInfoClint;

public class SinaStockInfoClint implements IStockInfoClint {

    private final String singleUrl = "http://hq.sinajs.cn/list=sh";

    private final String multiUrl = "http://hq.sinajs.cn/list=";

    @Override
    public SingleStockInfo getSingleInfo(String stockCode) {
        SingleStockInfo singleStockInfo = new SingleStockInfo();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(singleUrl + stockCode);
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    String info = EntityUtils.toString(entity);
                    info = info.substring(info.indexOf("\"") + 1, info.lastIndexOf("\""));
                    transStr2Bean(info, singleStockInfo);
                    singleStockInfo.setCode(stockCode);
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
        return singleStockInfo;
    }

    @Override
    public List<SingleStockInfo> getMultiInfo(List<String> stockCodeList) {
        List<SingleStockInfo> stockInfoList = new LinkedList<SingleStockInfo>();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        StringBuilder sb = new StringBuilder(multiUrl);
        for (String stockCode : stockCodeList) {
            sb.append("sh").append(stockCode).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        HttpGet httpGet = new HttpGet(sb.toString());
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    String info = EntityUtils.toString(entity);
                    String[] singleInfo = info.split("\n");
                    if (singleInfo.length == stockCodeList.size()) {
                        SingleStockInfo singleStockInfo = null;
                        String str = null;
                        for (int i = 0; i < singleInfo.length; i++) {
                            str = singleInfo[i];
                            singleStockInfo = new SingleStockInfo();
                            str = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
                            transStr2Bean(str, singleStockInfo);
                            singleStockInfo.setCode(stockCodeList.get(i));
                            stockInfoList.add(singleStockInfo);
                        }
                    }
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
        return stockInfoList;
    }

    private void transStr2Bean(String info, SingleStockInfo singleStockInfo) {
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
    }
}
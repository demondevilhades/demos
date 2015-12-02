package test.stock.clint.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import test.stock.clint.IStockInfoClint;

public class SinaStockInfoClint implements IStockInfoClint {

    private final String url = "http://hq.sinajs.cn/list=sh";

    @Override
    public String getSingleInfo(String stockCode) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(url + stockCode);
        System.out.println(httpGet.getRequestLine());
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            System.out.println("status:" + httpResponse.getStatusLine());
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                System.out.println("contentEncoding:" + entity.getContentEncoding());
                System.out.println("response content:" + EntityUtils.toString(entity));
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
}

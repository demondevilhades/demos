package test.stock.clint.impl.wmcloud;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 基金
 * 
 * @author hades
 */
public class Fund {
    private CloseableHttpClient httpClient = null;

    private static final String URL = "https://api.wmcloud.com:443/data/v1/";
    private static final String GET_FUND = "api/fund/getFund.json?field=${field}&secID=${secID}&etfLof=${etfLof}&listStatusCd=${listStatusCd}&ticker=${ticker}";
    private static final String GET_FUND_NAV = "/api/fund/getFundNav.json?field=&beginDate=20140101&endDate=20141231&secID=&ticker=000001&dataDate=";

    private static final String RET_CODE = "retCode";
    private static final String DATA = "data";

    public Fund() {
        httpClient = createHttpsClient();
    }

    @Override
    protected void finalize() throws Throwable {
        httpClient.close();
    }

    public String getFundJsonStr(String ticker) {
        return getFundJsonStr(ticker, null, null, null, null);
    }

    public JSONArray getFundJson(String ticker, String listStatusCd, String etfLof, String secID, String field) {
        JSONArray jsonArray = new JSONArray();
        String jsonStr = getFundJsonStr(ticker, listStatusCd, etfLof, secID, field);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if (jsonObject.getIntValue(RET_CODE) == 1) {
            jsonArray = jsonObject.getJSONArray(DATA);
        }
        return jsonArray;
    }

    /**
     * 获取基本基金信息
     * 
     * @param ticker
     * @param listStatusCd
     * @param etfLof
     * @param secID
     * @param field
     * @return
     */
    public String getFundJsonStr(String ticker, String listStatusCd, String etfLof, String secID, String field) {
        String result = "";
        String url = getFundUrl(ticker, listStatusCd, etfLof, secID, field);

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Bearer " + Conf.ACCESS_TOKEN);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String getFundInfoStr(String ticker) {
        String result = "";
        StringBuilder sb = new StringBuilder(URL);
        sb.append("api/market/getMktFundd.json?field=&ticker=").append(ticker);
        String url = sb.toString();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Bearer " + Conf.ACCESS_TOKEN);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String getFundUrl(String ticker, String listStatusCd, String etfLof, String secID, String field) {
        StringBuilder sb = new StringBuilder(URL);
        sb.append("api/fund/getFund.json?field=");
        if (StringUtils.isNotEmpty(field)) {
            sb.append(field);
        }
        sb.append("&secID=");
        if (StringUtils.isNotEmpty(secID)) {
            sb.append(secID);
        }
        sb.append("&etfLof=");
        if (StringUtils.isNotEmpty(etfLof)) {
            sb.append(etfLof);
        }
        sb.append("&listStatusCd=");
        if (StringUtils.isNotEmpty(listStatusCd)) {
            sb.append(listStatusCd);
        }
        sb.append("&ticker=");
        if (StringUtils.isNotEmpty(ticker)) {
            sb.append(ticker);
        }
        return sb.toString();
    }

    private CloseableHttpClient createHttpsClient() {
        X509TrustManager x509mgr = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { x509mgr }, null);
            sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }
}

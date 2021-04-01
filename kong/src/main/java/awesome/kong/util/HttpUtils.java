package awesome.kong.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author awesome
 */
public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public static String httpGet(String url, Map<String, String> headers) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        LOGGER.info(httpGet.toString());
        CloseableHttpClient httpClient = HttpClients.custom().build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            StatusLine statusLine = response.getStatusLine();
            StringBuilder sb = new StringBuilder();
            sb.append("ProtocolVersion=").append(statusLine.getProtocolVersion().toString()).append(", ReasonPhrase=")
                    .append(statusLine.getReasonPhrase()).append(", StatusCode=").append(statusLine.getStatusCode());
            LOGGER.info(sb.toString());
            return EntityUtils.toString(response.getEntity());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
            httpGet.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    /**
     * 
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws IOException
     */
    public static String httpPost(String url, Map<String, String> headers, Map<String, String> params)
            throws IOException {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            sslsf = new SSLConnectionSocketFactory(
                    builder.build());
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        
        
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (params != null) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
        }
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            StringBuilder sb = new StringBuilder();
            sb.append("ProtocolVersion=").append(statusLine.getProtocolVersion().toString()).append(", ReasonPhrase=")
                    .append(statusLine.getReasonPhrase()).append(", StatusCode=").append(statusLine.getStatusCode());
            LOGGER.info(sb.toString());
            return EntityUtils.toString(response.getEntity());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    /**
     * 
     * @param url
     * @param headers
     * @param obj
     * @return
     * @throws IOException
     */
    public static String httpPostJson(String url, Map<String, String> headers, Object obj) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setHeader("Content-Type", "application/json");
        if (obj != null) {
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(obj), Charsets.UTF_8));
        }
        CloseableHttpClient httpClient = HttpClients.custom().build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            StringBuilder sb = new StringBuilder();
            sb.append("ProtocolVersion=").append(statusLine.getProtocolVersion().toString()).append(", ReasonPhrase=")
                    .append(statusLine.getReasonPhrase()).append(", StatusCode=").append(statusLine.getStatusCode());
            LOGGER.info(sb.toString());
            return EntityUtils.toString(response.getEntity());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    /**
     * 
     * @param url
     * @param headers
     * @param str
     * @return
     * @throws IOException
     */
    public static String httpPostJsonStr(String url, Map<String, String> headers, String str) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setHeader("Content-Type", "application/json");
        if(str != null) {
            httpPost.setEntity(new StringEntity(str, Charsets.UTF_8));
        }
        CloseableHttpClient httpClient = HttpClients.custom().build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            StringBuilder sb = new StringBuilder();
            sb.append("ProtocolVersion=").append(statusLine.getProtocolVersion().toString()).append(", ReasonPhrase=")
                    .append(statusLine.getReasonPhrase()).append(", StatusCode=").append(statusLine.getStatusCode());
            LOGGER.info(sb.toString());
            return EntityUtils.toString(response.getEntity());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }
}

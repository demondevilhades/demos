package test.httpclient;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpsClientTest {

    public String get(String url) {
        CloseableHttpClient closeableHttpClient = SSLClientNew.getCloseableHttpClient();
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse closeableHttpResponse = null;
        String str = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                str = EntityUtils.toString(closeableHttpResponse.getEntity());
            } else {
                // TODO
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (closeableHttpResponse != null) {
                    closeableHttpResponse.close();
                }
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public void post(String url) {
        CloseableHttpClient closeableHttpClient = SSLClientNew.getCloseableHttpClient();
        HttpPost httpPost = new HttpPost(url);
        // TODO
    }
}

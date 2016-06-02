package test.httpclient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {

    public RequestConfig setConnectTimeout(int connectTimeout) {
        return RequestConfig.custom().setConnectTimeout(connectTimeout).build();
    }

    public String httpGet(String url, RequestConfig requestConfig) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        if (requestConfig != null) {
            httpGet.setConfig(requestConfig);
        }
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

    public String httpPost(String url, Header header, Map<String, String> params, RequestConfig requestConfig) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (requestConfig != null) {
            httpPost.setConfig(requestConfig);
        }
        CloseableHttpResponse closeableHttpResponse = null;
        String str = null;
        try {
            httpPost.addHeader(header);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            closeableHttpResponse = closeableHttpClient.execute(httpPost);
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

    public void download(String url, RequestConfig requestConfig, String filePath) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        if (requestConfig != null) {
            httpGet.setConfig(requestConfig);
        }
        CloseableHttpResponse closeableHttpResponse = null;

        InputStream is = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                is = closeableHttpResponse.getEntity().getContent();
                bis = new BufferedInputStream(is);

                File file = new File(filePath);
                fos = new FileOutputStream(file);

                byte[] buffer = new byte[1024 * 10];
                int len = bis.read(buffer);
                while (len != -1) {
                    fos.write(buffer, 0, len);
                    len = bis.read(buffer);
                }
            } else {
                // TODO
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (is != null) {
                    is.close();
                }
                if (closeableHttpResponse != null) {
                    closeableHttpResponse.close();
                }
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String upload(String url, RequestConfig requestConfig, String filePath) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        if (requestConfig != null) {
            httpPut.setConfig(requestConfig);
        }
        CloseableHttpResponse closeableHttpResponse = null;
        String str = null;

        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            httpPut.setEntity(new InputStreamEntity(fis));

            closeableHttpResponse = closeableHttpClient.execute(httpPut);
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
                if (fis != null) {
                    fis.close();
                }
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
}

package com.hades.jsouptest;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

public class HttpClientTest {
    
    public void run(){
        HttpGet get = new HttpGet("http://test");
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("EKM_SN", "ddd807b107fb4900b0dcdf58b49c9d17");
        cookie.setDomain(get.getURI().getHost());
        cookieStore.addCookie(cookie);
        cookie = new BasicClientCookie("JSESSIONID", "A07EBE7B4FFE0244B3AEFC54824657DA");
        cookie.setDomain(get.getURI().getHost());
        cookieStore.addCookie(cookie);

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);

            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                System.out.println(header.getName() + "\t" + header.getValue());
            }
            System.out.println(cookieStore);
            get.releaseConnection();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                get.releaseConnection();
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        HttpPost post = new HttpPost("http://test" + System.currentTimeMillis());
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("EKM_SN", "ddd807b107fb4900b0dcdf58b49c9d17");
        cookie.setDomain(post.getURI().getHost());
        cookieStore.addCookie(cookie);
        cookie = new BasicClientCookie("JSESSIONID", "A07EBE7B4FFE0244B3AEFC54824657DA");
        cookie.setDomain(post.getURI().getHost());
        cookieStore.addCookie(cookie);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.addHeader("X-Requested-With", "XMLHttpRequest");

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = null;
        
        String str = "_ajax_=_7b__22_username_22__3a__22_0096001654_22__2c__22_password_22__3a__22_Zs123456_22__7d_";
        try {
            HttpEntity entity = new StringEntity(str, "UTF-8");
            post.setEntity(entity);
            response = httpClient.execute(post);

            System.out.println(response.getStatusLine().getStatusCode());
            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                System.out.println(header.getName() + "\t" + header.getValue());
            }
            System.out.println(cookieStore);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                post.releaseConnection();
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

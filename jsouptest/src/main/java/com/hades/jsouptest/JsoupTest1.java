package com.hades.jsouptest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest1 {

    private String loginUrl = "https://www.oschina.net/home/login";

    public void run() throws IOException {
        Connection conn = Jsoup.connect(loginUrl);
        conn.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
        Response response = conn.method(Method.GET).execute();
        Map<String, String> cookies = response.cookies();
        Document dom = response.parse();

        Map<String, String> dataMap = new HashMap<String, String>();
        Element loginFormElement = dom.getElementsByClass("login-form").get(0);
        Elements elements = loginFormElement.getElementsByTag("input");
        for (Element element : elements) {
            if("userMail".equals(element.id())){
                element.attr("value", "test_name");
                dataMap.put(element.attr("name"), element.attr("value"));
            }
            if("userPassword".equals(element.id())){
                element.attr("value", "test_pwd");
                dataMap.put(element.attr("name"), element.attr("value"));
            }
            if("f_vcode".equals(element.id())){
                element.attr("value", "test_f_vcode");
                dataMap.put(element.attr("name"), element.attr("value"));
            }
            System.out.println(element);
            System.out.println("---");
        }
        
        conn = Jsoup.connect(loginUrl);
        conn.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
        response = conn.ignoreContentType(true).method(Method.POST).data(dataMap).cookies(cookies).execute();
        System.out.println(response.body());
        if(true){//success
            cookies = response.cookies();
        }
    }

    public static void main(String[] args) throws Exception {
        System.getProperties().setProperty("http.proxyHost", "proxy.zte.com.cn");
        System.getProperties().setProperty("http.proxyPort", "80");
        System.getProperties().setProperty("https.proxyHost", "proxy.zte.com.cn");
        System.getProperties().setProperty("https.proxyPort", "80");
        System.getProperties().setProperty("proxySet", "true");
        new JsoupTest1().run();
    }
}

package com.hades.jsouptest;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest2 {

    private String url = "https://www.oschina.net/question/tag/java";

    public void run() throws IOException {
        Connection conn = Jsoup.connect(url);
        conn.data("show", "time");
        conn.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
        Response response = conn.method(Method.GET).execute();
        Document dom = response.parse();

        Element qlElement = dom.getElementsByClass("question_list").get(0);
        Elements aElements = qlElement.getElementsByTag("a");
        for (Element element : aElements) {
            Elements h4Elements = element.getElementsByTag("h4");
            if (h4Elements.size() > 0) {
                System.out.println(h4Elements.get(0).text());
                System.out.println("---");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.getProperties().setProperty("http.proxyHost", "proxy.***.com.cn");
        System.getProperties().setProperty("http.proxyPort", "80");
        System.getProperties().setProperty("https.proxyHost", "proxy.***.com.cn");
        System.getProperties().setProperty("https.proxyPort", "80");
        System.getProperties().setProperty("proxySet", "true");
        new JsoupTest2().run();
    }
}

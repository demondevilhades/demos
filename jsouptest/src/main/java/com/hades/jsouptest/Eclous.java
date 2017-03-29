package com.hades.jsouptest;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Eclous {

    private final Logger logger = Logger.getLogger(this.getClass());

    private final String loginUrl = "http://zteict.in.eclous.com/login";
    private final String loginSubmitUrl = "http://zteict.in.eclous.com/login.loginform";
    private final String libqueryUrl = "http://zteict.in.eclous.com/front/course/libquery?$pz=";

    public void run() throws Exception {
        logger.info("run start");
        Map<String, String> cookies = login("0096001654", "Zs123456");
        if (cookies != null) {
            Thread.sleep(1000);
            runDetails(cookies);
        }
    }

    private void runDetails(Map<String, String> cookies) throws Exception {
        Connection conn;
        Response response;
        Document dom;

        // first
        conn = Jsoup.connect(libqueryUrl + "1-60");
        conn.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
        response = conn.method(Method.GET).cookies(cookies).execute();

        // logger.info(response.body());

        dom = response.parse();
        Element innerElement = dom.getElementsByClass("body-wrap").get(0).getElementsByClass("container").get(0)
                .getElementsByClass("row module").get(0).getElementsByClass("main-wrap").get(0)
                .getElementsByClass("inner").get(0);
        // logger.info(innerElement);

        Elements hovables = innerElement.getElementById("pager").getElementsByClass("hovable");
        // logger.info(hovables);
        int pageSize = 1;
        for (Element hovable : hovables) {
            String classVaule = hovable.attr("class");
            if ("hovable".equals(classVaule)) {
                String text = hovable.text();
                if (StringUtils.isNotEmpty(text)) {
                    int i = Integer.parseInt(text);
                    if (i > pageSize) {
                        pageSize = i;
                    }
                }
            }
        }
        logger.info("pageSize : " + pageSize);

        // begin loop
        int i = 1;
        while (i <= 19) {
            Thread.sleep(1000);
            conn = Jsoup.connect(libqueryUrl + i + "-60");
            conn.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
            response = conn.method(Method.GET).cookies(cookies).execute();
            dom = response.parse();
            Element listElement = dom.getElementsByClass("general-list clearfix").get(0).getElementsByTag("ul").get(0);
            Elements itemElements = listElement.getElementsByTag("li");
            for (Element itemElement : itemElements) {
                Element infoElement = itemElement.getElementsByClass("info").get(0);
                String title = infoElement.getElementsByClass("title text-primary inline-block text-autocat").get(0)
                        .text();
                String credit = infoElement.getElementsByClass("desc mt-5 cf").get(0).getElementsByClass("pull-right")
                        .get(0).text().split(":")[1];
                String price = infoElement.getElementsByClass("mt-5 clearfix").get(0)
                        .getElementsByClass("course-price").get(0).text().replaceAll("\"", "");
                String status = infoElement.getElementsByClass("mt-5 clearfix").get(0).getElementsByClass("pull-right")
                        .get(0).getElementsByClass("btn btn-primary btn-sm").get(0).text();
                System.out.println(title + "\t" + credit + "\t" + price + "\t" + status);
            }
            i++;
        }
    }

    private Map<String, String> login(String username, String password) throws Exception {
        Connection conn;
        Response response;
        // pre
        conn = Jsoup.connect(loginUrl);
        conn.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
        response = conn.method(Method.GET).execute();
        // cookies = response.cookies();

        Thread.sleep(1000);
        // login
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("_ajax_", "_7b__22_username_22__3a__22_0096001654_22__2c__22_password_22__3a__22_Zs123456_22__7d_");
        conn = Jsoup.connect(loginSubmitUrl);
        conn.header("X-Requested-With", "XMLHttpRequest");
        conn.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
        response = conn.ignoreContentType(true).method(Method.POST).data(dataMap).execute();
        int statusCode = response.statusCode();
        if (statusCode == 200) {
            logger.info("login success");
            return response.cookies();
        }
        logger.error(response.body());
        return null;
    }

    public static void main(String[] args) throws Exception {
        new Eclous().run();
    }
}

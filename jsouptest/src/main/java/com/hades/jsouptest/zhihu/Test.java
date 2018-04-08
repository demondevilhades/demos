package com.hades.jsouptest.zhihu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

    private final Logger logger = Logger.getLogger(this.getClass());

    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> cookies = new HashMap<String, String>();

    private final String baseUrl = "https://www.zhihu.com/";

    public void run() throws IOException {
        initHeaders();
        initCookies();

        Response response = Jsoup
                .connect(baseUrl)
                .userAgent(
                        "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36")
                .headers(headers).cookies(cookies).method(Method.GET).execute();
        headers = response.headers();
        cookies = response.cookies();
        if (headers != null) {
            logger.info("headers");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
        if (cookies != null) {
            logger.info("cookies");
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }

        Document dom = response.parse();
        logger.info("dom");
        // System.out.println(dom);
        Elements feeds = dom
                .select("main > div.Topstory > div.Topstory-container > div.Topstory-mainColumn > div.TopstoryMain > div > div.TopstoryItem > div.Feed");
        Elements es;
        for (Element feed : feeds) {
            // answer
            es = feed.select(" > div.FeedSource > div.FeedSource-firstline a.UserLink-link");
            if (es != null && es.size() > 0) {
                Element a = es.get(0);
                System.out.println(a.text() + " " + getUrl(a.attr("href")));
            }
            // topic
            es = feed.select(" > div.FeedSource > div.FeedSource-firstline a.TopicLink");
            if (es != null && es.size() > 0) {
                Element a = es.get(0);
                System.out.println(a.text() + " " + getUrl(a.attr("href")));
            }
            // user
            es = feed
                    .select(" > div.FeedSource > div.AuthorInfo > div.AuthorInfo-content > div.AuthorInfo-head a.UserLink-link");
            if (es != null && es.size() > 0) {
                Element a = es.get(0);
                System.out.println(a.text() + " " + getUrl(a.attr("href")));
            } else {
                es = feed.select(" > div.FeedSource > div.AuthorInfo > div.AuthorInfo-content > div.AuthorInfo-head");
                if (es != null && es.size() > 0) {
                    Element a = es.get(0);
                    System.out.println(a.text() + " " + getUrl(a.attr("href")));
                }
            }
            // title
            es = feed.select(" > div.ContentItem > h2.ContentItem-title a");
            if (es != null && es.size() > 0) {
                Element a = es.get(0);
                System.out.println(a.text() + " " + getUrl(a.attr("href")));
                
                // like
                es = feed
                        .select(" > div.ContentItem > div.RichContent > div.ContentItem-actions > button.LikeButton");
                String like = "";
                if (es != null && es.size() > 0) {
                    like = es.get(0).text();
                }
                
                if(StringUtils.isNotEmpty(like)){
                    System.out.println(like);
                } else {
                    // up & down
                    es = feed
                            .select(" > div.ContentItem > div.RichContent > div.ContentItem-actions > span > button.VoteButton--up");
                    String vUp = "";
                    if (es != null && es.size() > 0) {
                        vUp = es.get(0).text();
                    }
                    es = feed
                            .select(" > div.ContentItem > div.RichContent > div.ContentItem-actions > span > button.VoteButton--down");
                    String vDown = "";
                    if (es != null && es.size() > 0) {
                        vDown = es.get(0).text();
                    }
                    System.out.println(vUp + " " + vDown);
                }
            }
        }
    }

    private void initHeaders() {
        // headers.put("", "");
    }

    private void initCookies() {
        cookies.put("d_c0", "");
        cookies.put("_zap", "");
        cookies.put("__utma", "");
        cookies.put("__utmz", "");
        cookies.put("__utmv", "");
        cookies.put(
                "z_c0",
                "");
        cookies.put("__DAYU_PP", "");
        cookies.put("aliyungf_tc", "");
        cookies.put("_xsrf", "");
    }

    private String getUrl(String href) {
        if (href.startsWith("//")) {
            return "https:" + href;
        } else if (href.startsWith("/")) {
            return "https://www.zhihu.com" + href;
        }
        return href;
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("http.proxyHost", "proxy.***.com.cn");
        System.setProperty("http.proxyPort", "80");
        System.setProperty("https.proxyHost", "proxy.***.com.cn");
        System.setProperty("https.proxyPort", "80");
        new Test().run();
    }
}

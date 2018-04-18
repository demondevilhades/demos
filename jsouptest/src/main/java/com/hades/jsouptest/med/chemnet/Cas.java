package com.hades.jsouptest.med.chemnet;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hades.jsouptest.med.yaozui.util.SleepUtil;

/**
 * Chemical Abstract Services 美国化学文摘服务社 为化学物质制订的登记号
 * 
 * @author hades
 */
public class Cas {
    @SuppressWarnings("unused")
    private final Logger logger = Logger.getLogger(this.getClass());

    private final String rootUrl = "http://cheman.chemnet.com";
    private final String baseUrl = rootUrl + "/dict/zd_more.html";

    public void run() throws Exception {
        Document doc = connAndParse(baseUrl);
        Elements as = doc.select("div.hcent_zd3_c > ul > li > a");
        for (Element a : as) {
            // TODO

        }
    }

    private Document connAndParse(String url) throws IOException {
        Document doc = null;
        while (doc == null) {
            try {
                doc = Jsoup
                        .connect(url)
                        .timeout(30000)
                        .userAgent(
                                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36")
                        .execute().parse();
            } catch (Exception e) {
                e.printStackTrace();
                SleepUtil.sleep60();
            }
        }
        return doc;
    }

    public static void main(String[] args) throws Exception {
        new Cas().run();
    }
}

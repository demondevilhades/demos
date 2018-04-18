package com.hades.jsouptest.med.chemnet;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hades.jsouptest.med.yaozui.util.SleepUtil;

public class Act {
    @SuppressWarnings("unused")
    private final Logger logger = Logger.getLogger(this.getClass());

    private final String rootUrl = "http://cheman.chemnet.com";
    private final String baseUrl = rootUrl + "/atc/";

    public void run() throws Exception {
        Document doc = connAndParse(baseUrl);
        Elements uls = doc.select("div.cp > ul");
        for (Element ul : uls) {
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
        new Act().run();
    }
}

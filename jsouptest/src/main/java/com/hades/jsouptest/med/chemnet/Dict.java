package com.hades.jsouptest.med.chemnet;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hades.jsouptest.med.yaozui.util.SleepUtil;

/**
 * International Nonproprietary Names for Pharmaceutical Substances 国际非专有药名<br>
 * Proposed INN<br>
 * Recommended INN<br>
 * 
 * @author hades
 */
public class Dict {
    private final Logger logger = Logger.getLogger(this.getClass());

    private final String rootUrl = "http://cheman.chemnet.com";
    private final String baseUrl = rootUrl + "/drug_dict/";

    public void run() throws Exception {
        Document doc = connAndParse(baseUrl);
        Element innTable = doc.select("div.hcent_m1_2 > dl > dd > table").get(0);
        Elements as = innTable.select("> tbody > tr > td > a");
        for (Element a : as) {
            String name = a.text();
            logger.info("run : " + name);
            parseListPage(name, rootUrl + a.attr("href"));
        }
    }

    private void parseListPage(String name, String pageListUrl) throws Exception {
        while (pageListUrl != null) {
            logger.info("parseListPage : " + pageListUrl);
            Document doc = connAndParse(pageListUrl);
            Elements trs = doc.select("div.hcentg1 > div > table > tbody > tr > td > table > tbody > tr");
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                String td0Str = tds.get(0).text().trim();
                if (!"药品英文名".equals(td0Str)) {
                    String td1Str = tds.get(1).text().trim();
                    String td2Str = tds.get(2).text().trim();

//                    DBHelper.executeUpdateExp(
//                            "INSERT INTO med_inn (inn, enname, chname, efficacy) VALUES (?, ?, ?, ?)", new String[] {
//                                    name, td0Str, td1Str, td2Str });
                }
            }

            SleepUtil.sleep60();
            // nextPage
            pageListUrl = null;
            Elements as = doc.getElementById("iPage").select("a");
            for (Element a : as) {
                if ("下一页".equals(a.text().trim())) {
                    pageListUrl = rootUrl + a.attr("href");
                    break;
                }
            }
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
        System.getProperties().setProperty("http.proxyHost", "proxy.zte.com.cn");
        System.getProperties().setProperty("http.proxyPort", "80");
        System.getProperties().setProperty("https.proxyHost", "proxy.zte.com.cn");
        System.getProperties().setProperty("https.proxyPort", "80");
        System.getProperties().setProperty("proxySet", "true");
        new Dict().run();
    }
}

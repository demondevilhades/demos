package com.hades.jsouptest.med.chemnet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
 * �闩, 闩 : 呫诺<br>
 * �f : 噁<br>
 * 嗪<br>
 * �� : 唵<br>
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
        boolean runFlag = false;
        for (Element a : as) {
            String name = a.text().trim();
            if ("r-INNList-27".equals(name)) {
                runFlag = true;
            }
            if (!runFlag) {
                continue;
            }
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

                    System.out.println(name + "\t" + td0Str + "\t" + td1Str + "\t" + td2Str);
//                    System.out.println(new String(name.getBytes("gbk"), "utf-8") + "\t" + new String(td0Str.getBytes("gbk"), "utf-8")
//                            + "\t" + new String(td1Str.getBytes("gbk"), "utf-8") + "\t" + new String(td2Str.getBytes("gbk"), "utf-8"));
//                    DBHelper.executeUpdateExp(
//                            "INSERT INTO med_inn (inn, enname, chname, efficacy) VALUES (?, ?, ?, ?)", new String[] {
//                                    name, td0Str, td1Str, td2Str });
                }
            }

//            SleepUtil.sleep60();
            // nextPage
            pageListUrl = null;
//            Elements as = doc.getElementById("iPage").select("a");
//            for (Element a : as) {
//                if ("下一页".equals(a.text().trim())) {
//                    pageListUrl = rootUrl + a.attr("href");
//                    break;
//                }
//            }
        }
    }

    private Document connAndParse(String urlStr) throws IOException {
        Document doc = null;
        URL url = new URL(urlStr);
        while (doc == null) {
            HttpURLConnection connection = null;
            InputStream is = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(30000);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");
                connection.connect();
                is = connection.getInputStream();
                doc = Jsoup.parse(is, "gbk", urlStr);
//                doc = Jsoup
//                        .connect(url)
//                        .timeout(30000)
//                        .userAgent(
//                                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36")
//                        .execute().parse();
            } catch (Exception e) {
                e.printStackTrace();
                SleepUtil.sleep60();
            } finally {
                try {
                    if(is != null){
                        is.close();
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return doc;
    }

    public static void main(String[] args) throws Exception {
        System.getProperties().setProperty("http.proxyHost", "proxy.***.com.cn");
        System.getProperties().setProperty("http.proxyPort", "80");
        System.getProperties().setProperty("https.proxyHost", "proxy.***.com.cn");
        System.getProperties().setProperty("https.proxyPort", "80");
        System.getProperties().setProperty("proxySet", "true");
//        new Dict().run();
        new Dict().parseListPage("r-INNList-17", "http://cheman.chemnet.com/drug_dict/search.cgi?p=7&&f=search&c=r_inn&t=drug_dict&terms=r-INNList-17");
    }
}

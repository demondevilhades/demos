package com.hades.jsouptest;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RegionCode {
    private final Logger logger = Logger.getLogger(this.getClass());

    private final String rootUrl = "http://www.stats.gov.cn";
    private final String baseUrl = rootUrl + "/tjsj/tjbz/tjyqhdmhcxhfdm/2016/";// http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/index.html

    private int id = 1;
    private final int baseRetryNum = 5;

    // city county town village

    public void run() throws Exception {
        logger.info("start : " + baseUrl);
        Document doc = Jsoup.parse(baseUrl);
        Elements as = doc.select("table.provincetable tr.provincetr td a");
        for (Element a : as) {
            String name = a.text().trim();
            String idStr = String.valueOf(id++);
            insert(idStr, null, name, null, null);
            parseCity(idStr, name, baseUrl + a.attr("href"));
        }
    }

    private void parseCity(String parentId, String pName, String url) throws SQLException {
        logger.info(pName + "\t" + url);
        Elements trs = null;
        int retryNum = baseRetryNum;
        while (trs == null && retryNum-- > 0) {
            Document doc = Jsoup.parse(url);
            trs = doc.select("table.citytable tr.citytr");
        }
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            String code = tds.get(0).text().trim();
            String name = tds.get(1).text().trim();
            String idStr = String.valueOf(id++);
            insert(idStr, code, name, null, parentId);
            Elements as = tr.select("a");
            if (as != null && as.size() > 0) {
                parseCounty(idStr, code, parentUrl(url) + as.get(0).attr("href"));
            }
        }
        if (trs.size() == 0) {
            throw new RuntimeException();
        }
    }

    private void parseCounty(String parentId, String cityCode, String url) throws SQLException {
        logger.info(cityCode + "\t" + url);
        Elements trs = null;
        int retryNum = baseRetryNum;
        while (trs == null && retryNum-- > 0) {
            Document doc = Jsoup.parse(url);
            trs = doc.select("table.countytable tr.countytr");
        }
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            String code = tds.get(0).text().trim();
            String name = tds.get(1).text().trim();
            String idStr = String.valueOf(id++);
            insert(idStr, code, name, null, parentId);

            Elements as = tr.select("a");
            if (as != null && as.size() > 0) {
                parseTown(idStr, code, parentUrl(url) + as.get(0).attr("href"));
            }
        }
        if (trs.size() == 0) {
            throw new RuntimeException();
        }
    }

    private void parseTown(String parentId, String countyCode, String url) throws SQLException {
        logger.info(countyCode + "\t" + url);
        Elements trs = null;
        int retryNum = baseRetryNum;
        while (trs == null && retryNum-- > 0) {
            Document doc = Jsoup.parse(url);
            trs = doc.select("table.towntable tr.towntr");
        }
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            String code = tds.get(0).text().trim();
            String name = tds.get(1).text().trim();
            String idStr = String.valueOf(id++);
            insert(idStr, code, name, null, parentId);

            Elements as = tr.select("a");
            if (as != null && as.size() > 0) {
                parseVillage(idStr, code, parentUrl(url) + as.get(0).attr("href"));
            }
        }
        if (trs.size() == 0) {
            throw new RuntimeException();
        }
    }

    private void parseVillage(String parentId, String townCode, String url) throws SQLException {
        logger.info(townCode + "\t" + url);
        Elements trs = null;
        int retryNum = baseRetryNum;
        while (trs == null && retryNum-- > 0) {
            Document doc = Jsoup.parse(url);
            trs = doc.select("table.villagetable tr.villagetr");
        }
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            String code = tds.get(0).text().trim();
            String code2 = tds.get(1).text().trim();
            String name = tds.get(2).text().trim();
            String idStr = String.valueOf(id++);
            insert(idStr, code, name, code2, parentId);
        }
        logger.info(trs.size());
        if (trs.size() == 0) {
            throw new RuntimeException();
        }
    }

    private String parentUrl(String url) {
        return url.substring(0, url.lastIndexOf("/") + 1);
    }

    private void insert(String id, String code, String name, String type_code, String parent_id) throws SQLException {
        // TODO
    }

    public static void main(String[] args) throws Exception {
        new RegionCode().run();
    }
}

package com.hades.jsouptest.med.yaozui;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hades.jsouptest.med.yaozui.util.FileReader;
import com.hades.jsouptest.med.yaozui.util.FileWriter;
import com.hades.jsouptest.med.yaozui.util.Persistence;
import com.hades.jsouptest.med.yaozui.util.SleepUtil;

public class Yaopin {
    private final Logger logger = Logger.getLogger(this.getClass());

    private final String rootUrl = "https://www.yaozui.com";
    private final String baseUrl = rootUrl + "/yaopin/shouzi";// https://www.yaozui.com/yaopin/shouzi

    private final String charsetName = "utf-8";
    private final Charset charset = Charset.forName(charsetName);

    private final boolean continueFlag = true;
    private final String baseTempdir = "temp/";
    private final String tempdir = baseTempdir + "yaopin/";
    private final String continuePathStr = baseTempdir + "/yaopin_continue.txt";
    private final String listPathStr = baseTempdir + "/yaopin_list.txt";

    private FileWriter fw;

    private final Rel rel = new Rel();

    public void run() throws Exception {
        logger.info("start : " + baseUrl);

        // init
        File continueFile = new File(continuePathStr);
        logger.info("continueFlag : " + continueFlag);
        String continueShouzi = null;
        if (continueFlag) {
            if (continueFile.exists()) {
                FileReader fr = new FileReader(continueFile);
                String line = fr.readLine();
                fr.close();
                if (StringUtils.isNoneEmpty(line)) {
                    String[] split = line.split("\t");
                    continueShouzi = split[0];
                }
                logger.info("continue : " + line);
            }
        }

        String shouzi = null;
        try {
            File listFile = new File(listPathStr);
            if (!listFile.exists()) {
                listFile.createNewFile();
            }
            fw = new FileWriter(listFile, true);
            // parse
            Document doc = Jsoup.connect(baseUrl).execute().parse();
            Elements shouziAs = doc.select("div.container > div.row > div.col-md-10 > div.row > div.col-md-1 a");
            for (Element shouziA : shouziAs) {
                shouzi = shouziA.text().trim();
                if (continueShouzi != null) {
                    if (continueShouzi.equals(shouzi)) {
                        continueShouzi = null;
                    } else {
                        continue;
                    }
                }
                String shouziUrl = shouziA.attr("href");
                logger.info(shouzi + "\t" + shouziUrl);
                String url = parseShouzi(shouziUrl);
                while (url != null) {
                    logger.info(shouzi + "\t" + shouziUrl);
                    url = parseShouzi(url);
                }
            }
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            if (fw != null) {
                fw.close();
            }
            continueFile.delete();
            if (shouzi != null) {
                FileWriter continueFw = null;
                try {
                    continueFw = new FileWriter(continueFile);
                    continueFw.writeLine(new StringBuilder().append(shouzi).toString());
                } catch (IOException e) {
                    logger.error("", e);
                } finally {
                    if (continueFw != null) {
                        continueFw.close();
                    }
                }
            }
        }
    }

    private String parseShouzi(String shouziUrl) throws Exception {
        Document listDoc = Jsoup.connect(shouziUrl).execute().parse();
        Elements as = listDoc.select("div.main > div.container > div.row > div.col-md-9 > div.row > div.col-md-3 > a");
        for (Element a : as) {
            String name = a.text();
            parseYaopin(name, a.attr("href"));
        }
        Elements pageAs = listDoc
                .select("div.main > div.container > div.row > div.col-md-9 > ul.pagination > li > a");
        if (pageAs != null && pageAs.size() > 0) {
            for (Element pageA : pageAs) {
                if ("next".equals(pageA.attr("rel"))) {
                    return rootUrl + pageA.attr("href");
                }
            }
        }
        return null;
    }

    private void parseYaopin(String name, String url) throws Exception {
        logger.info(name + "\t" + url);
        String code = DigestUtils.md5Hex(url);
        File pageFile = new File(new StringBuilder().append(tempdir).append(code).toString());

        Document doc = null;
        if (!pageFile.exists()) {
            // SleepUtil.sleep20();
            String pageStr = Jsoup.connect(url).execute().parse().toString();
            int retry = 3;
            while (StringUtils.isEmpty(pageStr) && retry-- > 0) {
                SleepUtil.sleep20();
                pageStr = Jsoup.connect(url).execute().parse().toString();
            }
            if (StringUtils.isEmpty(pageStr)) {
                throw new RuntimeException();
            }
            Persistence.write2file(pageFile, charset, pageStr);
            fw.writeLine(new StringBuilder().append(code).append("\t").append(name).append("\t").append(url).toString());
            doc = Jsoup.parse(pageStr, url);
        } else {
            doc = Jsoup.parse(pageFile, charsetName, url);
        }

        StringBuilder indicationsSb = new StringBuilder();
        String desc = null;
        String dosage = null;
        String reactions = null;
        String matter = null;
        StringBuilder ingredientSb = new StringBuilder();

        Elements jibings = doc
                .select("div.main-container > section > div.container > div.row > div.col-content > div.row > div.col-xs-12 > div.jibings > a");
        if (jibings != null && jibings.size() > 0) {
            boolean first = true;
            for (Element jibing : jibings) {
                if (first) {
                    first = false;
                } else {
                    indicationsSb.append(",");
                }
                indicationsSb.append(jibing.text().trim());
            }
        }

        Elements descElements = doc
                .select("div.main-container > section > div.container > div.row > div.col-content > div.row > div.col-xs-12");
        if (descElements != null && descElements.size() > 0) {
            desc = descElements.get(0).ownText().trim();
        }

        Elements headerElements = doc
                .select("div.main-container > section > div.container > div.row > div.col-content > div.page-header-2");
        for (Element headerElement : headerElements) {
            String header = headerElement.text().trim();
            if ("用法用量".equals(header)) {
                dosage = headerElement.nextElementSibling().text().trim();
            } else if ("不良反应".equals(header)) {
                reactions = headerElement.nextElementSibling().text().trim();
            } else if ("注意事项".equals(header)) {
                matter = headerElement.nextElementSibling().text().trim();
            } else if (header.contains("主要成分")) {
                Elements ths = headerElement.nextElementSibling().select("tbody > tr > th");
                boolean first = true;
                for (Element th : ths) {
                    if (first) {
                        first = false;
                    } else {
                        ingredientSb.append(",");
                    }
                    ingredientSb.append(th.text().trim());
                }
            } else {
                logger.warn("header : " + header);
            }
        }

//        DBHelper.executeUpdateExp(
//                "INSERT IGNORE INTO med_yaopin (`code`, url, `name`, indications, `desc`, dosage, reactions, matter, ingredient, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())",
//                new String[] { code, url, name, indicationsSb.toString(), desc, dosage, reactions, matter,
//                        ingredientSb.toString() });

        // rel
        if (jibings != null && jibings.size() > 0) {
            for (Element jibing : jibings) {
                String jibingUrl = rootUrl + jibing.attr("href");
                rel.buildRel(DigestUtils.md5Hex(jibingUrl), code);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Yaopin().run();
    }
}

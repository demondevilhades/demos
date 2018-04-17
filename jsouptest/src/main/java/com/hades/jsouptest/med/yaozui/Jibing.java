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

public class Jibing {
    private final Logger logger = Logger.getLogger(this.getClass());

    private final String rootUrl = "https://www.yaozui.com";
    private final String baseUrl = rootUrl + "/jibing";// https://www.yaozui.com/jibing

    private final String charsetName = "utf-8";
    private final Charset charset = Charset.forName(charsetName);

    private final boolean continueFlag = true;
    private final String baseTempdir = "temp/";
    private final String tempdir = baseTempdir + "jibing/";
    private final String continuePathStr = baseTempdir + "/jibing_continue.txt";
    private final String listPathStr = baseTempdir + "/jibing_list.txt";

    private FileWriter fw;

    private final Rel rel = new Rel();

    public void run() throws Exception {
        logger.info("start : " + baseUrl);

        // init
        File continueFile = new File(continuePathStr);
        logger.info("continueFlag : " + continueFlag);
        String continueName = null;
        if (continueFlag) {
            if (continueFile.exists()) {
                FileReader fr = new FileReader(continueFile);
                String line = fr.readLine();
                fr.close();
                if (StringUtils.isNoneEmpty(line)) {
                    String[] split = line.split("\t");
                    continueName = split[0];
                }
                logger.info("continue : " + line);
            }
        }

        String name = null;
        try {
            File listFile = new File(listPathStr);
            if (!listFile.exists()) {
                listFile.createNewFile();
            }
            fw = new FileWriter(listFile, true);
            // parse
            Document doc = Jsoup.connect(baseUrl).execute().parse();
            Elements as = doc.select("div.main-container > section > div.container div.row div.fn a");
            for (Element a : as) {
                name = a.text();
                if (continueName != null) {
                    if (continueName.equals(name)) {
                        continueName = null;
                    } else {
                        continue;
                    }
                }
                parseJibing(name, a.attr("href"));
            }
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            if (fw != null) {
                fw.close();
            }
            continueFile.delete();
            if (name != null) {
                FileWriter continueFw = null;
                try {
                    continueFw = new FileWriter(continueFile);
                    continueFw.writeLine(new StringBuilder().append(name).toString());
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

    private void parseJibing(String name, String url) throws Exception {
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

        String desc = null;
        String medicare = null;
        String alias = null;
        String body = null;
        String infection = null;
        String people = null;
        String symptom = null;
        String concurrent_disease = null;
        String department = null;
        String cost = null;
        String curerate = null;
        String method = null;
        String check = null;
        String operation = null;
        String drug = null;
        String treatment_time = null;
        String duration = null;
        String period = null;
        String preparation = null;
        String latency = null;
        String latency_symptom = null;
        String spread = null;
        String is_genetic = null;
        String genetic = null;
        String infectious_disease = null;
        String clinic = null;

        Elements descElements = doc
                .select("div.main-container > section > div.container > div.row > div.col-content > div.panel-bodyx");
        if (descElements != null && descElements.size() > 0) {
            desc = descElements.get(0).text().trim();
        }
        Elements tables = doc
                .select("div.main-container > section > div.container > div.row > div.col-content > table.table");
        if (tables != null && tables.size() > 0) {
            Element table = tables.get(0);
            Elements trs = table.select("tr");
            for (Element tr : trs) {
                String th = tr.select("th").text().trim();
                String td = tr.select("td").text().trim();
                if ("是否属于医保".equals(th)) {
                    medicare = td;
                } else if ("别名".equals(th)) {
                    alias = td;
                } else if ("发病部位".equals(th)) {
                    body = td;
                } else if ("传染性".equals(th)) {
                    infection = td;
                } else if ("多发人群".equals(th)) {
                    people = td;
                } else if ("相关症状".equals(th)) {
                    symptom = td;
                } else if ("并发疾病".equals(th)) {
                    concurrent_disease = td;
                } else if ("就诊科室".equals(th)) {
                    department = td;
                } else if ("治疗费用".equals(th)) {
                    cost = td;
                } else if ("治愈率".equals(th)) {
                    curerate = td;
                } else if ("治疗方法".equals(th)) {
                    method = td;
                } else if ("相关检查".equals(th)) {
                    check = td;
                } else if ("相关手术".equals(th)) {
                    operation = td;
                } else if ("常用药品".equals(th)) {
                    drug = td;
                } else if ("最佳就诊时间".equals(th)) {
                    treatment_time = td;
                } else if ("就诊时长".equals(th)) {
                    duration = td;
                } else if ("复诊频率/诊疗周期".equals(th)) {
                    period = td;
                } else if ("就诊前准备".equals(th)) {
                    preparation = td;
                } else if ("潜伏期".equals(th)) {
                    latency = td;
                } else if ("潜伏期表现".equals(th)) {
                    latency_symptom = td;
                } else if ("传播途径".equals(th)) {
                    spread = td;
                } else if ("是否会遗传".equals(th)) {
                    is_genetic = td;
                } else if ("遗传方式".equals(th)) {
                    genetic = td;
                } else if ("传染病种类".equals(th)) {
                    infectious_disease = td;
                } else if ("门诊治疗".equals(th)) {
                    clinic = td;
                } else {
                    if (!"厂家".equals(th)) {
                        logger.warn(th + " : " + td);
                    }
                }
            }
        }

//        DBHelper.executeUpdateExp(
//                "INSERT IGNORE INTO med_jibing (`code`, url, `name`, `desc`, medicare, alias, body, infection, people, symptom, concurrent_disease, department, cost, curerate, method, `check`, operation, drug, treatment_time, duration, period, preparation, latency, latency_symptom, spread, is_genetic, genetic, infectious_disease, clinic, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())",
//                new String[] { code, url, name, desc, medicare, alias, body, infection, people, symptom,
//                        concurrent_disease, department, cost, curerate, method, check, operation, drug, treatment_time,
//                        duration, period, preparation, latency, latency_symptom, spread, is_genetic, genetic,
//                        infectious_disease, clinic });

        // rel
        Elements yaopinElements = doc
                .select("div.main-container > section > div.container > div.row > div.col-content > div.panel > div.panel-body > ol > li >div.details > a");
        if (yaopinElements != null) {
            for (Element yaopinA : yaopinElements) {
                String href = yaopinA.attr("href");
//                List<String[]> datas = rel.findUrlOfYaopin(href);
//                if(datas.size() == 0){
//                    System.out.println();
//                }
                rel.buildRel(code, DigestUtils.md5Hex(url));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Jibing().run();
    }
}

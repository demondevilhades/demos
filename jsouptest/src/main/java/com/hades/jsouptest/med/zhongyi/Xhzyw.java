package com.hades.jsouptest.med.zhongyi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 方剂
 * 
 * @author zs
 */
public class Xhzyw {
    private final Logger logger = Logger.getLogger(this.getClass());

    private final String rootUrl = "https://www.zhzyw.com";
    private final String baseUrl = rootUrl + "/zycs/zyfj/Index.html";

    private Set<String> set = new HashSet<String>();

    public void run() throws IOException {
        File file = new File("temp/zy_fangji.txt");
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);

            Document doc = Jsoup.connect(baseUrl).execute().parse();
            Elements as = doc.select("div.ullist > ul > li > a");
            for (Element a : as) {
                String url = rootUrl + a.attr("href");
                parseUrl(url, bw);
                logger.info(url);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    private void parseUrl(String url, BufferedWriter bw) throws IOException {
        while (url != null) {
            Document doc = Jsoup.connect(url).execute().parse();
            Elements as = doc.select("div.ullist01 > ul > li > a");
            for (Element a : as) {
                String name = a.text().trim();
                write(name, bw);
            }
            
            url = null;
            as = doc.select("div.pagecontent > a");
            for (Element a : as) {
                if(">".equals(a.text().trim())){
                    url = rootUrl + a.attr("href");
                }
            }
        }
    }

    private void write(String line, BufferedWriter bw) throws IOException {
        if (!set.contains(line)) {
            bw.write(line);
            bw.newLine();
            set.add(line);
        }
    }

    public static void main(String[] args) throws Exception {
        new Xhzyw().run();
    }
}

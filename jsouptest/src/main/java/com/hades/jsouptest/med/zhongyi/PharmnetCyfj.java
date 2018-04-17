package com.hades.jsouptest.med.zhongyi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 药材(去除前10个)
 * 
 * @author zs
 */
public class PharmnetCyfj {
    private final Logger logger = Logger.getLogger(this.getClass());

    private final String rootUrl = "http://www.pharmnet.com.cn";
    private final String baseUrl = rootUrl + "/tcm/knowledge/ycrs/";

    private final Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    private Set<String> set = new HashSet<String>();

    public void run() throws IOException {
        File file = new File("temp/zy_yaocai.txt");
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            for (int i = 1; i <= 253; i++) {
                parseUrl(baseUrl + "index" + i + ".html", bw);
                logger.info("page=" + i);
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
        Document doc = Jsoup.connect(url).execute().parse();
        Elements as = doc.select("table tbody td a");
        for (Element a : as) {
            String href = a.attr("href");
            if (StringUtils.isNotEmpty(href) && href.startsWith("http://www.pharmnet.com.cn/tcm/knowledge/detail/")) {
                String text = a.text();
                Matcher m = p.matcher(text);
                text = m.replaceAll("");
                write(text, bw);
                if (text.contains("（")) {
                    String[] split = text.split("（");
                    String[] split2 = split[1].split("）");
                    String name = split[0];
                    if (split2.length > 1) {
                        name += split2[1];
                    }
                    write(name, bw);
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
        new PharmnetCyfj().run();
    }
}

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
 * 穴位
 * 
 * @author zs
 */
public class Lxjk999 {
    @SuppressWarnings("unused")
    private final Logger logger = Logger.getLogger(this.getClass());

    private final String rootUrl = "http://www.lxjk999.com/";
    private Set<String> set = new HashSet<String>();

    public void run() throws IOException {
        File file = new File("temp/zy_xuewei.txt");
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            
            Document doc = Jsoup.connect(rootUrl).execute().parse();
            Elements as = doc.select("dl.tbox > dd > table > tbody > tr > td > a");
            for (Element a : as) {
                String name = a.text().trim();
                if(name.endsWith("穴")){
                    write(name, bw);
                    write(name.substring(0, name.length() - 1), bw);
                }
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

    private void write(String line, BufferedWriter bw) throws IOException {
        if (!set.contains(line)) {
            bw.write(line);
            bw.newLine();
            set.add(line);
        }
    }

    public static void main(String[] args) throws Exception {
        new Lxjk999().run();
    }
}

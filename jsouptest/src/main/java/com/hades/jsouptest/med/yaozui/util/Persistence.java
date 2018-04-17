package com.hades.jsouptest.med.yaozui.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class Persistence {
    
    public static String cleanStr(String fileName){
        return fileName.replaceAll("[\\\\/:\\*\\?\\\"<>\\|]", "").trim();
    }

    public static void write2file(File pageFile, String charsetName, String pageStr) throws IOException {
        write2file(pageFile, Charset.forName(charsetName), pageStr);
    }

    public static void write2file(File pageFile, Charset charset, String pageStr) throws IOException {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(pageFile);
            osw = new OutputStreamWriter(fos, charset);
            bw = new BufferedWriter(osw);
            bw.write(pageStr);
            bw.flush();
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
    
    public static void main(String[] args) {
        System.out.println(cleanStr("内科_癌性疼/痛_检查问/答_1.肺动脉狭窄，钱箱血流峰速4.3m/s,75mmhg.压差主动"));
    }
}

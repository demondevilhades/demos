package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CleanOther {

    private static final String[] NUM = new String[] { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
    private static final String[] TIAN_GAN = new String[] { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
    private static final String[] DI_ZHI = new String[] { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥" };
    private static final String[] CHEMISTRY = new String[] { "烷", "烯", "炔", "烃", "羧", "醇", "酚", "醚", "醛", "酮", "酸",
            "酰", "酯", "胺", "腈", "苯", "萘", "蒽", "菲", "呋喃", "噻吩", "吡咯", "噻唑", "咪唑", "吡啶", "吡嗪", "嘧啶", "哒嗪", "吲哚", "喹啉",
            "蝶啶", "吖啶" };

    public void run() throws IOException {
        File file = new File("library/med/other.txt");
        File newFile = new File("library/med/other1.txt");
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            fos = new FileOutputStream(newFile);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);

            String line = br.readLine();
            while (line != null) {
                fix(line, bw);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (fis != null) {
                    fis.close();
                }
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

    private void fix(String line, BufferedWriter bw) throws IOException {
        for (String str : NUM) {
            if (line.contains(str)) {
                return;
            }
        }
        for (String str : TIAN_GAN) {
            if (line.contains(str)) {
                return;
            }
        }
        for (String str : DI_ZHI) {
            if (line.contains(str)) {
                return;
            }
        }
        for (String str : CHEMISTRY) {
            if (line.contains(str)) {
                return;
            }
        }
        System.out.println(line);
        bw.write(line);
        bw.newLine();
    }

    public static void main(String[] args) throws Exception {
        new CleanOther().run();
    }
}

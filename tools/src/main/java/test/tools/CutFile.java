package test.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CutFile {

    private static final long maxSize = 20L * 1024 * 1024;

    @SuppressWarnings("unused")
    private static void streamCut(String fileName) {
        File file = new File(fileName);
        File cutFile = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        int i = 1;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) maxSize];

            while (fis.read(buffer) > 0) {
                cutFile = new File(fileName + "." + i);
                fos = new FileOutputStream(cutFile);
                fos.write(buffer);
                fos.flush();
                fos.close();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void lineCut(String fileName) {
        File file = new File(fileName);
        File cutFile = null;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        int i = 1;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                if (cutFile == null) {
                    cutFile = new File(fileName + "." + i);
                    fos = new FileOutputStream(cutFile);
                    osw = new OutputStreamWriter(fos, "GBK");
                    bw = new BufferedWriter(osw);
                    i++;
                }
                bw.write(line);
                bw.newLine();
                fos.flush();

                if (cutFile.length() >= maxSize) {
                    bw.close();
                    osw.close();
                    fos.close();
                    cutFile = null;
                }
                line = br.readLine();
            }
            if (cutFile != null && cutFile.length() > 0) {
                bw.close();
                osw.close();
                fos.close();
                cutFile = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        lineCut("");
    }
}

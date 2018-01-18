package com.hades.jsouptest.douban;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class FileWriter implements Closeable {
    private final Charset charset = Charset.forName("utf-8");

    private FileOutputStream fos = null;
    private OutputStreamWriter osw = null;
    private BufferedWriter bw = null;

    public FileWriter(File file) throws IOException {
        fos = new FileOutputStream(file);
        osw = new OutputStreamWriter(fos, charset);
        bw = new BufferedWriter(osw);
    }

    public void writeLine(String line) throws IOException {
        bw.write(line);
        bw.newLine();
        bw.flush();
    }

    @Override
    public void close() throws IOException {
        if (bw != null) {
            bw.close();
        }
        if (osw != null) {
            osw.close();
        }
        if (fos != null) {
            fos.close();
        }
    }

    @Deprecated
    public void write2file(File file, String str) throws IOException {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, charset);
            bw = new BufferedWriter(osw);
            bw.write(str);
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

    public static void save(String urlStr, String pathStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = conn.getInputStream();
            fos = new FileOutputStream(pathStr + new File(urlStr).getName());
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }
}

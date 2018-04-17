package com.hades.jsouptest.med.yaozui.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FileReader implements Closeable {
    private final Charset charset = Charset.forName("utf-8");

    private FileInputStream fis = null;
    private InputStreamReader isr = null;
    private BufferedReader br = null;

    public FileReader(File file) throws IOException {
        fis = new FileInputStream(file);
        isr = new InputStreamReader(fis, charset);
        br = new BufferedReader(isr);
    }

    public String readLine() throws IOException {
        return br.readLine();
    }

    @Override
    public void close() throws IOException {
        if (br != null) {
            br.close();
        }
        if (isr != null) {
            isr.close();
        }
        if (fis != null) {
            fis.close();
        }
    }
}

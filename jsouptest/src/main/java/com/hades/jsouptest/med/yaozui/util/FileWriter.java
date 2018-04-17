package com.hades.jsouptest.med.yaozui.util;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

    public FileWriter(File file, boolean append) throws IOException {
        fos = new FileOutputStream(file, append);
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
}

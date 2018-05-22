package hades.datatransfer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class BufferedFileReader extends Reader implements AutoCloseable {

    public static final Charset DEF_CHARSET = Charset.forName("utf-8");

    protected final FileInputStream fis;
    protected final InputStreamReader isr;
    protected final BufferedReader br;

    public BufferedFileReader(File file) throws IOException {
        this(file, DEF_CHARSET);
    }

    public BufferedFileReader(String file) throws IOException {
        this(file, DEF_CHARSET);
    }

    public BufferedFileReader(File file, Charset charset) throws IOException {
        fis = new FileInputStream(file);
        isr = new InputStreamReader(fis, charset);
        br = new BufferedReader(isr);
    }

    public BufferedFileReader(String file, Charset charset) throws IOException {
        fis = new FileInputStream(file);
        isr = new InputStreamReader(fis, charset);
        br = new BufferedReader(isr);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return br.read(cbuf, off, len);
    }

    public String readLine() throws IOException {
        return br.readLine();
    }

    @Override
    public void close() throws IOException {
        br.close();
        isr.close();
        fis.close();
    }
}

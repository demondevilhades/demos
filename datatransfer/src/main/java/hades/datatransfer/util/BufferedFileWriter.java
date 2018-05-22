package hades.datatransfer.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class BufferedFileWriter extends Writer implements AutoCloseable {

    public static final Charset DEF_CHARSET = Charset.forName("utf-8");

    protected final FileOutputStream fos;
    protected final OutputStreamWriter osr;
    protected final BufferedWriter bw;

    public BufferedFileWriter(File file) throws IOException {
        this(file, DEF_CHARSET);
    }

    public BufferedFileWriter(String file) throws IOException {
        this(file, DEF_CHARSET);
    }

    public BufferedFileWriter(File file, Charset charset) throws IOException {
        fos = new FileOutputStream(file);
        osr = new OutputStreamWriter(fos, charset);
        bw = new BufferedWriter(osr);
    }

    public BufferedFileWriter(String file, Charset charset) throws IOException {
        fos = new FileOutputStream(file);
        osr = new OutputStreamWriter(fos, charset);
        bw = new BufferedWriter(osr);
    }

    public void writeLine(String line) throws IOException {
        bw.write(line);
        bw.newLine();
    }

    @Override
    public void close() throws IOException {
        bw.close();
        osr.close();
        fos.close();
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        bw.write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        bw.flush();
    }
}

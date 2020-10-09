package hades.itext.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * 
 */
public class BufferedFileWriter extends Writer {
    protected final FileOutputStream fos;
    protected final OutputStreamWriter osr;
    protected final BufferedWriter bw;

    public BufferedFileWriter(File file, Charset charset, boolean append) throws IOException {
        fos = new FileOutputStream(file, append);
        osr = new OutputStreamWriter(fos, charset);
        bw = new BufferedWriter(osr);
    }

    public BufferedFileWriter(String file, Charset charset, boolean append) throws IOException {
        fos = new FileOutputStream(file, append);
        osr = new OutputStreamWriter(fos, charset);
        bw = new BufferedWriter(osr);
    }

    /**
     * Writes a line of text. And writes a line separator. The line separator string
     * is defined by thesystem property line.separator, and is not necessarily a
     * singlenewline ('\n') character.
     *
     * @param line String to be written
     * @throws IOException If an I/O error occurs
     */
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

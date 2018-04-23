package hades.compress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

public class IOUtils {

    private static final int BUFFER_SIZE = 1024;

    public static void write(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        while ((len = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
            os.write(buffer, 0, len);
        }
    }

    public static void write(SevenZFile szFile, OutputStream os) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        while ((len = szFile.read(buffer, 0, BUFFER_SIZE)) != -1) {
            os.write(buffer, 0, len);
        }
    }

    public static void write(InputStream is, SevenZOutputFile szof) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        while ((len = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
            szof.write(buffer, 0, len);
        }
    }
}

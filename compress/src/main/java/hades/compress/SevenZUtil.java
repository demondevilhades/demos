package hades.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.compress.utils.SeekableInMemoryByteChannel;

public class SevenZUtil {

    public static void unCompress(String fileStr, String outputStr) throws IOException {
        File outputFile = new File(fileStr);
        SevenZFile sevenZFile = null;
        try {
            sevenZFile = new SevenZFile(outputFile);
            unCompress(sevenZFile, outputStr);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (sevenZFile != null) {
                    sevenZFile.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public static void unCompress(byte[] buffer, String outputStr) throws IOException {
        SevenZFile sevenZFile = null;
        try {
            sevenZFile = new SevenZFile(new SeekableInMemoryByteChannel(buffer));
            unCompress(sevenZFile, outputStr);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (sevenZFile != null) {
                    sevenZFile.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public static void unCompress(InputStream is, String outputStr) throws IOException {
        byte[] bs = new byte[is.available()];
        is.read(bs);
        unCompress(bs, outputStr);
    }

    private static void unCompress(SevenZFile sevenZFile, String outputStr) throws IOException {
        SevenZArchiveEntry entry = sevenZFile.getNextEntry();
        while (entry != null) {
            File file = new File(outputStr, entry.getName());
            if (entry.isDirectory()) {
                file.mkdir();
            } else {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                byte[] bs = new byte[(int) entry.getSize()];
                sevenZFile.read(bs);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bs);
                fos.close();
            }
            entry = sevenZFile.getNextEntry();
        }
    }

    public static Map<String, byte[]> unCompressWithoutDirectory(byte[] buffer) throws IOException {
        Map<String, byte[]> map = new HashMap<String, byte[]>();
        SevenZFile sevenZFile = null;
        try {
            sevenZFile = new SevenZFile(new SeekableInMemoryByteChannel(buffer));
            SevenZArchiveEntry entry = sevenZFile.getNextEntry();
            while (entry != null) {
                if (!entry.isDirectory()) {
                    byte[] bs = new byte[(int) entry.getSize()];
                    sevenZFile.read(bs);
                    map.put(entry.getName(), bs);
                }
                entry = sevenZFile.getNextEntry();
            }
            return map;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (sevenZFile != null) {
                    sevenZFile.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    // ---

    public static void compress(String[] fileStrs, String outputFileStr) throws IOException {
        File outputFile = new File(outputFileStr);
        SevenZOutputFile szof = null;
        try {
            szof = new SevenZOutputFile(outputFile);
            compress(szof, fileStrs);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (szof != null) {
                    szof.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public static byte[] compress(String[] fileStrs) throws IOException {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        SevenZOutputFile szof = null;
        try {
            szof = new SevenZOutputFile(channel);
            compress(szof, fileStrs);
            return channel.array();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (szof != null) {
                    szof.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    private static void compress(SevenZOutputFile szof, String[] fileStrs) throws IOException {
        SevenZArchiveEntry entry = null;
        for (String fileStr : fileStrs) {
            File file = new File(fileStr);
            entry = szof.createArchiveEntry(file, fileStr);
            szof.putArchiveEntry(entry);
            if (!file.isDirectory()) {
                byte[] bs = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                fis.read(bs);
                fis.close();
                szof.write(bs);
            }
            szof.closeArchiveEntry();
        }
    }
}

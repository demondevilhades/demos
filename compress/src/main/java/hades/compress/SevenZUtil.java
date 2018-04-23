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

    /**
     * unCompress
     * 
     * @param fileStr
     * @param outputStr
     * @throws IOException
     */
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

    /**
     * unCompress
     * 
     * @param buffer
     * @param outputStr
     * @throws IOException
     */
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

    /**
     * unCompress
     * 
     * @param is
     * @param outputStr
     * @throws IOException
     */
    public static void unCompress(InputStream is, String outputStr) throws IOException {
        byte[] bs = new byte[is.available()];
        is.read(bs);
        unCompress(bs, outputStr);
    }

    /**
     * unCompress
     * 
     * @param sevenZFile
     * @param outputStr
     * @throws IOException
     */
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

    /**
     * unCompress
     * 
     * @param buffer
     * @return
     * @throws IOException
     */
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

    /**
     * compress
     * 
     * @param fileStrs
     * @return
     * @throws IOException
     */
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

    /**
     * compress
     * 
     * @param szof
     * @param fileStrs
     * @throws IOException
     */
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

    /**
     * compress
     * 
     * @param map
     *            (key : entryName ; value : file)
     * @param outputFileStr
     * @throws IOException
     */
    public static void compress(Map<String, String> map, String outputFileStr) throws IOException {
        File outputFile = new File(outputFileStr);
        SevenZOutputFile szof = null;
        try {
            szof = new SevenZOutputFile(outputFile);
            SevenZArchiveEntry entry = null;
            for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                File file = new File(mapEntry.getValue());
                entry = szof.createArchiveEntry(file, mapEntry.getKey());
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
}

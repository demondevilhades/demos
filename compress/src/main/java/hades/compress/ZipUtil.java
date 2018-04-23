package hades.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    /**
     * zip
     * 
     * @param originFile
     * @param outputFileStr
     * @throws IOException
     */
    public static void zip(File originFile, String outputFileStr) throws IOException {
        File outputFile = new File(outputFileStr);
        if (originFile.isDirectory() && outputFile.getPath().indexOf(originFile.getPath()) != -1) {
            throw new RuntimeException("outputFile must not be the child of originFile");
        }

        FileOutputStream fos = null;
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(outputFile);
            cos = new CheckedOutputStream(fos, new CRC32());
            zos = new ZipOutputStream(cos);
            zip(originFile, zos, "");
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
                if (cos != null) {
                    cos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * zip
     * 
     * @param map
     *            (key : entryName ; value : file)
     * @param outputFileStr
     * @throws IOException
     */
    public static void zip(Map<String, String> map, String outputFileStr) throws IOException {
        File outputFile = new File(outputFileStr);
        FileOutputStream fos = null;
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(outputFile);
            cos = new CheckedOutputStream(fos, new CRC32());
            zos = new ZipOutputStream(cos);
            for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                File file = new File(mapEntry.getValue());
                ZipEntry zipEntry = new ZipEntry(mapEntry.getKey());
                zos.putNextEntry(zipEntry);
                FileInputStream fis = new FileInputStream(file);
                IOUtils.write(fis, zos);
                fis.close();
                zos.closeEntry();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
                if (cos != null) {
                    cos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    private static void zip(File file, final ZipOutputStream zos, final String parentPath) throws IOException {
        if (file.isDirectory()) {
            String path = parentPath + file.getName() + "/";
            File[] files = file.listFiles();
            for (File childFile : files) {
                zip(childFile, zos, path);
            }
        } else {
            ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
            zos.putNextEntry(zipEntry);
            FileInputStream fis = new FileInputStream(file);
            IOUtils.write(fis, zos);
            fis.close();
            zos.closeEntry();
        }
    }

    /**
     * unzip
     * 
     * @param zipFile
     * @param outputFileStr
     * @throws IOException
     */
    public static void unzip(File zipFile, String outputFileStr) throws IOException {
        ZipFile zip = null;
        ZipEntry zipEntry = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            zip = new ZipFile(zipFile);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                zipEntry = entries.nextElement();
                File file = new File(outputFileStr, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    is = zip.getInputStream(zipEntry);
                    fos = new FileOutputStream(file);
                    IOUtils.write(is, fos);
                    fos.close();
                    is.close();
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
                if (zip != null) {
                    zip.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }
}

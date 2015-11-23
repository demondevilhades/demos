package test.classencrypt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * encrypt
 * 
 * @author zs
 */
public class EncryptClass {

    private static final String suffix = ".hclass";

    public static void encryptAll(String basePath, String target, String filter) {
        List<File> allClass = getAllClass(basePath, filter);
        for (File file : allClass) {
            String sClassPath = file.getAbsolutePath();
            encrypt(sClassPath,
                            sClassPath.replaceAll("\\\\", "/").replaceFirst(basePath, target)
                                            .replaceFirst("\\.class", suffix));
        }
    }

    /**
     * 加密
     * 
     * @param source
     * @param target
     */
    public static void encrypt(String source, String target) {
        File sourceFile = new File(source);
        File targetFile = new File(target);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        byte[] data = null;
        try {
            fis = new FileInputStream(sourceFile);
            bis = new BufferedInputStream(fis);
            data = new byte[bis.available()];
            bis.read(data);

            data = en(data);

            targetFile.mkdirs();
            if (targetFile.exists()) {
                targetFile.delete();
            }
            targetFile.createNewFile();

            fos = new FileOutputStream(targetFile);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] en(byte[] data) {
        byte[] resultBs = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            resultBs[i] = (byte) (data[i] + 2);
        }
        return resultBs;
    }

    /**
     * 获取目录下所有文件
     * 
     * @param basePath
     * @return
     */
    public static List<File> getAllClass(String basePath, final String filter) {
        File base = new File(basePath);
        List<File> list = new ArrayList<File>();
        if (base.exists()) {
            if (filter == null || filter.equals("")) {
                addAllFiles(base, list);
            } else {
                addAllFiles(base, new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        if (pathname.getName().contains(filter)) {
                            return false;
                        }
                        return true;
                    }
                }, list);
            }
        } else {
            System.out.println("source is not exist!");
        }
        return list;
    }

    private static void addAllFiles(File sFile, FileFilter filter, List<File> list) {
        if (sFile.isDirectory()) {
            File[] listFiles = sFile.listFiles(filter);
            for (File file : listFiles) {
                addAllFiles(file, filter, list);
            }
        } else {
            list.add(sFile);
        }
    }

    private static void addAllFiles(File sFile, List<File> list) {
        if (sFile.isDirectory()) {
            File[] listFiles = sFile.listFiles();
            for (File file : listFiles) {
                addAllFiles(file, list);
            }
        } else {
            list.add(sFile);
        }
    }
}

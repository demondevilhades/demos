package test.nlp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import test.nlp.Constant;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

/**
 * IO操作工具类
 */
public class IOUtil {

    /**
     * 获取BufferedReader
     * 
     * @param in
     *            输入流
     * @param charset
     *            字符集
     * @return
     * @throws UnsupportedEncodingException
     */
    public static BufferedReader getBufferedReader(InputStream in, Charset charset) throws UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(in, charset));
    }

    /**
     * 获取BufferedReader
     * 
     * @param inputFile
     *            输入文件
     * @param charset
     *            字符集
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public static BufferedReader getBufferedReader(File inputFile, Charset charset)
            throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), charset));
    }

    /**
     * 获取BufferedReader
     * 
     * @param inputFilePath
     *            文件路径
     * @param charset
     *            字符集
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public static BufferedReader getBufferedReader(String inputFilePath, Charset charset)
            throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath), charset));
    }

    /**
     * 获取BufferedWriter
     * 
     * @param outputFilePath
     *            输出文件路径
     * @param charset
     *            字符集
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public static BufferedWriter getBufferedWriter(String outputFilePath, Charset charset)
            throws UnsupportedEncodingException, FileNotFoundException {
        return getBufferedWriter(outputFilePath, charset, false);
    }

    /**
     * 获取BufferedWriter
     * 
     * @param outputFilePath
     *            输出文件
     * @param charset
     *            字符集
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public static BufferedWriter getBufferedWriter(File outputFile, Charset charset)
            throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), charset));
    }

    /**
     * 获取BufferedWriter
     * 
     * @param outputFilePath
     *            输出文件路径
     * @param charset
     *            字符集
     * @param append
     *            是否追加数据
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public static BufferedWriter getBufferedWriter(String outputFilePath, Charset charset, boolean append)
            throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath, append), charset));
    }

    /**
     * 获取DataInputStream
     * 
     * @param in
     *            输入流
     * @return
     */
    public static DataInputStream getDataInputStream(InputStream in) {
        return new DataInputStream(new BufferedInputStream(in));
    }

    /**
     * 获取DataInputStream
     * 
     * @param inputFilePath
     *            输入文件路径
     * @return
     */
    public static DataInputStream getDataInputStream(String inputFilePath) throws FileNotFoundException {
        return getDataInputStream(new File(inputFilePath));
    }

    /**
     * 获取DataInputStream
     * 
     * @param inputFile
     *            输入文件
     * @return
     */
    public static DataInputStream getDataInputStream(File inputFile) throws FileNotFoundException {
        return new DataInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
    }

    /**
     * 获取DataOutputStream
     * 
     * @param outputFilePath
     *            输出文件路径
     * @return
     * @throws FileNotFoundException
     */
    public static DataOutputStream getDataOutputStream(String outputFilePath) throws FileNotFoundException {
        return getDataOutputStream(new FileOutputStream(outputFilePath));
    }

    /**
     * 获取DataOutputStream
     * 
     * @param out
     *            输出流
     * @return
     * @throws FileNotFoundException
     */
    public static DataOutputStream getDataOutputStream(OutputStream out) {
        return new DataOutputStream(new BufferedOutputStream(out));
    }

    /**
     * 获取DataOutputStream
     * 
     * @param outputFile
     *            输出文件
     * @return
     * @throws FileNotFoundException
     */
    public static DataOutputStream getDataOutputStream(File outputFile) throws FileNotFoundException {
        return getDataOutputStream(new FileOutputStream(outputFile));
    }

    /**
     * 获取ObjectInputStream
     * 
     * @param inputFile
     *            输入文件
     * @return
     * @throws IOException
     */
    public static ObjectInputStream getObjectInputStream(File inputFile) throws IOException {
        return new ObjectInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
    }

    /**
     * 获取ObjectInputStream
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static ObjectInputStream getObjectInputStream(InputStream in) throws IOException {
        return new ObjectInputStream(new BufferedInputStream(in));
    }

    /**
     * 获取DataOutputStream
     * 
     * @param outputFile
     *            输出文件
     * @return
     * @throws IOException
     */
    public static ObjectOutputStream getObjectOutputStream(File outputFile) throws IOException {
        return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
    }

    /**
     * 获取DataOutputStream
     * 
     * @param outputFile
     *            输出文件
     * @return
     * @throws IOException
     */
    public static ObjectOutputStream getObjectOutputStream(OutputStream out) throws IOException {
        return new ObjectOutputStream(new BufferedOutputStream(out));
    }

    /**
     * 读取数据至map中
     * 
     * @param inputFile
     *            输入文件
     * @param charset
     *            字符集
     * @param separator
     *            第一行数据之间的分隔符
     * @throws IOException
     */
    public static Map<String, String> readToMap(File inputFile, Charset charset, String separator) throws IOException {
        Map<String, String> result = Maps.newHashMap();
        try (BufferedReader reader = getBufferedReader(inputFile, charset);) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                String[] tokens = line.split(separator);
                if (tokens.length != 2) {
                    continue;
                }
                result.put(tokens[0], tokens[1]);
            }
        }
        return result;
    }

    public static String readFile(File inputFile, Charset charset) throws IOException {
        List<String> lines = Files.readLines(inputFile, charset);
        return Joiner.on(System.getProperty("line.separator")).join(lines);
    }

    public static List<String> readLines(File inputFile, Charset charset) throws IOException {
        List<String> lines = null;
        try (InputStream in = new FileInputStream(inputFile);) {
            lines = IOUtils.readLines(in, StandardCharsets.UTF_8.toString());
        }
        return lines;
    }

    /**
     * 将list数据写入文件中
     * 
     * @param datas
     * @param outputFile
     * @throws IOException
     */
    public static void write(Collection<?> datas, File outputFile) throws IOException {
        try (BufferedWriter writer = Files.newWriter(outputFile, StandardCharsets.UTF_8);) {
            IOUtils.writeLines(datas, System.getProperty("line.separator"), writer);
        }
    }

    public static void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                // 不处理
            }
        }
    }

    public static void close(AutoCloseable... autoCloseables) {
        for (AutoCloseable autoCloseable : autoCloseables) {
            close(autoCloseable);
        }
    }

    /**
     * 当目录不存在时创建目录，否则直接返回
     * 
     * @param directoryPath
     *            目录路径
     */
    public static File createDirsIfNotExist(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 获取目录下的所有TXT文件
     * 
     * @param directory
     * @return
     */
    public static File[] listTXTFiles(File directory) {
        return directory.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(Constant.TXT_FILE_SUFFIX);
            }
        });
    }

    /**
     * 获取指定目录的子目录
     * 
     * @param directory
     * @return
     */
    public static File[] listChildDirs(File directory) {
        return directory.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
    }
}

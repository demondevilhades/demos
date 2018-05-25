package test.nlp.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import test.nlp.util.IOUtil;
import test.nlp.util.StringUtils;

/**
 * 文件读取基类
 */
public abstract class FileReader<T> {

    public T read(File file, String separator) throws IOException {
        return read(file, StandardCharsets.UTF_8, separator);
    }

    public T read(File file, Charset charset, String separator) throws IOException {
        if (!file.isFile()) {
            return newResult();
        }
        try (InputStream in = new FileInputStream(file);) {
            return read(in, charset, separator);
        }
    }

    public T read(InputStream in, Charset charset, String separator) throws IOException {
        T result = newResult();
        String line = null;

        BufferedReader reader = IOUtil.getBufferedReader(in, charset);
        while ((line = reader.readLine()) != null) {
            if (org.apache.commons.lang3.StringUtils.isBlank(line)) {
                continue;
            }
            line = StringUtils.regularize(line);
            String[] tokens = line.split(separator);
            processTokens(line, tokens, result);
        }
        return result;
    }

    protected abstract T newResult();

    /**
     * 处理切分结果
     * 
     * @param original
     *            原文本串
     * @param tokens
     *            切分集合
     * @param result
     *            最终结果
     */
    protected abstract void processTokens(String original, String[] tokens, T result);
}

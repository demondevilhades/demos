package test.nlp.dict;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 词典基类
 */
public abstract class Dictionary {

    protected static final Logger LOG = LoggerFactory.getLogger(Dictionary.class);
    
    protected final String libraryPath = this.getClass().getClassLoader().getResource("library/").getPath();

    /**
     * 创建词典
     * 
     * @param source
     *            源文件
     * @param desti
     *            词典文件
     * @throws Exception
     */
    public void create(File source, File desti) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * 加载词典
     * 
     * @param path
     * @throws Exception
     */
    public void read(File path) throws IOException, ClassNotFoundException {
        try (InputStream in = new FileInputStream(path);) {
            read(in);
        }
    }

    /**
     * 保存词典
     * 
     * @param path
     * @throws Exception
     */
    public void write(File path) throws IOException {
        try (OutputStream out = new FileOutputStream(path);) {
            write(out);
        }
    }

    protected abstract void read(InputStream in) throws IOException, ClassNotFoundException;

    protected abstract void write(OutputStream out) throws IOException;
}

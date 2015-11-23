package test.classencrypt;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * custom classLoader
 * 
 * @author zs
 */
public class CustomClassLoader extends ClassLoader {

    private final String customSuffix = ".hclass";

    private String classPath = null;

    public CustomClassLoader(String classPath) {
        super();
        this.classPath = classPath;
    }

    @Override
    public Class<?> findClass(String fileName) throws ClassNotFoundException {
        if (fileName.endsWith(customSuffix)) {
            return findClassEncrypt(fileName);
        } else {
            return super.findClass(fileName);
        }
    }

    private Class<?> findClassEncrypt(String fileName) throws ClassNotFoundException {
        String absoluteFileName = classPath + fileName.replaceAll("\\\\", "/");
        String className = fileName.substring(0, fileName.lastIndexOf(".")).replaceAll("\\\\", "/")
                        .replaceAll("/", ".");
        byte[] classBytes = null;
        try {
            classBytes = loadClassBytesEncrypt(absoluteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Class<?> cl = defineClass(className, classBytes, 0, classBytes.length);
        if (cl == null) {
            throw new ClassNotFoundException(className);
        }
        return cl;
    }

    private byte[] loadClassBytesEncrypt(String name) throws IOException {
        FileInputStream fis = new FileInputStream(name);
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int ch;
            while ((ch = fis.read()) != -1) {
                buffer.write((byte) (ch - 2));
            }
            return buffer.toByteArray();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}

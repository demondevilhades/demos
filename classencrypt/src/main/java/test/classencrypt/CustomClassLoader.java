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

    private final String nameFilter = ".Example";

    private String classPath = null;

    public CustomClassLoader(String classPath) {
        super();
        this.classPath = classPath;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.endsWith(nameFilter)) {
            return findClassEncrypt(name);
        } else {
            return super.findClass(name);
        }
    }

    private Class<?> findClassEncrypt(String name) throws ClassNotFoundException {
        byte[] classBytes = null;
        try {
            classBytes = loadClassBytesEncrypt(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
        if (cl == null) {
            throw new ClassNotFoundException(name);
        }
        return cl;
    }

    private byte[] loadClassBytesEncrypt(String name) throws IOException {
        String cname = classPath + name.replace('.', '/') + ".class";
        System.out.println(cname);
        FileInputStream in = new FileInputStream(cname);
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                buffer.write((byte) (ch - 2));
            }
            in.close();
            return buffer.toByteArray();
        } finally {
            in.close();
        }
    }
}

package test.classencrypt;

import java.lang.reflect.Method;

import org.junit.Test;

public class CustomClassLoaderTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {
        CustomClassLoader customClassLoader = new CustomClassLoader(EncryptClassTest.targetPath);
        //Class<?> exampleClazz = (Class<Example>) customClassLoader.findClass("test.classencrypt.Example");
        Class<?> exampleClazz = (Class<Example>) customClassLoader.findClass("test/classencrypt/Example.hclass");
        Method[] methods = exampleClazz.getMethods();

        Object example = exampleClazz.newInstance();
        System.out.println(example);
    }
}

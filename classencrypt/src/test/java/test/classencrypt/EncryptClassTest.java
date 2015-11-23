package test.classencrypt;

import org.junit.Test;

public class EncryptClassTest {

    private static final String sourcePath = "F:/workspace/eclipse/classencrypt/target/classes/";
    static final String targetPath = "F:/workspace/classencrypt/";

    @Test
    public void test() {
        EncryptClass.encryptAll(sourcePath, targetPath, null);
    }
}

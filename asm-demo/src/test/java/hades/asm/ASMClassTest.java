package hades.asm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class ASMClassTest {

    private final String className = "Test";

    // javap -p -c -l Test.class

    @Test
    public void test() throws Exception {
        ASMClass asmClass = new ASMClass(className, null, null);
        asmClass.createDefConstructor();
        asmClass.createVoidMethod("run", "testRun");
        writeClassFile(asmClass.getByteCode(), "D:/" + asmClass.getClassName() + ".class");
    }

    private void writeClassFile(byte[] bs, String fileName) {
        File classFile = new File(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(classFile);
            fos.write(bs);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

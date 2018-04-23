package hades.compress;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

public class ZipUtilTest {

    @SuppressWarnings("serial")
    @Test
    public void test() throws IOException {
        ZipUtil.zip(new HashMap<String, String>() {
            {
                put("a1/test2.txt", "temp/a1/test2.txt");
                put("test.txt", "temp/test.txt");
            }
        }, "temp/test.zip");
        ZipUtil.unzip(new File("temp/test.zip"), "temp/");
    }
}

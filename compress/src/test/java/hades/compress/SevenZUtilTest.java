package hades.compress;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

public class SevenZUtilTest {

    @SuppressWarnings("serial")
    @Test
    public void test() throws IOException {
//      SevenZUtil.compress(new String[] { "temp/a1/test2.txt", "temp/test.txt" }, "temp/test.7z");
        SevenZUtil.compress(new HashMap<String, String>(){{
            put("a1/test2.txt", "temp/a1/test2.txt");
            put("test.txt", "temp/test.txt");
        }}, "temp/test.7z");
        SevenZUtil.unCompress("temp/test.7z", "temp/");
    }
}

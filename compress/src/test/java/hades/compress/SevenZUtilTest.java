package hades.compress;

import java.io.IOException;

import org.junit.Test;

public class SevenZUtilTest {

    @Test
    public void test() throws IOException {
        SevenZUtil.compress(new String[] { "temp/a1/test2.txt", "temp/test.txt" }, "temp/test.7z");
        SevenZUtil.unCompress("temp/test.7z", "temp/");
    }
}

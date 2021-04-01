package awesome.kong;

import org.junit.Test;

/**
 * 
 * @author awesome
 */
public class AppTest {

    private App app = new App();

    @Test
    public void testTestFailed() throws Exception {
        app.testFailed();
    }

    @Test
    public void testTestBasic() throws Exception {
        app.testBasic();
    }

    @Test
    public void testTestHmac() throws Exception {
        app.testHmac();
    }

    @Test
    public void testTestHmacPost() throws Exception {
        app.testHmacPost();
    }

    @Test
    public void testTestOauth2() throws Exception {
        app.testOauth2();
    }

    @Test
    public void testTestJwt() throws Exception {
        app.testJwt();
    }
}

package test.rsa;

import java.security.Provider;
import java.security.Security;

import org.junit.Assert;
import org.junit.Test;

public class RSAUtilsTest {

    @Test
    public void info() {
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            System.out.println(provider.getInfo());
        }
    }

    @Test
    public void test1() throws Exception {
        // server
        RSAKey rsaKey = RSAUtils.getRSAKey();
        String rsaPublicKeyStr = RSAUtils.encode(rsaKey.getRsaPublicKey().getEncoded());
        System.out.println(rsaPublicKeyStr);

        // client
        String text = "abc123一二三";
        String sendText = RSAUtils.encode(RSAUtils.RSAEncode(
                RSAUtils.genRSAPublicKey(RSAUtils.decode(rsaPublicKeyStr)), text.getBytes()));
        System.out.println(sendText);
        Assert.assertNotEquals(text, RSAUtils.decode(sendText));

        // server
        String decodeMsg = new String(RSAUtils.RSADecode(rsaKey.getRsaPrivateKey(), RSAUtils.decode(sendText)));
        System.out.println(decodeMsg);
        Assert.assertEquals(text, decodeMsg);
    }

    @Test
    public void test2() throws Exception {
        RSAKey rsaKey0 = RSAUtils.getRSAKey();
        MED med = RSAUtils.getMED(rsaKey0);
        System.out.println(med);
        RSAKey rsaKey1 = RSAUtils.getRSAKey(med);
        System.out.println(rsaKey1);
        Assert.assertEquals(rsaKey0, rsaKey1);
    }
}

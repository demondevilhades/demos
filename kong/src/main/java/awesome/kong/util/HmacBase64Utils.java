package awesome.kong.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * 
 * @author awesome
 */
public class HmacBase64Utils {

    /**
     * 
     * @param key
     * @param valueToDigest
     * @return
     */
    public static String hmacMd5(String key, String valueToDigest) {
        byte[] bs = new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmac(valueToDigest);
        return Base64.encodeBase64String(bs);
    }

    /**
     * 
     * @param key
     * @param valueToDigest
     * @return
     */
    public static String hmacSha256(String key, String valueToDigest) {
        byte[] bs = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmac(valueToDigest);
        return Base64.encodeBase64String(bs);
    }
}

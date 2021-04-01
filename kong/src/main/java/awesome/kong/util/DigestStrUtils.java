package awesome.kong.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @author awesome
 */
public class DigestStrUtils {

    public static String sha256Base64(String str) {
        return Base64.encodeBase64String(DigestUtils.getSha256Digest().digest(str.getBytes()));
    }
}

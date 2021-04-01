package awesome.kong.util;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * 
 * @author awesome
 */
public class JwtUtils {

    public static String genJwtHS256(String issuer, String secret) {
        return JWT.create().withIssuer(issuer).sign(Algorithm.HMAC256(secret));

    }

    public static String genJwtHS256(Map<String, Object> map, String secret) {
        return JWT.create().withPayload(map).sign(Algorithm.HMAC256(secret));
    }
}

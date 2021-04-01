package awesome.kong;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.alibaba.fastjson.JSONObject;

import awesome.kong.util.DigestStrUtils;
import awesome.kong.util.HmacBase64Utils;
import awesome.kong.util.HttpUtils;
import awesome.kong.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author awesome
 */
@Slf4j
public class App {
    private final String host = "http://host:8001";
    private final String path = "/test";
    private final String url = host + path;

    private final String postPath = "/testPost";
    private final String postUrl = host + postPath;

    private final String hostHttps = "https://host:8443";

    public void testFailed() throws IOException {
        Map<String, String> headers = new HashMap<>();
        String str = HttpUtils.httpGet(url, headers);
        log.info(str);
    }

    public void testBasic() throws IOException {
        String username = "test";
        String password = "test";

        Map<String, String> headers = new HashMap<>();
        String encode = Base64.encodeBase64String((username + ":" + password).getBytes());
        headers.put("Authorization", "Basic " + encode);
        String str = HttpUtils.httpGet(url, headers);
        log.info(str);
    }

    public void testHmac() throws IOException {
        String username = "test";
        String secret = "***";

//        String testParams = "?test1=1&test2=2";

        Date now = new Date();
        // now = DateUtils.addDays(now, 1); // HMAC signature cannot be verified, a valid date or x-date header is required for HMAC Authentication
        String dateStr = DateFormatUtils.format(now, "EEE, dd MMM yyyy HH:mm:ss z", TimeZone.getTimeZone("GMT"),
                Locale.US);
        String message = new StringBuilder().append("x-date: ").append(dateStr)
//                .append("\nGET /openapi/user-service-dev.yml HTTP/1.1")
                .toString();
        log.info(message);
        String signature = HmacBase64Utils.hmacSha256(secret, message);
        log.info(signature);

        Map<String, String> headers = new HashMap<>();
        headers.put("x-date", dateStr);
        headers.put("Authorization", "hmac username=\"" + username
                + "\", algorithm=\"hmac-sha256\", headers=\"x-date\", signature=\"" + signature + "\"");

        String str = HttpUtils.httpGet(url, headers);
//        String str = HttpUtils.httpGet(url + testParams, headers);
        log.info(str);
    }

    public void testHmacPost() throws IOException {
        String username = "test";
        String secret = "***";

        Map<String, String> params = new HashMap<>();
        params.put("jwt", "test");
        String jsonStr = JSONObject.toJSONString(params);
        log.info("json = {}", jsonStr);
        String digest = "SHA-256=" + DigestStrUtils.sha256Base64(jsonStr);

        Date now = new Date();
        String dateStr = DateFormatUtils.format(now, "EEE, dd MMM yyyy HH:mm:ss z", TimeZone.getTimeZone("GMT"),
                Locale.US);

        String message = new StringBuilder().append("x-date: ").append(dateStr).append("\ndigest: ").append(digest)
                .toString();
        log.info(message);
        String signature = HmacBase64Utils.hmacSha256(secret, message);
        log.info(signature);

        Map<String, String> headers = new HashMap<>();
        headers.put("Digest", digest);
        headers.put("x-date", dateStr);
        headers.put("Authorization", "hmac username=\"" + username
                + "\", algorithm=\"hmac-sha256\", headers=\"x-date digest\", signature=\"" + signature + "\"");

        String str = HttpUtils.httpPostJsonStr(postUrl, headers, jsonStr);
        log.info(str);
    }

    public void testOauth2() throws IOException {
        String clientId = "***";
        String clientSecret = "***";

        // 1. token
        String accessToken = null;
        {
            String oauthUrl = hostHttps + "/openapi/oauth2/token";

            Map<String, String> params = new HashMap<>();
            params.put("grant_type", "client_credentials");
            params.put("client_id", clientId);
            params.put("client_secret", clientSecret);
            String str = HttpUtils.httpPost(oauthUrl, null, params);
            log.info(str);
            JSONObject jsonObject = JSONObject.parseObject(str);
            accessToken = jsonObject.getString("access_token");
        }

        // 2. request
        {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + accessToken);
            String str = HttpUtils.httpGet(url, headers);
            log.info(str);
        }
    }

    public void testJwt() throws IOException {
        String key = "***"; // iss
        String secret = "***";

        String jwt = JwtUtils.genJwtHS256(key, secret);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + jwt);
        String str = HttpUtils.httpGet(url, headers);
        log.info(str);
    }
}

package com.hades.leetcode;

import java.util.HashMap;
import java.util.Map;

public class P535 {
    private final String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z" };

    private final String url = "http://tinyurl.com/";
    private final Map<String, String> map = new HashMap<String, String>();
    private long i = 0;

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        String str = new StringBuilder(url).append(genCode()).toString();
        map.put(str, longUrl);
        return str;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return map.get(shortUrl);
    }

    private StringBuilder genCode() {
        int[] url6;
        int index;
        url6 = new int[] { 0, 0, 0, 0, 0, 0 };
        index = 0;
        long l = i++;
        while (l > 0) {
            url6[index++] = (int) l % 62;
            l /= 62;
        }
        StringBuilder sb = new StringBuilder();
        for (int i : url6) {
            sb.append(chars[i]);
        }
        return sb;
    }

    public static void main(String[] args) {
        P535 p = new P535();
        System.out.println(p.decode(p.encode("")));
    }
}

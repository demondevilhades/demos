package test.nlp.util;

import java.math.BigInteger;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import test.nlp.keyword.Keyword;

/**
 * simhash工具类
 */
public final class SimHashUtil {

    private static final BigInteger PRIME = BigInteger.valueOf(1000003);

    /**
     * 生成64位hash
     */
    private static final int HASH_BITS = 64;

    /**
     * 计算两个hash值的海明距离
     * 
     * @param hash
     * @return
     */
    public static int getHammingDistance(String hash1, String hash2) {

        if (hash1.length() != hash2.length()) {
            return Integer.MAX_VALUE;
        }
        int distance = 0;
        for (int i = 0; i < hash1.length(); i++) {
            if (hash1.charAt(i) != hash2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    /**
     * 计算两个hash值的海明距离
     * 
     * @param hash
     * @return
     */
    public static int getHammingDistance(long hash1, long hash2) {
        BigInteger bigInteger = BigInteger.valueOf(hash1 ^ hash2);
        int distance = 0;
        while (bigInteger.signum() != 0) {
            distance++;
            bigInteger = bigInteger.and(bigInteger.subtract(BigInteger.valueOf(1)));
        }
        return distance;
    }

    /**
     * 获取simhash值
     * 
     * @param keywords
     * @param hashBits
     * @return
     */
    public static String simHash(Collection<Keyword> keywords) {
        double[] v = calcVector(keywords, HASH_BITS);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < v.length; i++) {
            if (v[i] >= 0) {
                result.append("1");
            } else {
                result.append("0");
            }
        }
        return result.toString();
    }

    /**
     * 计算向量值
     * 
     * @param keywords
     * @param hashBits
     * @return
     */
    private static double[] calcVector(Collection<Keyword> keywords, int hashBits) {
        double[] v = new double[hashBits];
        for (Keyword keyword : keywords) {
            BigInteger hash = hash(keyword.getWord());
            BigInteger mask = BigInteger.valueOf(1);
            for (int i = 0; i < hashBits; i++) {
                if (hash.shiftRight(i).and(mask).signum() == 1) {
                    v[i] += keyword.getWeight();
                } else {
                    v[i] -= keyword.getWeight();
                }

            }
        }
        return v;
    }

    private static BigInteger hash(String text) {

        if (StringUtils.isBlank(text)) {
            return BigInteger.valueOf(0);
        }

        char[] charArray = text.toCharArray();
        BigInteger hash = BigInteger.valueOf((long) charArray[0] << 7);
        BigInteger mask = BigInteger.valueOf(2).pow(HASH_BITS).subtract(BigInteger.valueOf(1));
        for (int i = 0; i < charArray.length; i++) {
            hash = hash.multiply(PRIME).xor(BigInteger.valueOf((long) charArray[i])).and(mask);
        }
        return hash;
    }

}

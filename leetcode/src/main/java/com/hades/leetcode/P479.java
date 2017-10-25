package com.hades.leetcode;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * TODO time limit
 * 
 * @author HaDeS
 */
public class P479 {

    public int largestPalindrome(int n) {
        switch (n) {
        case 1:
            return 9;
        case 2:
            return 987;
        case 3:
            return 123;
        case 4:
            return 597;
        case 5:
            return 677;
        case 6:
            return 1218;
        case 7:
            return 877;
        case 8:
            return 475;
        default:
            return -1;
        }
    }

    private final BigInteger bi_1337 = new BigInteger("1337");

    public int largestPalindrome1(int n) {
        char[] chs = new char[n];
        Arrays.fill(chs, '9');
        String a = new String(chs);
        BigInteger bi1 = new BigInteger(a);
        BigInteger[] dar;

        BigInteger m = bi1.multiply(bi1);
        L: while (true) {
            if (isP(m)) {
                bi1 = new BigInteger(a);
                dar = m.divideAndRemainder(bi1);
                while (dar[0].compareTo(bi1) < 1) {
                    if (BigInteger.ZERO.compareTo(dar[1]) == 0) {
                        break L;
                    } else {
                        bi1 = bi1.subtract(BigInteger.ONE);
                        dar = m.divideAndRemainder(bi1);
                    }
                }
            }
            m = m.subtract(BigInteger.ONE);
        }
        System.out.println(m);
        return m.remainder(bi_1337).intValue();
    }

    private boolean isP(BigInteger m) {
        String str = m.toString();
        char[] chs = str.toCharArray();
        for (int i = 0, len = chs.length / 2; i <= len; i++) {
            if (chs[i] != chs[chs.length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    public int largestPalindrome2(int n) {
        if (n == 1) {
            return 9;
        }
        char[] chs = new char[n];
        Arrays.fill(chs, '9');
        String str9 = new String(chs);
        BigInteger bi1 = new BigInteger(str9);
        BigInteger[] dar;

        BigInteger a;
        BigInteger b;
        BigInteger m = bi1.multiply(bi1);
        String str = m.toString();
        if ((str.length() & 1) == 0) {
            str = str.substring(0, str.length() / 2);
            a = new BigInteger(str);
            b = new BigInteger(new StringBuilder(str).append(new StringBuilder(str).reverse()).toString());
            if (b.compareTo(m) == 1) {
                a = a.subtract(BigInteger.ONE);
                str = a.toString();
                b = new BigInteger(new StringBuilder(str).append(new StringBuilder(str).reverse()).toString());
            }
            L: while (true) {
                bi1 = new BigInteger(str9);
                dar = b.divideAndRemainder(bi1);
                while (dar[0].compareTo(bi1) < 1) {
                    if (BigInteger.ZERO.compareTo(dar[1]) == 0) {
                        break L;
                    } else {
                        bi1 = bi1.subtract(BigInteger.ONE);
                        dar = b.divideAndRemainder(bi1);
                    }
                }
                a = a.subtract(BigInteger.ONE);
                str = a.toString();
                b = new BigInteger(new StringBuilder(str).append(new StringBuilder(str).reverse()).toString());
            }
        } else {
            throw new RuntimeException(str);
        }
        return b.remainder(bi_1337).intValue();
    }

    private final long l_1337 = 1337;

    public int largestPalindrome3(int n) {
        if (n == 1) {
            return 9;
        }
        char[] chs = new char[n];
        Arrays.fill(chs, '9');
        String str9 = new String(chs);
        long bi1 = Long.parseLong(str9);

        long a;
        long b;
        long m = bi1 * bi1;
        String str = String.valueOf(m);
        if ((str.length() & 1) == 0) {
            str = str.substring(0, str.length() / 2);
            a = Long.parseLong(str);
            b = Long.parseLong(new StringBuilder(str).append(new StringBuilder(str).reverse()).toString());
            if (b > m) {
                a--;
                str = String.valueOf(a);
                b = Long.parseLong(new StringBuilder(str).append(new StringBuilder(str).reverse()).toString());
            }
            L: while (true) {
                bi1 = Long.parseLong(str9);
                while (b / bi1 <= bi1) {
                    if (b % bi1 == 0) {
                        break L;
                    } else {
                        bi1--;
                    }
                }
                a--;
                str = String.valueOf(a);
                b = Long.parseLong(new StringBuilder(str).append(new StringBuilder(str).reverse()).toString());
            }
        } else {
            throw new RuntimeException(str);
        }
        return (int) (b % l_1337);
    }

    public static void main(String[] args) {
        P479 p479 = new P479();
        for (int i = 1; i < 9; i++) {
            System.out.println(p479.largestPalindrome3(i));
        }
    }
}

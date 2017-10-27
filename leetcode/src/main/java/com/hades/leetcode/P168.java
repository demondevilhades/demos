package com.hades.leetcode;

public class P168 {
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        int a;
        do {
            n--;
            a = n % 26;
            n = n / 26;
            sb.append((char) (a + 'a'));
        } while (n > 0);
        return sb.reverse().toString().toUpperCase();
    }

    public static void main(String[] args) {
        P168 p168 = new P168();
        System.out.println(p168.convertToTitle(25));
        System.out.println(p168.convertToTitle(26));
        System.out.println(p168.convertToTitle(27));
        System.out.println(p168.convertToTitle(28));
        System.out.println(p168.convertToTitle(95));
        System.out.println(p168.convertToTitle(701));
    }
}

package com.hades.leetcode;

public class P400 {
    public int findNthDigit(int n) {
        int d = 1;
        int a = 0;
        long b = 9;
        while (n > b) {
            n -= b;
            d++;
            a = (int) Math.pow(10, d - 1) - 1;
            b = ((long) Math.pow(10, d) - 1 - a) * d;
        }
        a += (n / d);
        int m = n % d;
        String str = String.valueOf(m == 0 ? a : a + 1);
        return str.charAt(m == 0 ? str.length() - 1 : m - 1) - 48;
    }

    public static void main(String[] args) {
        P400 p = new P400();
        System.out.println(p.findNthDigit(11));// 0
        System.out.println(p.findNthDigit(100));// 5
        System.out.println(p.findNthDigit(10000000));// 7
        System.out.println(p.findNthDigit(1000000000));// 7
    }
}

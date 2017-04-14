package com.hades.leetcode;

public class P29 {
    /**
     * TLE
     * 
     * @param dividend
     * @param divisor
     * @return
     */
    public int divide(int dividend, int divisor) {
        if (divisor == 1) {
            return dividend;
        }
        if (divisor == 0 || (dividend == Integer.MIN_VALUE && divisor == -1)) {
            return Integer.MAX_VALUE;
        }
        int s = 1;
        if (dividend > 0) {
            dividend = -dividend;
            if (divisor < 0) {
                s = -1;
            } else {
                divisor = -divisor;
            }
        } else if (divisor > 0) {
            s = -1;
            divisor = -divisor;
        }
//        int c = 0;
//        while (dividend <= divisor) {
//            dividend -= divisor;
//            c++;
//        }
//        return s == 1 ? c : -c;
        return s == 1 ? (dividend / divisor) : -(dividend / divisor);
    }

    public static void main(String[] args) {
        P29 p29 = new P29();
        System.out.println(p29.divide(0, 1));
        System.out.println(p29.divide(1, 2));
        System.out.println(p29.divide(2, 1));
        System.out.println(p29.divide(3, 2));
        System.out.println(p29.divide(-2147483648, -1));
        System.out.println(p29.divide(-2147483648, 1));
        System.out.println(p29.divide(-2147483648, 2));
    }
}

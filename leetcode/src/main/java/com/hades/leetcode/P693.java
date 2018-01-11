package com.hades.leetcode;

public class P693 {
    public boolean hasAlternatingBits(int n) {
        return n > 1073741823 ? hasAlternatingBits2(n) : hasAlternatingBits1(n);
    }

    public boolean hasAlternatingBits1(int n) {
        int i = 0;
        int j = 1;
        while (i <= n) {
            if (i == n) {
                return true;
            }
            i = i * 2 + j;
            j = j == 1 ? 0 : 1;
        }
        return false;
    }

    public boolean hasAlternatingBits2(int n) {
        char[] chs = Integer.toBinaryString(n).toCharArray();
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] != (((i & 1) == 0) ? 1 : 0) + '0') {
                return false;
            }
        }
        return true;
    }

    public boolean hasAlternatingBits3(int n) {
        long i = 0;
        int j = 1;
        while (i <= n) {
            if (i == n) {
                return true;
            }
            i = i * 2 + j;
            j = j == 1 ? 0 : 1;
        }
        return false;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // 0 0
        // 1 1
        // 2 10
        // 5 101
        // 10 1010
        // 21 10101
        // 42 101010
        // 85 1010101
        P693 p = new P693();
        // System.out.println(p.hasAlternatingBits2(0));
        // System.out.println(p.hasAlternatingBits2(1));
        // System.out.println(p.hasAlternatingBits2(2));
        // System.out.println(p.hasAlternatingBits2(85));
        // System.out.println(p.hasAlternatingBits2(86));
         System.out.println(p.hasAlternatingBits3(1431655766));
    }
}

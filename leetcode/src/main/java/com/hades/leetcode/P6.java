package com.hades.leetcode;

public class P6 {
    public String convert(String s, int numRows) {
        String[][] strs = new String[numRows][];
        int len = s.length();
        for (int i = 0; i < strs.length; i++) {
            strs[i] = new String[len];
        }
        String[] split = s.split("");
        int index = 0;
        L: for (int i = 0; i < len; i++) {
            int a = i % (numRows / 2 + 1);
            if ((i & 1) != 0) {
                for (int j = numRows - 1; j >= 0; j--) {
                    if (a <= j && (numRows - a - 1) >= j) {
                        strs[j][i] = split[index++];
                    }
                    if (index >= len) {
                        break L;
                    }
                }
            } else {
                for (int j = 0; j < numRows; j++) {
                    if (a <= j && (numRows - a - 1) >= j) {
                        strs[j][i] = split[index++];
                    }
                    if (index >= len) {
                        break L;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String[] strings : strs) {
            for (String str : strings) {
                if (str != null) {
                    sb.append(str);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        P6 p6 = new P6();
        System.out.println(p6.convert("ABC", 1));
        System.out.println(p6.convert("PAYPALISHIRING", 3));// PAHNAPLSIIGYIR
        System.out.println("PAHNAPLSIIGYIR".endsWith(p6.convert("PAYPALISHIRING", 3)));
        System.out.println(p6.convert("ABCDEFGHIJK", 5));// AIBHJCGKDFE
        System.out.println("AIBHJCGKDFE".endsWith(p6.convert("ABCDEFGHIJK", 5)));
        System.out.println(p6.convert("PAYPALISHIRING", 4));// PINALSIGYAHRPI
        System.out.println("PINALSIGYAHRPI".endsWith(p6.convert("PAYPALISHIRING", 4)));
    }
}

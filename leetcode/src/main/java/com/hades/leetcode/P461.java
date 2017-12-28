package com.hades.leetcode;

public class P461 {
    public int hammingDistance(int x, int y) {
        char[] chs = Integer.toBinaryString(x ^ y).toCharArray();
        int count = 0;
        for (char ch : chs) {
            if (ch == '1') {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(Integer.bitCount(1 ^ 4));
        System.out.println(new P461().hammingDistance(1, 4));
    }
}

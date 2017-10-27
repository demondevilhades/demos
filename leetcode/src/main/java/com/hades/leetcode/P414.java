package com.hades.leetcode;

public class P414 {
    public int thirdMax(int[] nums) {
        long m1 = Long.MIN_VALUE;
        long m2 = Long.MIN_VALUE;
        long m3 = Long.MIN_VALUE;
        for (int i : nums) {
            if (i > m1) {
                m3 = m2;
                m2 = m1;
                m1 = i;
            } else if (i < m1 && i > m2) {
                m3 = m2;
                m2 = i;
            } else if (i < m2 && i > m3) {
                m3 = i;
            }
        }
        return (int) (m3 == Long.MIN_VALUE ? m1 : m3);
    }

    public static void main(String[] args) {
        System.out.println(new P414().thirdMax(new int[] { 1, -2147483648, 2 }));
        System.out.println(new P414().thirdMax(new int[] { 5, 2, 2 }));
        System.out.println(new P414().thirdMax(new int[] { 3, 2, 1 }));
    }
}

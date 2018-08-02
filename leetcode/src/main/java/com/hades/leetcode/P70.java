package com.hades.leetcode;

public class P70 {
    public int climbStairs(int n) {
        int i = 1, j = 1;
        while (n-- > 1) {
            j = i + j;
            i = j - i;
        }
        return j;
    }

    public static void main(String[] args) {
        P70 p70 = new P70();
        System.out.println(p70.climbStairs(2));
        System.out.println(p70.climbStairs(3));
        System.out.println(p70.climbStairs(4));
    }
}

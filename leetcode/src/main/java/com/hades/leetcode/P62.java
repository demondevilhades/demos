package com.hades.leetcode;

public class P62 {
    public int uniquePaths(int m, int n) {
        return c(m - 1, m + n - 2);
    }

    private int c(int u, int d) {
        long i = u <= (d - u) ? u : d - u;
        long j = i;
        long result = 1;
        while (i-- > 0) {
            result *= (d--);
        }
        while (j > 0) {
            result /= (j--);
        }
        return (int) result;
    }

    public static void main(String[] args) {
        P62 p62 = new P62();
        System.out.println(p62.uniquePaths(1, 2));
        System.out.println(p62.uniquePaths(7, 3));
        System.out.println(p62.uniquePaths(1, 10));
        System.out.println(p62.uniquePaths(4, 4));
        System.out.println(p62.uniquePaths(10, 10));
    }
}

package com.hades.leetcode;

public class P72 {
    /**
     * from https://leetcode.com/problems/edit-distance/discuss/25849/Java-DP-solution-O(nm)
     * 
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] cost = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            cost[i][0] = i;
        }
        for (int i = 1; i <= n; i++) {
            cost[0][i] = i;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    cost[i + 1][j + 1] = cost[i][j];
                } else {
                    int a = cost[i][j];
                    int b = cost[i][j + 1];
                    int c = cost[i + 1][j];
                    cost[i + 1][j + 1] = a < b ? (a < c ? a : c) : (b < c ? b : c);
                    cost[i + 1][j + 1]++;
                }
            }
        }
        return cost[m][n];
    }

    public static void main(String[] args) {
        P72 p72 = new P72();
        System.out.println(p72.minDistance("horse", "ros"));
        System.out.println(p72.minDistance("intention", "execution"));
        System.out.println(p72.minDistance("aaaaaaaaaaaaaaaaaaaaaabaaa", "aaabaaaaaaaaaaaaaaaaaaaaaa"));
    }
}

package com.hades.leetcode;

public class P64 {
    public int minPathSum(int[][] grid) {
        int[][] minSum = new int[grid.length][grid[0].length];

        minSum[0][0] = grid[0][0];
        for (int i = 1; i < grid.length; i++) {
            minSum[i][0] = grid[i][0] + minSum[i - 1][0];
        }
        for (int i = 1; i < grid[0].length; i++) {
            minSum[0][i] = grid[0][i] + minSum[0][i - 1];
        }
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                minSum[i][j] = grid[i][j] + (minSum[i - 1][j] > minSum[i][j - 1] ? minSum[i][j - 1] : minSum[i - 1][j]);
            }
        }
        return minSum[grid.length - 1][grid[0].length - 1];
    }

    public static void main(String[] args) {
        P64 p64 = new P64();
        System.out.println(p64.minPathSum(new int[][] { { 1, 3, 1 }, { 1, 5, 1 }, { 4, 2, 1 } }));
    }
}

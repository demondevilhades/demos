package com.hades.leetcode;

public class P200 {
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    clear(grid, i, j);
                }
            }
        }
        return count;
    }

    private void clear(char[][] grid, int i, int j) {
        grid[i][j] = 0;
        if (i > 0 && grid[i - 1][j] == '1') {
            clear(grid, i - 1, j);
        }
        if (j > 0 && grid[i][j - 1] == '1') {
            clear(grid, i, j - 1);
        }
        if (i + 1 < grid.length && grid[i + 1][j] == '1') {
            clear(grid, i + 1, j);
        }
        if (j + 1 < grid[i].length && grid[i][j + 1] == '1') {
            clear(grid, i, j + 1);
        }
    }
}

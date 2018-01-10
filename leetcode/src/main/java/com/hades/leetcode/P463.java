package com.hades.leetcode;

public class P463 {
    public int islandPerimeter(int[][] grid) {
        int l = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    l += 4;
                    if (i != 0) {
                        if (grid[i - 1][j] == 1) {
                            l--;
                        }
                    }
                    if (i != grid.length - 1) {
                        if (grid[i + 1][j] == 1) {
                            l--;
                        }
                    }
                    if (j != 0) {
                        if (grid[i][j - 1] == 1) {
                            l--;
                        }
                    }
                    if (j != grid[i].length - 1) {
                        if (grid[i][j + 1] == 1) {
                            l--;
                        }
                    }
                }
            }
        }
        return l;
    }

    public static void main(String[] args) {
        System.out.println(new P463().islandPerimeter(new int[][] { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 1, 0, 0 },
                { 1, 1, 0, 0 } }));
    }
}

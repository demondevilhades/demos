package com.hades.leetcode;

public class P85 {
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int max = 0;
        int[][] up = new int[matrix.length][matrix[0].length];
        for (int j = 0; j < up[0].length; j++) {
            if (matrix[0][j] == '1') {
                up[0][j] = 1;
            }
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '1') {
                    up[i][j] = up[i - 1][j] + 1;
                }
            }
        }
        int partMax;
        for (int i = 0; i < up.length; i++) {
            partMax = largestRectangleArea(up[i]);
            max = max >= partMax ? max : partMax;
        }
        return max;
    }

    private int largestRectangleArea(int[] heights) {
        int max = 0;
        int a;
        int min;
        int change;
        for (int i = 0; i < heights.length; i = change) {
            min = heights[i];
            max = max >= min ? max : min;
            change = i;
            for (int j = i + 1; j < heights.length; j++) {
                if (min >= heights[j]) {
                    min = heights[j];
                } else if (change == i) {
                    change = j;
                }
                a = min * (j - i + 1);
                max = max >= a ? max : a;
            }
            if (change == i) {
                break;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        P85 p85 = new P85();
        System.out.println(p85.maximalRectangle(new char[][] { { '1', '0', '1', '0', '0' },
                { '1', '0', '1', '1', '1' }, { '1', '1', '1', '1', '1' }, { '1', '0', '0', '1', '0' } }));
    }
}

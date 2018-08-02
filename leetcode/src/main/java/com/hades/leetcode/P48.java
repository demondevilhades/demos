package com.hades.leetcode;

public class P48 {
    public void rotate(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return;
        }
        if ((matrix.length & 1) != 0) {
            int n = matrix.length;
            int len = n / 2;
            int t;
            for (int i = 0; i < len + 1; i++) {
                for (int j = 0; j < len; j++) {
                    t = matrix[i][j];
                    matrix[i][j] = matrix[n - j - 1][i];
                    matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                    matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                    matrix[j][n - i - 1] = t;
                }
            }
        } else {
            int n = matrix.length;
            int len = n / 2;
            int t;
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    t = matrix[i][j];
                    matrix[i][j] = matrix[n - j - 1][i];
                    matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                    matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                    matrix[j][n - i - 1] = t;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        P48 p48 = new P48();
        p48.rotate(matrix);
        PrintUtils.print(matrix);
        matrix = new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
        p48.rotate(matrix);
        PrintUtils.print(matrix);
        matrix = new int[][] { { 1, 2 }, { 3, 4 } };
        p48.rotate(matrix);
        PrintUtils.print(matrix);
    }
}

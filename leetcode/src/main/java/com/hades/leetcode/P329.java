package com.hades.leetcode;

public class P329 {

    public int longestIncreasingPath(int[][] matrix) {
        int result = 0;
        int xLen = matrix.length;
        int i, j, step;
        if (xLen > 0) {
            int yLen = matrix[0].length;
            int[][] map = new int[xLen][];
            for (int k = 0; k < map.length; k++) {
                map[k] = new int[yLen];
            }
            if (yLen > 0) {
                for (i = 0; i < xLen; i++) {
                    for (j = 0; j < yLen; j++) {
                        step = step(i, j, 1, matrix, xLen, yLen, map);
                        map[i][j] = step;
                        if (step > result) {
                            result = step;
                        }
                    }
                }
            }
        }
        return result;
    }

    private int step(int x, int y, int step, int[][] matrix, int xLen, int yLen, int[][] map) {
        if (map[x][y] != 0) {
            return map[x][y];
        }
        int[] s = { 0, 0, 0, 0 };
        if (x + 1 < xLen && matrix[x + 1][y] > matrix[x][y]) {
            s[0] = step(x + 1, y, step, matrix, xLen, yLen, map) + 1;
        }
        if (x - 1 >= 0 && matrix[x - 1][y] > matrix[x][y]) {
            s[1] = step(x - 1, y, step, matrix, xLen, yLen, map) + 1;
        }
        if (y + 1 < yLen && matrix[x][y + 1] > matrix[x][y]) {
            s[2] = step(x, y + 1, step, matrix, xLen, yLen, map) + 1;
        }
        if (y - 1 >= 0 && matrix[x][y - 1] > matrix[x][y]) {
            s[3] = step(x, y - 1, step, matrix, xLen, yLen, map) + 1;
        }
        int max = step;
        for (int i : s) {
            if (i > max) {
                max = i;
            }
        }
        map[x][y] = max;
        return max;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        P329 p329 = new P329();
        int[][] m1 = { { 9, 9, 4 }, { 6, 6, 8 }, { 2, 1, 1 } };
        System.out.println(p329.longestIncreasingPath(m1));
        int[][] m2 = { { 3, 4, 5 }, { 3, 2, 6 }, { 2, 2, 1 } };
        System.out.println(p329.longestIncreasingPath(m2));
        int[][] m3 = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 19, 18, 17, 16, 15, 14, 13, 12, 11, 10 },
                { 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 }, { 39, 38, 37, 36, 35, 34, 33, 32, 31, 30 },
                { 40, 41, 42, 43, 44, 45, 46, 47, 48, 49 }, { 59, 58, 57, 56, 55, 54, 53, 52, 51, 50 },
                { 60, 61, 62, 63, 64, 65, 66, 67, 68, 69 }, { 79, 78, 77, 76, 75, 74, 73, 72, 71, 70 },
                { 80, 81, 82, 83, 84, 85, 86, 87, 88, 89 }, { 99, 98, 97, 96, 95, 94, 93, 92, 91, 90 },
                { 100, 101, 102, 103, 104, 105, 106, 107, 108, 109 },
                { 119, 118, 117, 116, 115, 114, 113, 112, 111, 110 },
                { 120, 121, 122, 123, 124, 125, 126, 127, 128, 129 },
                { 139, 138, 137, 136, 135, 134, 133, 132, 131, 130 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
        System.out.println(p329.longestIncreasingPath(m3));
        System.out.println(System.currentTimeMillis());
    }
}

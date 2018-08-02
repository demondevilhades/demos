package com.hades.leetcode;

public class P79 {
    public boolean exist(char[][] board, String word) {
        char[] chs = word.toCharArray();
        boolean[][] arrived = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == chs[0]) {
                    arrived[i][j] = true;
                    if (check(chs, board, 1, i, j, arrived)) {
                        return true;
                    }
                    arrived[i][j] = false;
                }
            }
        }
        return false;
    }

    private boolean check(char[] chs, char[][] board, int index, int i, int j, boolean[][] arrived) {
        if (index == chs.length) {
            return true;
        }
        if (i > 0 && board[i - 1][j] == chs[index] && !arrived[i - 1][j]) {
            arrived[i - 1][j] = true;
            if (check(chs, board, index + 1, i - 1, j, arrived)) {
                return true;
            }
            arrived[i - 1][j] = false;
        }
        if (j > 0 && board[i][j - 1] == chs[index] && !arrived[i][j - 1]) {
            arrived[i][j - 1] = true;
            if (check(chs, board, index + 1, i, j - 1, arrived)) {
                return true;
            }
            arrived[i][j - 1] = false;
        }
        if (i < board.length - 1 && board[i + 1][j] == chs[index] && !arrived[i + 1][j]) {
            arrived[i + 1][j] = true;
            if (check(chs, board, index + 1, i + 1, j, arrived)) {
                return true;
            }
            arrived[i + 1][j] = false;
        }
        if (j < board[0].length - 1 && board[i][j + 1] == chs[index] && !arrived[i][j + 1]) {
            arrived[i][j + 1] = true;
            if (check(chs, board, index + 1, i, j + 1, arrived)) {
                return true;
            }
            arrived[i][j + 1] = false;
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] chs = { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } };

        P79 p79 = new P79();
        System.out.println(p79.exist(chs, "ABCCED"));
        System.out.println(p79.exist(chs, "SEE"));
        System.out.println(p79.exist(chs, "ABCB"));
    }
}

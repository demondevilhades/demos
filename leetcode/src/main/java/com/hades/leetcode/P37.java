package com.hades.leetcode;

import java.util.Arrays;

public class P37 {
    public boolean solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (char k = '1'; k <= '9'; k++) {
                        board[i][j] = k;
                        if (v0(board)) {
                            if (solveSudoku(board)) {
                                return true;
                            }
                        }
                    }
                    board[i][j] = '.';
                    return false;
                }
            }
        }
        return true;
    }

    public void solveSudoku2(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (char k = '1'; k <= '9'; k++) {
                        board[i][j] = k;
                        if (v0(board)) {
                            solveSudoku2(board);
                        }
                    }
                    board[i][j] = '.';
                    return;
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
        System.out.println("==================");
    }

    private boolean v0(char[][] s) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = j + 1; k < 9; k++) {
                    if ((s[i][j] != '.' && s[i][j] == s[i][k]) || (s[j][i] != '.' && s[j][i] == s[k][i])) {
                        return false;
                    }
                }
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                for (int k0 = 0; k0 < 3; k0++) {
                    for (int k1 = 0; k1 < 3; k1++) {
                        for (int k2 = 0; k2 < 3; k2++) {
                            for (int k3 = 0; k3 < 3; k3++) {
                                if ((k0 != k2 && k1 != k3) && s[i + k0][j + k1] != '.'
                                        && s[i + k0][j + k1] == s[i + k2][j + k3]) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}

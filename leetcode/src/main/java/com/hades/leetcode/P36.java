package com.hades.leetcode;

public class P36 {
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = j + 1; k < 9; k++) {
                    if ((board[i][j] != '.' && board[i][j] == board[i][k]) || (board[j][i] != '.' && board[j][i] == board[k][i])) {
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
                                if ((k0 != k2 && k1 != k3) && board[i + k0][j + k1] != '.'
                                        && board[i + k0][j + k1] == board[i + k2][j + k3]) {
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

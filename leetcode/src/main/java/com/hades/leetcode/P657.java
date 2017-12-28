package com.hades.leetcode;

public class P657 {
    public boolean judgeCircle(String moves) {
        int x = 0, y = 0;
        char ch;
        for (int i = 0, len = moves.length(), half = len / 2 + 1; i < len; i++) {
            ch = moves.charAt(i);
            switch (ch) {
            case 'R':
                x++;
                break;
            case 'L':
                x--;
                break;
            case 'U':
                y++;
                break;
            case 'D':
                y--;
                break;
            default:
                break;
            }
            if (x + y > half) {
                return false;
            }
        }
        return (x == 0 && y == 0);
    }

    public static void main(String[] args) {
        System.out.println(new P657().judgeCircle(""));
    }
}

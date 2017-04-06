package com.hades.leetcode;

public class P13 {
    public int romanToInt(String s) {
        int len = s.length();
        int sum = 0;
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == 'I') {
                if (i != len - 1 && (s.charAt(i + 1) == 'V' || s.charAt(i + 1) == 'X')) {
                    sum--;
                } else {
                    sum++;
                }
            } else if (s.charAt(i) == 'V') {
                sum += 5;
            } else if (s.charAt(i) == 'X') {
                if (i != len - 1 && (s.charAt(i + 1) == 'C' || s.charAt(i + 1) == 'L')) {
                    sum -= 10;
                } else {
                    sum += 10;
                }
            } else if (s.charAt(i) == 'L') {
                sum += 50;
            } else if (s.charAt(i) == 'C') {
                if (i != len - 1 && (s.charAt(i + 1) == 'D' || s.charAt(i + 1) == 'M')) {
                    sum -= 100;
                } else {
                    sum += 100;
                }
            } else if (s.charAt(i) == 'D') {
                sum += 500;
            } else if (s.charAt(i) == 'M') {
                sum += 1000;
            }
        }
        return sum;
    }

    public static void main(String[] args) {

    }
}

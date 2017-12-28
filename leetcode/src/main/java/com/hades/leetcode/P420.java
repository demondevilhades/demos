package com.hades.leetcode;

/**
 * TODO
 * 
 * @author HaDeS
 */
public class P420 {
    public int strongPasswordChecker(String s) {
        if (s == null || s.length() < 6 || s.length() > 20) {
            return 1;
        }
        char[] chs = s.toCharArray();
        boolean b0 = true, b1 = true, b2 = true;
        int c = 0;
        char cl = chs[0];
        for (char ch : chs) {
            if (b0 && ch >= 97 && ch <= 122) {
                b0 = false;
            }
            if (b1 && ch >= 65 && ch <= 90) {
                b1 = false;
            }
            if (b2 && ch >= 48 && ch <= 57) {
                b2 = false;
            }
            if (c < 3) {
                if (c == 0) {
                    c = 1;
                } else {
                    if (cl == ch) {
                        c++;
                    } else {
                        cl = ch;
                        c = 1;
                    }
                }
            }
        }
        if (b0 || b1 || b2) {
            return 2;
        }
        if (c < 3) {
            return 3;
        }
        return 0;
    }

    public static void main(String[] args) {
        P420 p = new P420();
        System.out.println(p.strongPasswordChecker("azAZ1234567890"));
    }
}

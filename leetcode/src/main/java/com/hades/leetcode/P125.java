package com.hades.leetcode;

import java.util.regex.Pattern;

public class P125 {
    private final Pattern p = Pattern.compile("[\\W]");

    public boolean isPalindrome(String s) {
        char[] chs = p.matcher(s).replaceAll("").toLowerCase().toCharArray();
        int len = chs.length;
        if (len == 0) {
            return true;
        }
        len = len / 2;
        for (int i = 0; i <= len; i++) {
            if (chs[i] != chs[chs.length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new P125().p.matcher("ab c:;,A BC").replaceAll("").toLowerCase());
    }
}

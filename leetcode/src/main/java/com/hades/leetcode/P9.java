package com.hades.leetcode;

public class P9 {
    public boolean isPalindrome(int x) {
        String str = String.valueOf(x);
        int len = str.length();
        int i = 0;
        while (i < len / 2) {
            if (str.charAt(i) != str.charAt(len - i - 1)) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new P9().isPalindrome(11));
    }
}

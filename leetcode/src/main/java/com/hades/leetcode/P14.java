package com.hades.leetcode;

public class P14 {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length < 1) {
            return "";
        }
        String shortestStr = strs[0];
        for (String str : strs) {
            if (str.length() < shortestStr.length()) {
                shortestStr = str;
            }
        }

        StringBuilder sb = new StringBuilder();
        L: for (int i = 0; i < shortestStr.length(); i++) {
            char ch = shortestStr.charAt(i);
            for (String str : strs) {
                if (str.charAt(i) != ch) {
                    break L;
                }
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        P14 p14 = new P14();

    }
}

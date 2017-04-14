package com.hades.leetcode;

public class P28 {
    public int strStr(String haystack, String needle) {
        return haystack == null ? -1 : haystack.indexOf(needle);
    }

    public static void main(String[] args) {
        P28 p28 = new P28();
        System.out.println(p28.strStr("ABCABCABCDABCABCABC", "DA"));
    }
}

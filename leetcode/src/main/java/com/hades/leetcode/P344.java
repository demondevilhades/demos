package com.hades.leetcode;

public class P344 {
    public String reverseString(String s) {
        if (s == null) {
            return null;
        }
        return new StringBuilder(s).reverse().toString();
    }
}

package com.hades.leetcode;

public class P557 {
    public String reverseWords(String s) {
        if (s == null) {
            return null;
        }
        String[] split = s.split(" ");
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String str : split) {
            if (!first) {
                sb.append(" ");
            } else {
                first = false;
            }
            sb.append(new StringBuilder(str).reverse());
        }
        return sb.toString();
    }

}

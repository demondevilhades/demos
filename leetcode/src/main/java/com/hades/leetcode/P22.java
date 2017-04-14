package com.hades.leetcode;

import java.util.LinkedList;
import java.util.List;

public class P22 {

    public List<String> generateParenthesis(int n) {
        List<String> strList = new LinkedList<String>();
        append(0, 0, new StringBuilder(), n * 2, strList);
        return strList;
    }

    private void append(int i, int v, final StringBuilder sb, final int n, final List<String> strList) {
        if (v >= 0 && v * 2 <= n) {
            if (i == n) {
                if (v == 0) {
                    strList.add(sb.toString());
                }
            } else {
                append(i + 1, v + 1, new StringBuilder(sb).append('('), n, strList);
                append(i + 1, v - 1, new StringBuilder(sb).append(')'), n, strList);
            }
        }
    }

    public static void main(String[] args) {
        P22 p22 = new P22();
        List<String> list = p22.generateParenthesis(9);
        for (String str : list) {
            System.out.println(str);
        }
    }
}

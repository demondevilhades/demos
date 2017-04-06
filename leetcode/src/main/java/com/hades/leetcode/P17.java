package com.hades.leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class P17 {
    private final Map<String, char[]> map = new HashMap<String, char[]>() {
        {
            put("2", new char[] { 'a', 'b', 'c' });
            put("3", new char[] { 'd', 'e', 'f' });
            put("4", new char[] { 'g', 'h', 'i' });
            put("5", new char[] { 'j', 'k', 'l' });
            put("6", new char[] { 'm', 'n', 'o' });
            put("7", new char[] { 'p', 'q', 'r', 's' });
            put("8", new char[] { 't', 'u', 'v' });
            put("9", new char[] { 'w', 'x', 'y', 'z' });
            put("0", new char[] { ' ' });
            put("*", new char[] { '+' });
        }
    };

    private final List<String> strList = new LinkedList<String>();
    private int len;
    private String[] split;

    public List<String> letterCombinations(String digits) {
        strList.clear();
        if ("".endsWith(digits)) {
            return strList;
        }
        split = digits.split("");
        len = split.length;
        append(0, new StringBuilder());
        return strList;
    }

    private void append(int i, final StringBuilder sb) {
        if (i == len) {
            strList.add(sb.toString());
        } else {
            char[] chs = map.get(split[i]);
            if (chs == null) {
                append(i + 1, sb);
            } else {
                for (int j = 0; j < chs.length; j++) {
                    append(i + 1, new StringBuilder(sb).append(chs[j]));
                }
            }
        }
    }

    /**
     * You are here! Your runtime beats 2.02% of java submissions.
     * 
     * @param args
     */
    public static void main(String[] args) {
        P17 p17 = new P17();
        {
            List<String> letterCombinations = p17.letterCombinations("23");
            for (String str : letterCombinations) {
                System.out.println(str);
            }
        }
        {
            List<String> letterCombinations = p17.letterCombinations("");
            for (String str : letterCombinations) {
                System.out.println(str);
            }
        }
    }
}

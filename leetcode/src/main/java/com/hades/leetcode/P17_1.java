package com.hades.leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class P17_1 {
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

    public List<String> letterCombinations(String digits) {
        List<String> strList = new LinkedList<String>();
        if ("".endsWith(digits)) {
            return strList;
        }
        String[] split = digits.split("");
        int len = split.length;
        List<StringBuilder> sbList = new LinkedList<StringBuilder>();
        sbList.add(new StringBuilder());
        int i = 0;
        while (i < len) {
            char[] chs = map.get(split[i]);
            if (chs != null) {
                List<StringBuilder> sbListNew = new LinkedList<StringBuilder>();
                for (int j = 0; j < chs.length; j++) {
                    for (StringBuilder sb : sbList) {
                        sbListNew.add(new StringBuilder(sb).append(chs[j]));
                    }
                }
                sbList = sbListNew;
            }
            i++;
        }
        for (StringBuilder sb : sbList) {
            strList.add(sb.toString());
        }
        return strList;
    }

    /**
     * You are here! Your runtime beats 2.54% of java submissions.
     * 
     * @param args
     */
    public static void main(String[] args) {
        P17_1 p17 = new P17_1();
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

package com.hades.leetcode;

import java.util.HashMap;
import java.util.Map;

public class P76 {
    public String minWindow(String s, String t) {
        String result = "";
        char[] sChs = s.toCharArray();
        Map<Character, Integer> tMap = getChMap(t);
        int size = Integer.MAX_VALUE;
        int start = 0;
        int end = 0;
        for (end = 0; end < sChs.length; end++) {
            System.out.println("start=" + start + ", end=" + end);
            Integer count = tMap.get(sChs[end]);
            if (count == null) {
                if (start == end) {
                    start++;
                }
            } else {
                tMap.put(sChs[end], count - 1);
                while (tMap.get(sChs[start]) < 0) {
                    tMap.put(sChs[start], tMap.get(sChs[start]) + 1);
                    start++;
                    while (start < end && !tMap.containsKey(sChs[start])) {
                        start++;
                    }
                }
                if (checkMap(tMap)) {
                    int c = end - start + 1;
                    if (size > c) {
                        size = c;
                        result = new String(sChs, start, c);
                    }
                }
            }
        }
        return result;
    }

    private boolean checkMap(Map<Character, Integer> tMap) {
        for (Integer count : tMap.values()) {
            if (count > 0) {
                return false;
            }
        }
        return true;
    }

    private Map<Character, Integer> getChMap(String t) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            Integer count = map.get(ch);
            if (count == null) {
                map.put(ch, 1);
            } else {
                map.put(ch, count + 1);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        P76 p76 = new P76();
        System.out.println(p76.minWindow("ADOBECODEBANC", "ABC")); // BANC
        System.out.println(p76.minWindow("cabwefgewcwaefgcf", "cae"));// cwae
    }
}

package com.hades.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P49 {

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<List<String>>();
        if (strs != null && strs.length != 0) {
            Map<String, List<String>> map = new HashMap<String, List<String>>();
            for (String str : strs) {
                String newStr = getNewStr(str);
                List<String> list = map.get(newStr);
                if (list == null) {
                    list = new ArrayList<String>();
                    map.put(newStr, list);
                    result.add(list);
                }
                list.add(str);
            }
        }
        return result;
    }

    private String getNewStr(String str) {
        char[] chs = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char ch = 'a'; ch <= 'z'; ch++) {
            for (char c : chs) {
                if (c == ch) {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        P49 p49 = new P49();
        PrintUtils.print(p49.groupAnagrams(new String[] { "eat", "tea", "tan", "ate", "nat", "bat" }));
    }
}

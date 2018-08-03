package com.hades.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class P139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        for (ListIterator<String> iterator = wordDict.listIterator(); iterator.hasNext();) {
            String word = iterator.next();
            iterator.remove();
            if (c(word, wordDict, new ArrayList<Integer>())) {
                continue;
            }
            iterator.add(word);
        }
        // for (ListIterator<String> iterator = wordDict.listIterator();
        // iterator.hasNext();) {
        // String word = iterator.next();
        // int nextIndex = iterator.nextIndex();
        //
        // ListIterator<String> iterator2 =
        // wordDict.listIterator(iterator.nextIndex());
        // while (iterator2.hasNext()) {
        // String word2 = iterator2.next();
        // if (word.length() > word2.length() && word.replaceAll(word2,
        // "").isEmpty()) {
        // iterator.remove();
        // break;
        // } else if (word.length() < word2.length() && word2.replaceAll(word,
        // "").isEmpty()) {
        // iterator2.remove();
        // }
        // }
        // iterator = wordDict.listIterator(nextIndex);
        // }
        return c(s, wordDict, new ArrayList<Integer>());
    }

    private boolean c(String s, List<String> wordDict, List<Integer> jumpLenList) {
        if (s.length() == 0) {
            return true;
        }
        for (String word : wordDict) {
            int len = s.length() - word.length();
            if (jumpLenList.contains(len)) {
                continue;
            }
            if (s.startsWith(word)) {
                if (c(s.substring(word.length(), s.length()), wordDict, jumpLenList)) {
                    return true;
                } else {
                    jumpLenList.add(s.length() - word.length());
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        P139 p139 = new P139();
        String s = "bccdbacdbdacddabbaaaadababadad";
        List<String> wordDict = Arrays.asList(new String[] { "cbc", "bcda", "adb", "ddca", "bad", "bbb", "dad", "dac",
                "ba", "aa", "bd", "abab", "bb", "dbda", "cb", "caccc", "d", "dd", "aadb", "cc", "b", "bcc", "bcd",
                "cd", "cbca", "bbd", "ddd", "dabb", "ab", "acd", "a", "bbcc", "cdcbd", "cada", "dbca", "ac", "abacd",
                "cba", "cdb", "dbac", "aada", "cdcda", "cdc", "dbc", "dbcb", "bdb", "ddbdd", "cadaa", "ddbc", "babb" });
        System.out.println(p139.wordBreak(s, new LinkedList<String>(wordDict)));
        s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        wordDict = Arrays.asList(new String[] { "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa",
                "aaaaaaaaa", "aaaaaaaaaa", "ba" });
        System.out.println(p139.wordBreak(s, new LinkedList<String>(wordDict)));
        s = "leetcode";
        wordDict = Arrays.asList(new String[] { "leet", "code" });
        System.out.println(p139.wordBreak(s, new LinkedList<String>(wordDict)));
    }
}

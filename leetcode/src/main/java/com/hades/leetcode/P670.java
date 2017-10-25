package com.hades.leetcode;

public class P670 {
    public int maximumSwap(int num) {
        char[] chs = String.valueOf(num).toCharArray();
        int i = 0;
        int len = chs.length;
        int j = len - 1;
        while (i < len - 1) {
            if (chs[i] < chs[j]) {
                int k = j - 1;
                while (k > i) {
                    if (chs[j] < chs[k]) {
                        j = k;
                    }
                    k--;
                }
                break;
            }
            if (chs[i] == '9') {
                i++;
                j = len - 1;
                continue;
            }
            j--;
            if (j == i) {
                i++;
                j = len - 1;
            }
        }
        if (i < len - 1) {
            char ch = chs[i];
            chs[i] = chs[j];
            chs[j] = ch;
        }
        return Integer.parseInt(new String(chs));
    }
}

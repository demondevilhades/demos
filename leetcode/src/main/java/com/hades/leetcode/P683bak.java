package com.hades.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 理解错了，开的是值不是索引(以为k是连续k个，没错…)
 * 
 * @author HaDeS
 *
 */
public class P683bak {

    private List<Integer> iList = new ArrayList<Integer>();

    public int kEmptySlots(int[] flowers, int k) {
        iList.clear();
        int len = flowers.length;

        putIndex(1, len, k, flowers);
        int max = getMaxLen(len);

        int i = 1;
        while (max > k && i < len) {
            if (putIndex(++i, len, k, flowers)) {
                return i;
            }
            max = getMaxLen(len);
        }
        return -1;
    }

    private boolean putIndex(int i, int len, int k, int[] flowers) {
        int j = 0;
        int m = 0;
        int size = iList.size();
        for (; j < len; j++) {
            if (flowers[j] == i) {
                for (; m < size; m++) {
                    if (iList.get(m) > j) {
                        break;
                    }
                }
                iList.add(m, j);
                break;
            }
        }
        if (m > 0 && (j - iList.get(m - 1) - 1 == k)) {
            return true;
        }
        if (m < size - 2 && (iList.get(m + 1) - j - 1 == k)) {
            return true;
        }
        return false;
    }

    private int getMaxLen(int len) {
        int max = 0;
        int p = 0;
        for (int i : iList) {
            if (i - p - 1 > max) {
                max = i - p - 1;
            }
            p = i;
        }
        if (len - p - 1 > max) {
            max = len - p - 1;
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new P683bak().kEmptySlots(new int[] { 1, 3, 2 }, 1));// 2
        System.out.println(new P683bak().kEmptySlots(new int[] { 6, 5, 8, 9, 7, 1, 10, 2, 3, 4 }, 2));// 8
    }
}

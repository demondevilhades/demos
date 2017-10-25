package com.hades.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * 
 * @author HaDeS
 */
public class P683 {

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
        int j = flowers[i - 1];
        int m = 0;
        int size = iList.size();
        for (; m < size; m++) {
            if (iList.get(m) > j) {
                break;
            }
        }
        iList.add(m, j);
        size = iList.size();
        if (m > 0 && (j - iList.get(m - 1) - 1 == k)) {
            return true;
        }
        if (m < size - 1 && (iList.get(m + 1) - j - 1 == k)) {
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
        if (len - p > max) {
            max = len - p;
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new P683().kEmptySlots(new int[] { 1, 3, 2 }, 1));// 2
        System.out.println(new P683().kEmptySlots(new int[] { 6, 5, 8, 9, 7, 1, 10, 2, 3, 4 }, 2));// 8
        System.out.println(new P683().kEmptySlots(new int[] { 3, 9, 2, 8, 1, 6, 10, 5, 4, 7 }, 1));// 6
        System.out.println(new P683().kEmptySlots(new int[] { 10, 1, 9, 3, 5, 7, 6, 4, 8, 2 }, 8));// 2
    }
}

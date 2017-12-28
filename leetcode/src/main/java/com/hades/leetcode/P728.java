package com.hades.leetcode;

import java.util.ArrayList;
import java.util.List;

public class P728 {
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> list = new ArrayList<Integer>();
        int[] nums;
        L: for (int i = left; i <= right; i++) {
            if (i < 10) {
                list.add(i);
            } else {
                nums = getNums(i);
                for (int num : nums) {
                    if (num == 0 || i % num != 0) {
                        continue L;
                    }
                }
                list.add(i);
            }
        }
        return list;
    }

    private int[] getNums(int num) {
        int[] nums = new int[stringSize(num)];
        int i = 0;
        while (num > 0) {
            nums[i++] = num % 10;
            num /= 10;
        }
        return nums;
    }

    private final int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };

    private int stringSize(int x) {
        for (int i = 0;; i++)
            if (x <= sizeTable[i])
                return i + 1;
    }

    public static void main(String[] args) {
        List<Integer> list = new P728().selfDividingNumbers(1, 10000);
        for (int i : list) {
            System.out.println(i);
        }
    }
}

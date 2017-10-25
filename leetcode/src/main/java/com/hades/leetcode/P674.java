package com.hades.leetcode;

public class P674 {
    public int findLengthOfLCIS(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }
        int max = 1;
        int i = 0;
        int a = 1;
        while (++i < len) {
            if (nums[i] > nums[i - 1]) {
                a++;
                if (max < a) {
                    max = a;
                }
            } else {
                a = 1;
            }
        }
        return max;
    }
}

package com.hades.leetcode;

import java.util.Arrays;

public class P136 {
    public int singleNumber(int[] nums) {
        Arrays.sort(nums);
        int i = 0;
        for (; i < nums.length - 1; i += 2) {
            if (nums[i] != nums[i + 1]) {
                break;
            }
        }
        return i > nums.length ? nums[i - 1] : nums[i];
    }
}

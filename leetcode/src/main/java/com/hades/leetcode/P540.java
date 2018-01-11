package com.hades.leetcode;

public class P540 {
    public int singleNonDuplicate(int[] nums) {
        for (int i = 0; i < nums.length - 1;) {
            if (nums[i] == nums[i + 1]) {
                i += 2;
            } else {
                return nums[i];
            }
        }
        return nums[nums.length - 1];
    }
}

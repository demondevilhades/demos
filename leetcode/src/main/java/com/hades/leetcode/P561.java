package com.hades.leetcode;

import java.util.Arrays;

public class P561 {
    public int arrayPairSum(int[] nums) {
        int sum = 0;
        Arrays.parallelSort(nums);
        for (int i = 0, s = nums.length / 2; i < s; i++) {
            sum += nums[i * 2];
        }
        return sum;
    }

}

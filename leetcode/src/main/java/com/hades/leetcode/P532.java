package com.hades.leetcode;

import java.util.Arrays;

public class P532 {
    public int findPairs(int[] nums, int k) {
        if (nums.length == 0 || k < 0) {
            return 0;
        }
        int count = 0;
        Arrays.sort(nums);
        int s;
        for (int i = 0; i < nums.length - 1; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                s = nums[j] - nums[i];
                if (s == k) {
                    count++;
                } else if (s > k) {
                    break;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(new P532().findPairs(new int[] { 3, 1, 4, 1, 5 }, 2));// 2
        System.out.println(new P532().findPairs(new int[] { 1, 3, 1, 5, 4 }, 0));// 1
        System.out.println(new P532().findPairs(new int[] { 1, 1, 1, 1, 1 }, 0));// 1
    }
}

package com.hades.leetcode;

import java.util.Arrays;

public class P189 {
    public void rotate(int[] nums, int k) {
        if (nums.length > 1) {
            while (k > nums.length) {
                k -= nums.length;
            }
            if (k != 0) {
                int[] nums0 = Arrays.copyOfRange(nums, 0, nums.length - k);
                int[] nums1 = Arrays.copyOfRange(nums, nums.length - k, nums.length);
                System.arraycopy(nums0, 0, nums, k, nums0.length);
                System.arraycopy(nums1, 0, nums, 0, nums1.length);
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2 };
        new P189().rotate(nums, 1);
        System.out.println(Arrays.toString(nums));
    }
}

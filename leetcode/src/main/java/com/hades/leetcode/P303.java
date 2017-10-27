package com.hades.leetcode;

public class P303 {

    static class NumArray {

        private int[] nums;

        public NumArray(int[] nums) {
            this.nums = nums;
        }

        public int sumRange(int i, int j) {
            int sum = 0;
            for (int k = (i >= 0 ? i : 0), len = (j < nums.length ? (j + 1) : nums.length); k < len; k++) {
                sum += nums[k];
            }
            return sum;
        }
    }

    static class NumArray2 {

        private int[] nums;

        public NumArray2(int[] nums) {
            this.nums = new int[nums.length];
            if (nums.length > 0) {
                this.nums[0] = nums[0];
                for (int i = 1; i < nums.length; i++) {
                    this.nums[i] = nums[i] + this.nums[i - 1];
                }
            }
        }

        public int sumRange(int i, int j) {
            return i > 0 ? this.nums[j] - this.nums[i - 1] : this.nums[j];
        }
    }
}

package com.hades.leetcode;

import java.util.Random;

public class P398 {
    public static class Solution {

        private int[] nums;
        private int len;
        private Random random = new Random();

        public Solution(int[] nums) {
            this.nums = nums;
            this.len = nums.length;
        }

        public int pick(int target) {
            int result = -1;
            int count = 0;
            for (int i = 0; i < len; i++) {
                if (nums[i] != target) {
                    continue;
                }
                if (random.nextInt(++count) == 0) {
                    result = i;
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {

    }
}

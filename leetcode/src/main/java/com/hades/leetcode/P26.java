package com.hades.leetcode;

public class P26 {
    public int removeDuplicates(int[] nums) {
        int len = 0;
        if (nums.length > 0) {
            int[] _nums = new int[nums.length];
            _nums[0] = nums[0];
            int j = 0;
            len++;
            for (int i = 1; i < nums.length; i++) {
                if (_nums[j] == nums[i]) {
                    continue;
                }
                _nums[++j] = nums[i];
                len++;
            }
            for (int i = 1; i < _nums.length; i++) {
                nums[i] = _nums[i];
            }
        }
        return len;
    }

    public static void main(String[] args) {
        P26 p26 = new P26();
        System.out.println(p26.removeDuplicates(new int[] { 1, 1, 2 }));
    }
}

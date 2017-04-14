package com.hades.leetcode;

import java.util.Arrays;

public class P31 {
    public int[] nextPermutation(int[] nums) {
        boolean b = true;
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i - 1] < nums[i]) {
                b = false;
                break;
            }
        }
        int replace;
        if (b) {
            for (int i = (nums.length - 1) / 2; i >= 0; i--) {
                replace = nums[i];
                nums[i] = nums[nums.length - 1 - i];
                nums[nums.length - 1 - i] = replace;
            }
        } else {
            for (int i = nums.length - 1; i > 0; i--) {
                if (nums[i - 1] < nums[i]) {
                    replace = nums[i];
                    nums[i] = nums[i - 1];
                    nums[i - 1] = replace;
                    break;
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        P31 p31 = new P31();
        System.out.println(Arrays.toString(p31.nextPermutation(new int[] { 1, 2 })));
        System.out.println(Arrays.toString(p31.nextPermutation(new int[] { 1, 2, 3 })));
        System.out.println(Arrays.toString(p31.nextPermutation(new int[] { 1, 3, 2 })));
    }
}

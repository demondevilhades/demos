package com.hades.leetcode;

public class P581 {
    public int findUnsortedSubarray(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }

        int start = 0;
        L: while (start < nums.length) {
            if (!(start > 0 && nums[start] == nums[start - 1])) {
                for (int i = start + 1; i < nums.length; i++) {
                    if (nums[start] > nums[i]) {
                        break L;
                    }
                }
            }
            start++;
        }

        int end = nums.length - 1;
        L: while (end >= start) {
            if (!(end < nums.length - 1 && nums[end] == nums[end - 1])) {
                for (int i = end - 1; i >= start; i--) {
                    if (nums[end] < nums[i]) {
                        break L;
                    }
                }
            }
            end--;
        }

        return end - start + 1;
    }

    public static void main(String[] args) {
        System.out.println(new P581().findUnsortedSubarray(new int[] { 2, 6, 4, 8, 10, 9, 15 }));
    }
}

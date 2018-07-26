package com.hades.leetcode;

public class P33 {
    public int search(int[] nums, int target) {
        int result = -1;
        if (nums.length > 0) {
            if (target >= nums[0]) {
                for (int i = 0; i < nums.length; i++) {
                    if (target == nums[i]) {
                        result = i;
                    } else if (target < nums[i] || (i > 0 && nums[i] < nums[i - 1])) {
                        break;
                    }
                }
            } else {
                for (int i = nums.length - 1; i >= 0; i--) {
                    if (target == nums[i]) {
                        result = i;
                    } else if (target > nums[i] || (i > 0 && nums[i] < nums[i - 1])) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        P33 p33 = new P33();
        System.out.println(p33.search(new int[] {}, 0));
        System.out.println(p33.search(new int[] { 4, 5, 6, 7, 0, 1, 2 }, 0));
        System.out.println(p33.search(new int[] { 4, 5, 6, 7, 0, 1, 2 }, 3));
    }
}

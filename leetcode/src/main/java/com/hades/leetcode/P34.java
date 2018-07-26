package com.hades.leetcode;

import java.util.Arrays;

public class P34 {
    public int[] searchRange(int[] nums, int target) {
        int[] result = { -1, -1 };
        if (nums.length > 0) {
            if (target >= nums[0]) {
                for (int i = 0; i < nums.length; i++) {
                    if (target == nums[i]) {
                        if (result[0] == -1) {
                            result[0] = i;
                            result[1] = i;
                        } else {
                            result[1] = i;
                        }
                    } else if (target < nums[i] || (i > 0 && nums[i] < nums[i - 1])) {
                        break;
                    }
                }
            } else {
                for (int i = nums.length - 1; i >= 0; i--) {
                    if (target == nums[i]) {
                        if (result[1] == -1) {
                            result[0] = i;
                            result[1] = i;
                        } else {
                            result[0] = i;
                        }
                    } else if (target > nums[i] || (i > 0 && nums[i] < nums[i - 1])) {
                        break;
                    }
                }
            }

        }
        return result;
    }

    public static void main(String[] args) {
        P34 p34 = new P34();
        System.out.println(Arrays.toString(p34.searchRange(new int[] { 5, 7, 7, 8, 8, 10 }, 8)));
        System.out.println(Arrays.toString(p34.searchRange(new int[] { 5, 7, 7, 8, 8, 10 }, 6)));
        System.out.println(Arrays.toString(p34.searchRange(new int[] { 1 }, 1)));
    }
}

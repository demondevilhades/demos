package com.hades.leetcode;

public class P665 {
    public boolean checkPossibility(int[] nums) {
        int len = nums.length;
        if (len < 2) {
            return true;
        }
        int a = 0;
        for (int i = 0; i < len - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                if (i != 0 && i + 2 < len && nums[i + 1] < nums[i - 1] && nums[i + 2] < nums[i]) {
                    return false;
                }
                a++;
                if (a > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new P665().checkPossibility(new int[] { 4, 2, 3 }));
        System.out.println(new P665().checkPossibility(new int[] { 2, 3, 3, 2, 4 }));
        System.out.println(new P665().checkPossibility(new int[] { 3, 4, 2, 3 }));
    }
}

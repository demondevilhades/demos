package com.hades.leetcode;

public class P55 {
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        L: for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] == 0) {
                int d = 1;
                while (i - d >= 0) {
                    if (nums[i - d] > d) {
                        i -= d;
                        continue L;
                    }
                    d++;
                }
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        P55 p55 = new P55();
        System.out.println(p55.canJump(new int[] { 2, 3, 1, 1, 4 }));
        System.out.println(p55.canJump(new int[] { 3, 2, 1, 0, 4 }));
        System.out.println(p55.canJump(new int[] { 2, 0, 0 }));
    }
}

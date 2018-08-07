package com.hades.leetcode;

public class P152 {
    @Deprecated
    public int maxProduct2(int[] nums) {
        int part0 = nums[0];
        int part1 = part0 > 0 ? part0 : 0;
        int part2 = 0;
        int max = part0;
        boolean part2Flag = false;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 0) {
                part0 = 0;
                part1 = 0;
                part2 = 0;
                part2Flag = false;
                max = max >= 0 ? max : 0;
            } else if (nums[i] < 0) {
                if (part0 == 0) {
                    part0 = nums[i];
                } else {
                    part0 *= nums[i];
                    max = max >= part0 ? max : part0;
                }
                part1 = 0;
                if (!part2Flag) {
                    part2Flag = true;
                } else {
                    part2 *= nums[i];
                    max = max >= part2 ? max : part2;
                }
            } else {
                if (part0 == 0) {
                    part0 = nums[i];
                } else {
                    part0 *= nums[i];
                    max = max >= part0 ? max : part0;
                }
                if (part1 == 0) {
                    part1 = nums[i];
                } else {
                    part1 *= nums[i];
                    max = max >= part1 ? max : part1;
                }
                if (part2Flag) {
                    part2 *= nums[i];
                    max = max >= part2 ? max : part2;
                }
            }
        }
        return max;
    }

    public int maxProduct(int[] nums) {
        int part0 = nums[0];
        int part1 = part0 > 0 ? part0 : 0;
        boolean part2Flag = part0 < 0 ? true : false;
        int part2 = part2Flag ? 1 : 0;
        int max = part0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 0) {
                part0 = 0;
                part1 = 0;
                part2 = 0;
                part2Flag = false;
                max = max >= 0 ? max : 0;
            } else if (nums[i] < 0) {
                if (part0 == 0) {
                    part0 = nums[i];
                } else {
                    part0 *= nums[i];
                }
                max = max >= part0 ? max : part0;
                part1 = 0;
                if (!part2Flag) {
                    part2Flag = true;
                    part2 = 1;
                } else {
                    part2 *= nums[i];
                    max = max >= part2 ? max : part2;
                }
            } else {
                if (part0 == 0) {
                    part0 = nums[i];
                } else {
                    part0 *= nums[i];
                }
                max = max >= part0 ? max : part0;
                if (part1 == 0) {
                    part1 = nums[i];
                } else {
                    part1 *= nums[i];
                }
                max = max >= part1 ? max : part1;
                if (part2Flag) {
                    part2 *= nums[i];
                    max = max >= part2 ? max : part2;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        P152 p152 = new P152();
        System.out.println(p152.maxProduct(new int[] { 2, 3, -2, 4 }));
        System.out.println(p152.maxProduct(new int[] { -2, 0, -1 }));
        System.out.println(p152.maxProduct(new int[] { 0, 2 }));
        System.out.println(p152.maxProduct(new int[] { 2, -5, -2, -4, 3 }));
        System.out.println(p152.maxProduct(new int[] { -1, -2, -3, 0 }));
    }
}

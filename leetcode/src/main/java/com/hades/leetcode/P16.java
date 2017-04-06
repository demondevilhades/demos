package com.hades.leetcode;

import java.util.Arrays;

public class P16 {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int result = nums[0] + nums[1] + nums[2];
        int sum;
        int a = 0;
        int b = 0;
        int c = 0;
        L: for (int i = 0; i < nums.length - 2; i++) {
            if (i != 0 && a == nums[i]) {
                continue;
            }
            a = nums[i];
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                if (j != i + 1 && nums[j - 1] == nums[j]) {
                    j++;
                    continue;
                }
                b = nums[j];
                if (k != nums.length - 1 && nums[k + 1] == nums[k]) {
                    k--;
                    continue;
                }
                c = nums[k];
                sum = a + b + c;
                if (sum - target > 0) {
                    k--;
                } else if (sum - target < 0) {
                    j++;
                } else {
                    result = sum;
                    break L;
                }
                if (Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        P16 p16 = new P16();
//        System.out.println(p16.threeSumClosest(new int[] { -1, 2, 1, -4 }, 1));
//        System.out.println(p16.threeSumClosest(new int[] { 1, 1, 1, 0 }, 100));
        System.out.println(p16.threeSumClosest(new int[] { 6, -18, -20, -7, -15, 9, 18, 10, 1, -20, -17, -19, -3, -5,
                -19, 10, 6, -11, 1, -17, -15, 6, 17, -18, -3, 16, 19, -20, -3, -17, -15, -3, 12, 1, -9, 4, 1, 12, -2,
                14, 4, -4, 19, -20, 6, 0, -19, 18, 14, 1, -15, -5, 14, 12, -4, 0, -10, 6, 6, -6, 20, -8, -6, 5, 0, 3,
                10, 7, -2, 17, 20, 12, 19, -13, -1, 10, -1, 14, 0, 7, -3, 10, 14, 14, 11, 0, -4, -15, -8, 3, 2, -5, 9,
                10, 16, -4, -3, -9, -8, -14, 10, 6, 2, -12, -7, -16, -6, 10 }, -52));
    }
}

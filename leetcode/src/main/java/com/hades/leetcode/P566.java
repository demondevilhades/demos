package com.hades.leetcode;

import java.util.Arrays;

public class P566 {
    public int[][] matrixReshape(int[][] nums, int r, int c) {
        if (nums.length > 0 && nums[0].length > 0 && (nums.length * nums[0].length == r * c) && nums.length != r) {
            int[][] ret = new int[r][];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = new int[c];
            }

            int i0 = 0;
            int j0 = 0;
            int i1 = 0;
            int j1 = 0;
            int len;
            while (i0 < nums.length) {
                len = Math.min(nums[0].length - j0, c - j1);
                System.arraycopy(nums[i0], j0, ret[i1], j1, len);
                j0 += len;
                j1 += len;
                if (j0 == nums[0].length) {
                    j0 = 0;
                    i0++;
                }
                if (j1 == c) {
                    j1 = 0;
                    i1++;
                }
            }

            return ret;
        }
        return nums;
    }

    public static void main(String[] args) {
        P566 p = new P566();
        int[][] mr = p.matrixReshape(new int[][] { { 1, 2 }, { 3, 4 } }, 1, 4);
        for (int[] is : mr) {
            System.out.println(Arrays.toString(is));
        }
        mr = p.matrixReshape(new int[][] { { 1, 2, 5 }, { 3, 4, 6 } }, 3, 2);
        for (int[] is : mr) {
            System.out.println(Arrays.toString(is));
        }
    }
}

package com.hades.leetcode;

import java.util.Arrays;

public class P75 {
    public void sortColors(int[] nums) {
        int i0 = 0;
        int i = 0;
        int i2 = nums.length - 1;
        int c;
        while (i <= i2) {
            if (nums[i] == 0) {
                if (i != i0) {
                    c = nums[i0];
                    nums[i0] = nums[i];
                    nums[i] = c;
                }
                i0++;
                i++;
            } else if (nums[i] == 2) {
                do {
                    if (nums[i2] == 2) {
                        i2--;
                    } else {
                        c = nums[i2];
                        nums[i2] = nums[i];
                        nums[i] = c;
                        i2--;
                        break;
                    }
                } while (i < i2);
            } else {
                i++;
            }
        }
    }

    public static void main(String[] args) {
        P75 p75 = new P75();
        int[] is;
        is = new int[] { 2, 0, 2, 1, 1, 0 };
        p75.sortColors(is);
        System.out.println(Arrays.toString(is));
        is = new int[] { 2, 0, 1 };
        p75.sortColors(is);
        System.out.println(Arrays.toString(is));
    }
}

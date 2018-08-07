package com.hades.leetcode;

public class P198 {
    public int rob(int[] nums) {
        int n = 0, y = 0, t;
        for (int i : nums) {
            t = n;
            n = n >= y ? n : y;
            y = t + i;
        }
        return n >= y ? n : y;
    }

}

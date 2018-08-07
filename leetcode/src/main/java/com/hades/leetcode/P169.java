package com.hades.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class P169 {
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int m = nums.length / 2;
        for (int i = 0; i < nums.length; i++) {
            Integer count = map.get(nums[i]);
            count = count == null ? 1 : count + 1;
            if (count >= m) {
                return nums[i];
            }
            map.put(nums[i], count);
        }
        return 0;
    }

    public int majorityElement1(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    public static void main(String[] args) {
    }
}

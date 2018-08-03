package com.hades.leetcode;

import java.util.HashMap;
import java.util.Map;

public class P128 {
    public int longestConsecutive(int[] nums) {
        int longest = 0;
        int count;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                Integer count0 = map.get(nums[i] - 1);
                Integer count1 = map.get(nums[i] + 1);
                if (count0 != null) {
                    if (count1 != null) {
                        count = count0 + count1 + 1;
                        map.put(nums[i] - count0, count);
                        map.put(nums[i] + count1, count);
                        map.put(nums[i], count);
                    } else {
                        count = count0 + 1;
                        map.put(nums[i] - count0, count);
                        map.put(nums[i], count);
                    }
                } else if (count1 != null) {
                    count = count1 + 1;
                    map.put(nums[i], count);
                    map.put(nums[i] + count1, count);
                } else {
                    count = 1;
                    map.put(nums[i], count);
                }
                longest = longest >= count ? longest : count;
            }
        }
        return longest;
    }

    public static void main(String[] args) {
        P128 p128 = new P128();
        System.out.println(p128.longestConsecutive(new int[] { 100, 4, 200, 1, 3, 2 }));
        System.out.println(p128.longestConsecutive(new int[] { 1, 2, 0, 1 }));
    }
}

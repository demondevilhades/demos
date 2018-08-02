package com.hades.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class P78 {
    /**
     * from https://leetcode.com/problems/subsets/discuss/122645/3ms-easiest-solution-no-backtracking-no-bit-manipulation-no-dfs-no-bullshit
     * 
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int n : nums) {
            int size = result.size();
            for (int i = 0; i < size; i++) {
                List<Integer> subset = new ArrayList<>(result.get(i));
                subset.add(n);
                result.add(subset);
            }
        }
        return result;
    }

    @Deprecated
    public List<List<Integer>> subsets1(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (nums != null) {
            result.add(new ArrayList<Integer>(0));
            if (nums.length > 0) {
                {
                    List<Integer> list = new ArrayList<Integer>(nums.length);
                    for (int j : nums) {
                        list.add(j);
                    }
                    result.add(list);
                }
                Set<int[]> set;
                for (int len = 1; len < nums.length; len++) {
                    set = new HashSet<int[]>();
                    L: for (int i = 0; i + len <= nums.length; i++) {
                        int[] is = Arrays.copyOfRange(nums, i, i + len);
                        for (int[] js : set) {
                            if (Arrays.equals(is, js)) {
                                continue L;
                            }
                        }
                        set.add(is);
                        List<Integer> list = new ArrayList<Integer>(len);
                        for (int j : is) {
                            list.add(j);
                        }
                        result.add(list);
                    }
                }
            }
        }
        return result;
    }

}

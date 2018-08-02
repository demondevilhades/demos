package com.hades.leetcode;

import java.util.ArrayList;
import java.util.List;

public class P46 {
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) {
            return new ArrayList<List<Integer>>(0);
        }
        int pow = (int) Math.pow(2, nums.length);
        List<List<Integer>> result = new ArrayList<List<Integer>>(pow);

        List<Integer> numList = new ArrayList<Integer>(nums.length);
        for (int i : nums) {
            numList.add(i);
        }
        d(numList, new ArrayList<Integer>(nums.length), result);
        return result;
    }

    private void d(final List<Integer> numList, final List<Integer> list, final List<List<Integer>> result) {
        if (numList.size() == 0) {
            result.add(list);
        } else {
            for (int i = 0; i < numList.size(); i++) {
                List<Integer> newNumList = new ArrayList<Integer>(numList);
                List<Integer> newList = new ArrayList<Integer>(list);
                newList.add(newNumList.remove(i));
                d(newNumList, newList, result);
            }
        }
    }

    public static void main(String[] args) {
        P46 p46 = new P46();
        System.out.println(p46.permute(new int[] { 1, 2, 3 }).size());
    }
}

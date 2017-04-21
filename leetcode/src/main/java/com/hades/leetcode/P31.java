package com.hades.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class P31 {

    private final Comparator comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    };

    @SuppressWarnings("unchecked")
    public int[] nextPermutation(int[] nums) {
        if (nums.length > 0) {
            boolean b = true;
            for (int i = nums.length - 1; i > 0; i--) {
                if (nums[i - 1] < nums[i]) {
                    b = false;
                    break;
                }
            }
            int replace;
            if (b) {
                for (int i = (nums.length - 1) / 2; i >= 0; i--) {
                    replace = nums[i];
                    nums[i] = nums[nums.length - 1 - i];
                    nums[nums.length - 1 - i] = replace;
                }
            } else {
                List<Integer> list = new ArrayList<Integer>();
                for (int i = nums.length - 1; i > 0; i--) {
                    list.add(nums[i]);
                    if (nums[i - 1] < nums[i]) {
                        list.add(nums[i - 1]);
                        Collections.sort(list, comparator);
                        replace = nums[i - 1];
                        for (int l : list) {
                            if (replace < l) {
                                replace = l;
                                break;
                            }
                        }
                        nums[i - 1] = replace;
                        int size = list.size();
                        boolean jump = true;
                        for (int j = 0, h = i; j < size; j++) {
                            int a = list.get(j);
                            if (jump && a == replace) {
                                jump = false;
                                continue;
                            }
                            nums[h++] = a;
                        }
                        break;
                    }
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        P31 p31 = new P31();
        System.out.println(Arrays.toString(p31.nextPermutation(new int[] { 1, 2 })));
        System.out.println(Arrays.toString(p31.nextPermutation(new int[] { 1, 2, 3 })));
        System.out.println(Arrays.toString(p31.nextPermutation(new int[] { 1, 3, 2 })));
    }
}

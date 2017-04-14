package com.hades.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class P18 {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        int len = nums.length;
        if (len < 4) {
            return result;
        }
        Arrays.sort(nums);
        List<Integer> item;
        int sum;
        int a;
        int b;
        int c;
        int d;
        int k;
        int l;
        for (int i = 0; i < len - 3; i++) {
            if (i != 0 && nums[i - 1] == nums[i]) {
                continue;
            }
            a = nums[i];
            for (int j = i + 1; j < len - 2; j++) {
                if (j != i + 1 && nums[j - 1] == nums[j]) {
                    continue;
                }
                b = nums[j];
                k = j + 1;
                l = len - 1;
                while (k < l) {
                    if (k != j + 1 && nums[k - 1] == nums[k]) {
                        k++;
                        continue;
                    }
                    c = nums[k];
                    if (l != len - 1 && nums[l + 1] == nums[l]) {
                        l--;
                        continue;
                    }
                    d = nums[l];
                    sum = a + b + c + d;
                    if (sum > target) {
                        l--;
                    } else if (sum < target) {
                        k++;
                    } else {
                        item = new LinkedList<Integer>();
                        item.add(a);
                        item.add(b);
                        item.add(c);
                        item.add(d);
                        result.add(item);
                        k++;
                        l--;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        P18 p18 = new P18();
        List<List<Integer>> list = p18.fourSum(new int[] {}, 0);
        for (List<Integer> l : list) {
            System.out.println(l);
        }
    }
}

package com.hades.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class P698 {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        List<Integer> numList = new ArrayList<Integer>();
        for (int num : nums) {
            sum += num;
            numList.add(num);
        }
        Collections.sort(numList);
        if (sum % k != 0) {
            return false;
        }
        int avg = sum / k;
        while (numList.size() > 0 && a(numList, avg, 0)) {
        }
        return numList.size() > 0 ? false : true;
    }

    private boolean a(List<Integer> numList, int avg, int sum) {
        if (sum == 0) {
            sum += numList.get(numList.size() - 1);
            numList.remove(numList.size() - 1);
            if (sum > avg) {
                return false;
            } else if (sum == avg) {
                return true;
            } else {
                return a(numList, avg, sum);
            }
        } else {
            int i = numList.size();
            while (--i >= 0) {
                int sum1 = sum + numList.get(i);
                if (sum1 > avg) {
                    continue;
                } else if (sum1 == avg) {
                    numList.remove(i);
                    return true;
                } else {
                    List<Integer> list = new ArrayList<Integer>(numList);
                    list.remove(i);
                    if (a(list, avg, sum1)) {
                        numList.clear();
                        numList.addAll(list);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(new P698().canPartitionKSubsets(new int[] { 4, 3, 2, 3, 5, 2, 1 }, 4));
        System.out.println(new P698().canPartitionKSubsets(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 5));
        System.out.println(new P698().canPartitionKSubsets(new int[] { 4, 4, 6, 2, 3, 8, 10, 2, 10, 7 }, 4));
        System.out.println(new P698().canPartitionKSubsets(new int[] { 883, 850, 2995, 2324, 4474, 907, 991, 294, 912,
                19, 826, 425, 3100, 438, 210 }, 4));
    }
}

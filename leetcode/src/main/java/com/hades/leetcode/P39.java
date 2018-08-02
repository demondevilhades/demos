package com.hades.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P39 {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (candidates.length > 0) {
            Arrays.sort(candidates);
            int len = -1;
            for (int i = 0; i < candidates.length; i++) {
                if (target / candidates[i] == 0) {
                    len = i;
                    break;
                }
            }
            int[] aCounts = len == -1 ? new int[candidates.length] : new int[len + 1];

            L: while (true) {
                aCounts[0]++;
                int sum = sum(aCounts, candidates);
                if (sum == target) {
                    List<Integer> list = new ArrayList<Integer>();
                    for (int i = 0; i < aCounts.length; i++) {
                        for (int j = 0; j < aCounts[i]; j++) {
                            list.add(candidates[i]);
                        }
                    }
                    result.add(list);
                }
                // System.out.println(Arrays.toString(aCounts));
                if (sum > target) {
                    if (aCounts.length == 1) {
                        break L;
                    }
                    aCounts[0] = -1;
                    aCounts[1]++;
                    int index = 1;
                    while (sum(aCounts, candidates) >= target) {
                        aCounts[index] = 0;
                        if (++index >= aCounts.length) {
                            break L;
                        }
                        aCounts[index]++;
                    }
                }
            }

        }
        return result;
    }

    private int sum(final int[] aCounts, final int[] candidates) {
        int sum = 0;
        for (int i = 0; i < aCounts.length; i++) {
            sum += (aCounts[i] * candidates[i]);
        }
        return sum;
    }
    
    public List<List<Integer>> combinationSum1(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (candidates.length > 0) {
            Arrays.sort(candidates);
            int len = -1;
            for (int i = 0; i < candidates.length; i++) {
                if (target / candidates[i] == 0) {
                    len = i;
                    break;
                }
            }
            int[] aCounts = len == -1 ? new int[candidates.length] : new int[len + 1];

            int sum = 0;
            L: while (true) {
                aCounts[0]++;
                sum += candidates[0];
                if (sum == target) {
                    List<Integer> list = new ArrayList<Integer>();
                    for (int i = 0; i < aCounts.length; i++) {
                        for (int j = 0; j < aCounts[i]; j++) {
                            list.add(candidates[i]);
                        }
                    }
                    result.add(list);
                }
                // System.out.println(Arrays.toString(aCounts));
                if (sum > target) {
                    if (aCounts.length == 1) {
                        break L;
                    }
                    sum -= (aCounts[0] + 1) * candidates[0];
                    aCounts[0] = -1;
                    aCounts[1]++;
                    sum += candidates[1];
                    int index = 1;
                    while (sum >= target) {
                        sum -= aCounts[index] * candidates[index];
                        aCounts[index] = 0;
                        if (++index >= aCounts.length) {
                            break L;
                        }
                        aCounts[index]++;
                        sum += candidates[index];
                    }
                }
            }

        }
        return result;
    }

    public static void main(String[] args) {
        P39 p39 = new P39();
        {
            List<List<Integer>> combinationSum = p39.combinationSum(new int[] { 2 }, 1);
            for (List<Integer> list : combinationSum) {
                System.out.println(list);
            }
            System.out.println("===============");
        }
        {
            List<List<Integer>> combinationSum = p39.combinationSum(new int[] { 2, 3, 6, 7 }, 7);
            for (List<Integer> list : combinationSum) {
                System.out.println(list);
            }
            System.out.println("===============");
        }
        {
            List<List<Integer>> combinationSum = p39.combinationSum(new int[] { 2, 3, 5 }, 8);
            for (List<Integer> list : combinationSum) {
                System.out.println(list);
            }
            System.out.println("===============");
        }
        {
            List<List<Integer>> combinationSum = p39.combinationSum(new int[] { 48, 22, 49, 24, 26, 47, 33, 40, 37, 39,
                    31, 46, 36, 43, 45, 34, 28, 20, 29, 25, 41, 32, 23 }, 69);
            for (List<Integer> list : combinationSum) {
                System.out.println(list);
            }
            System.out.println("===============");
        }
        {
            List<List<Integer>> combinationSum = p39.combinationSum1(new int[] { 48, 22, 49, 24, 26, 47, 33, 40, 37, 39,
                    31, 46, 36, 43, 45, 34, 28, 20, 29, 25, 41, 32, 23 }, 69);
            for (List<Integer> list : combinationSum) {
                System.out.println(list);
            }
            System.out.println("===============");
        }
    }
}

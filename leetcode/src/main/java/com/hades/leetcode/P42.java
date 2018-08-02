package com.hades.leetcode;

import java.util.ArrayList;
import java.util.List;

public class P42 {

    public int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        int leftSide = 0;
        while (leftSide + 1 < height.length && height[leftSide + 1] >= height[leftSide]) {
            leftSide++;
        }
        if (leftSide + 1 == height.length) {
            return 0;
        }
        int rightSide = height.length - 1;
        while (rightSide - 1 > leftSide && height[rightSide - 1] >= height[rightSide]) {
            rightSide--;
        }
        if (leftSide + 1 == rightSide) {
            return 0;
        }

        int result = 0;
        int min = height[leftSide] > height[rightSide] ? height[rightSide] : height[leftSide];
        List<Integer> sortIndex = sortIndex(leftSide, rightSide, min, height);
        if (sortIndex.size() > 1) {
            int left, right;
            if (sortIndex.get(0) <= sortIndex.get(1)) {
                left = sortIndex.get(0);
                right = sortIndex.get(1);
            } else {
                left = sortIndex.get(1);
                right = sortIndex.get(0);
            }
            result += sum(height, left, right);
            for (int i = 2; i < sortIndex.size(); i++) {
                int index = sortIndex.get(i);
                if (index > right) {
                    result += sum(height, right, index);
                    right = index;
                } else if (index < left) {
                    result += sum(height, index, left);
                    left = index;
                }
                if (left == leftSide && right == rightSide) {
                    break;
                }
            }
        }
        return result;
    }

    private int sum(int[] height, int left, int right) {
        int min = height[left] > height[right] ? height[right] : height[left];
        int sum = 0;
        for (int i = left + 1; i < right; i++) {
            sum += min - height[i];
        }
        return sum;
    }

    private List<Integer> sortIndex(int left, int right, int min, int[] a) {
        List<Integer> indexList = new ArrayList<Integer>();
        int leftSide = -1, rightSide = -1;
        for (int i = left; i <= right; i++) {
            if (a[i] >= min) {
                if (leftSide == rightSide) {
                    if (leftSide == -1) {
                        leftSide = i;
                        rightSide = i;
                    } else if (a[i] < leftSide) {
                        leftSide = i;
                    } else {
                        rightSide = i;
                    }
                } else if (i > leftSide && i < rightSide) {
                    continue;
                } else if (i < leftSide) {
                    leftSide = i;
                } else {
                    rightSide = i;
                }

                int listIndex = 0;
                while (listIndex < indexList.size() && a[i] < a[indexList.get(listIndex)]) {// max->min
                    listIndex++;
                }
                indexList.add(listIndex, i);
            }
        }
        return indexList;
    }

    public static void main(String[] args) {
        P42 p42 = new P42();
        System.out.println(p42.trap(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));// 6
        System.out.println(p42.trap(new int[] { 4, 3, 3, 9, 3, 0, 9, 2, 8, 3 }));// 23
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(p42.trap(DataUtils.readIntArr("P42")));// 174801674
        System.out.println(System.currentTimeMillis() - currentTimeMillis);// 55380
    }
}

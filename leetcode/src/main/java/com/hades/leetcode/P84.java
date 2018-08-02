package com.hades.leetcode;

public class P84 {
    public int largestRectangleArea(int[] heights) {
        int max = 0;
        int a;
        int min;
        int change;
        for (int i = 0; i < heights.length; i = change) {
            min = heights[i];
            max = max >= min ? max : min;
            change = i;
            for (int j = i + 1; j < heights.length; j++) {
                if (min >= heights[j]) {
                    min = heights[j];
                } else if (change == i) {
                    change = j;
                }
                a = min * (j - i + 1);
                max = max >= a ? max : a;
            }
            if (change == i) {
                break;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        P84 p84 = new P84();
        System.out.println(p84.largestRectangleArea(new int[] { 2, 1, 5, 6, 2, 3 }));
        System.out.println(p84.largestRectangleArea(new int[] { 1, 2, 2 }));
    }
}

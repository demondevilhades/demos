package com.hades.leetcode;

import java.util.ArrayList;
import java.util.List;

public class P11 {
    @Deprecated
    public int maxArea0(int[] height) {
        List<int[]> heightList = new ArrayList<int[]>();
        int l0 = 0;
        int l1 = 0;
        for (int i = 0; i < height.length; i++) {
            int j = height[i];
            if (l0 == 0) {
                l0 = j;
                heightList.add(new int[] { i, j });
            } else if (l1 < j) {
                l1 = j;
            } else {
                l0 = j;
                l1 = 0;
                heightList.add(new int[] { i, j });
            }
        }
        heightList.add(new int[] { height.length - 1, l1 });

        int sum = 0;
        for (int i = 1; i < heightList.size(); i++) {
            int[] is0 = heightList.get(i - 1);
            int[] is1 = heightList.get(i);
            sum += (is0[1] < is1[1] ? is0[1] * (is1[0] - is0[0]) : is1[1] * (is1[0] - is0[0]));
        }
        return sum;
    }

    public int maxArea1(int[] height) {
        int max = 0;
        int a = 0;
        int b = 0;
        for (int i = 0; i < height.length; i++) {
            if (a > height[i]) {
                continue;
            }
            a = height[i];
            b = 0;
            for (int j = height.length - 1; j > i; j--) {
                if (b > height[j]) {
                    continue;
                }
                b = height[j];
                max = Math.max(max, (j - i) * (a > b ? b : a));
            }
        }
        return max;
    }

    public int maxArea(int[] height) {
        int max = 0;
        int a = 0;
        int b = 0;
        int i = 0;
        int j = height.length - 1;
        while (i < j) {
            if (a > height[i]) {
                i++;
                continue;
            }
            a = height[i];
            if (b > height[j]) {
                j--;
                continue;
            }
            b = height[j];
            max = Math.max(max, Math.min(a, b) * (j - i));
            if (a < b) {
                i++;
            } else {
                j--;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        P11 p11 = new P11();
        System.out.println(p11.maxArea(new int[] { 1, 1 }));// 1
        System.out.println(p11.maxArea(new int[] { 0, 2 }));// 0
        System.out.println(p11.maxArea(new int[] { 1, 2, 4, 3 }));// 4
        int[] testA = new int[15000];
        for (int i = 0; i < 15000; i++) {
            testA[i] = i + 1;
        }
        System.out.println(p11.maxArea(testA));// 56250000
        for (int i = 0; i < 15000; i++) {
            testA[i] = 15000 - i;
        }
        System.out.println(p11.maxArea(testA));// 56250000
    }
}

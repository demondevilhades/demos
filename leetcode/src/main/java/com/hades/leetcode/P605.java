package com.hades.leetcode;

public class P605 {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        int countN = 1;
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 0) {
                countN++;
            } else {
                count += ((countN - 1) / 2);
                countN = 0;
            }
        }
        if (countN > 0) {
            count += (countN / 2);
        }
        return n <= count;
    }

    public static void main(String[] args) {
        P605 p = new P605();
        System.out.println(p.canPlaceFlowers(new int[] { 1, 0, 0, 0, 1 }, 1));
        System.out.println(p.canPlaceFlowers(new int[] { 1, 0, 0, 0, 1 }, 2));
        System.out.println(p.canPlaceFlowers(new int[] { 0, 0, 1, 0, 0, 0, 1, 0, 0, }, 3));
        System.out.println(p.canPlaceFlowers(new int[] { 0, 0, 1, 0, 0, 0, 1, 0, 0, }, 4));

    }
}

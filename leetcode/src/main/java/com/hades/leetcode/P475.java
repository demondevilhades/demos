package com.hades.leetcode;

import java.util.Arrays;

public class P475 {
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);

        int max = 0;
        int h0 = 0;
        int i = 0;
        boolean flag = true;
        for (; i < houses.length; i++) {
            if (houses[i] < heaters[h0]) {
                if (flag) {
                    max = heaters[h0] - houses[i];
                    flag = false;
                }
            } else {
                break;
            }
        }

        for (; i < houses.length; i++) {
            while (h0 < heaters.length - 1 && houses[i] >= heaters[h0 + 1]) {
                h0++;
            }
            if (h0 < heaters.length - 1) {
                max = Math.max(max, Math.min(heaters[h0 + 1] - houses[i], houses[i] - heaters[h0]));
            } else {
                break;
            }
        }

        if (h0 == heaters.length - 1) {
            max = Math.max(max, houses[houses.length - 1] - heaters[h0]);
        }
        return max;
    }

    public static void main(String[] args) {
        P475 p = new P475();
        System.out.println(p.findRadius(new int[] { 1, 2, 3 }, new int[] { 2 }));// 1
        System.out.println(p.findRadius(new int[] { 1 }, new int[] { 1, 2, 3, 4 }));// 0
        System.out.println(p.findRadius(new int[] { 1, 2, 3, 4 }, new int[] { 1, 4 }));// 1
        System.out.println(p.findRadius(new int[] { 1, 5 }, new int[] { 2 }));// 3
        System.out.println(p.findRadius(new int[] { 282475249, 622650073, 984943658, 144108930, 470211272, 101027544,
                457850878, 458777923 }, new int[] { 823564440, 115438165, 784484492, 74243042, 114807987, 137522503,
                441282327, 16531729, 823378840, 143542612 }));// 161834419
    }
}

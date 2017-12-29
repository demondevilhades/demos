package com.hades.leetcode;

import java.util.Arrays;

public class P338 {

    public int[] countBits0(int num) {
        int[] arr = new int[num + 1];
        int[] arr0 = { 1 };
        arr[0] = 0;
        int j = 0;
        for (int i = 1; i < arr.length; i++) {
            if (j >= arr0.length) {
                int[] arr1 = new int[arr0.length * 2];
                for (int k = 0; k < arr0.length; k++) {
                    arr1[k] = arr0[k];
                }
                for (int k = 0; k < arr0.length; k++) {
                    arr1[k + arr0.length] = arr0[k] + 1;
                }
                arr0 = arr1;
                j = 0;
            }
            arr[i] = arr0[j];
            j++;
        }
        return arr;
    }

    public int[] countBits1(int num) {
        int[] arr = new int[num];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.bitCount(i);
        }
        return arr;
    }

    public static void main(String[] args) {
        P338 p = new P338();
        System.out.println(Arrays.toString(p.countBits0(0)));

//        long currentTimeMillis;
//        long m0;
//        long m1;
//
//        currentTimeMillis = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            p.countBits0(65535);
//        }
//        m0 = System.currentTimeMillis() - currentTimeMillis;
//        System.out.println(m0);
//
//        currentTimeMillis = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            p.countBits1(65535);
//        }
//        m1 = System.currentTimeMillis() - currentTimeMillis;
//        System.out.println(m1);
    }
}

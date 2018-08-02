package com.hades.leetcode;

import java.util.List;

public class PrintUtils {

    public static void print(int[][] m) {
        for (int[] is : m) {
            for (int i : is) {
                System.out.print(i + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static <T> void print(List<List<T>> listList) {
        for (List<T> list : listList) {
            for (T t : list) {
                System.out.print(t);
                System.out.print(", ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

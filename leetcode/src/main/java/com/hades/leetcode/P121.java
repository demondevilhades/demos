package com.hades.leetcode;

public class P121 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int max = 0;
        int min = prices[0];
        int c;
        for (int i = 1; i < prices.length; i++) {
            if (min < prices[i]) {
                c = prices[i] - min;
                max = max >= c ? max : c;
            } else {
                min = prices[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        P121 p121 = new P121();
        System.out.println(p121.maxProfit(new int[] { 7, 1, 5, 3, 6, 4 }));
        System.out.println(p121.maxProfit(new int[] { 7, 6, 4, 3, 1 }));
    }
}

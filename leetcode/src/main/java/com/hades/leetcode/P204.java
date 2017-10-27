package com.hades.leetcode;

public class P204 {
    public int countPrimes(int n) {
        if (n <= 2) {
            return 0;
        }
        boolean[] bs = new boolean[n];
        int count = 0;

        for (int i = 2, s = (int) Math.sqrt(n); i < n; i++) {
            if (bs[i] == false) {
                count++;
                if (i <= s) {
                    for (int j = 2, a = i * j; a < n;) {
                        bs[a] = true;
                        a = i * ++j;
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(new P204().countPrimes(1500000));
        System.out.println(System.currentTimeMillis() - currentTimeMillis);
    }
}

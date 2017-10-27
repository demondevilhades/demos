package com.hades.leetcode;

public class P204bak {
    public int countPrimes(int n) {
        if (n == 1) {
            return 0;
        }
        int num = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) {
                num++;
            }
        }
        return num;
    }

    private boolean isPrime(int a) {
        for (int i = 2, m = (int) Math.sqrt(a); i <= m; i++) {
            if (a % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(new P204bak().countPrimes(1500000));
        System.out.println(System.currentTimeMillis() - currentTimeMillis);
    }
}

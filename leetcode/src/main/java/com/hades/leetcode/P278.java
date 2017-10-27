package com.hades.leetcode;

public class P278 extends VersionControl {
    public int firstBadVersion(int n) {
        if(n == 1){
            return 1;
        }
        int good = 0;
        int a = n / 2;
        while (good < a && a <= n) {
            if (isBadVersion(a)) {
                if (a - 1 == good) {
                    break;
                }
                a = good + (a - good) / 2;
            } else {
                good = a;
                a = good + (((n - good) / 2) == 0 ? 1 : ((n - good) / 2));
            }
        }
        return a;
    }

    public static void main(String[] args) {
        System.out.println(new P278().firstBadVersion(2));
    }
}

class VersionControl {
    boolean isBadVersion(int version) {
        return version >= 2;
    }
}
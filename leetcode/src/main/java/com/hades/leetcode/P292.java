package com.hades.leetcode;

public class P292 {
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

    public static void main(String[] args) {
        P292 p292 = new P292();
        System.out.println(p292.canWinNim(1));
        System.out.println(p292.canWinNim(2));
        System.out.println(p292.canWinNim(3));
        System.out.println(p292.canWinNim(4));
        System.out.println(p292.canWinNim(5));
        System.out.println(p292.canWinNim(6));
        System.out.println(p292.canWinNim(7));
        System.out.println(p292.canWinNim(8));
    }
}

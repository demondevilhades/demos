package com.hades.leetcode;

public class P319 {
    public int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }

    public static void main(String[] args) {
        P319 p319 = new P319();
        System.out.println(p319.bulbSwitch(1));
        System.out.println(p319.bulbSwitch(2));
        System.out.println(p319.bulbSwitch(3));
        System.out.println(p319.bulbSwitch(4));
        System.out.println(p319.bulbSwitch(5));
        System.out.println(p319.bulbSwitch(6));
    }
}

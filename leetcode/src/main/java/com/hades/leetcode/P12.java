package com.hades.leetcode;

public class P12 {

    private final String digit[] = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };
    private final String ten[] = { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
    private final String hundred[] = { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" };
    private final String thousand[] = { "", "M", "MM", "MMM" };

    public String intToRoman(int num) {
        return thousand[num / 1000] + hundred[num % 1000 / 100] + ten[num % 100 / 10] + digit[num % 10];
    }

    public static void main(String[] args) {

    }
}

package com.hades.leetcode;

/**
 * TODO
 * 
 * @author HaDeS
 */
public class P537 {
    public String complexNumberMultiply(String a, String b) {
        String[] a_ab = a.replace("i", "").split("\\+");
        String[] b_ab = b.replace("i", "").split("\\+");

        int aa = Integer.parseInt(a_ab[0]);
        int ab = Integer.parseInt(a_ab[1]);
        int ba = Integer.parseInt(b_ab[0]);
        int bb = Integer.parseInt(b_ab[1]);

        return String.valueOf(aa * ba - ab * bb) + "+" + String.valueOf(ab * ba + aa * bb) + "i";
    }

    public static void main(String[] args) {
        System.out.println(new P537().complexNumberMultiply("1+1i", "1+1i"));
    }
}

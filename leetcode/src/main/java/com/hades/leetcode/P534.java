package com.hades.leetcode;

/**
 * Hello world!
 *
 */
public class P534 {
    private static final String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z" };

    // AtomicLong
    private static long al = 0;

    public static void main(String[] args) {
        int[] url6;
        int index;
        // args = new String[] { "aaa", "bbb" };
        for (String str : args) {
            url6 = new int[] { 0, 0, 0, 0, 0, 0 };
            index = 0;
            long l = al++;
            while (l > 0) {
                url6[index++] = (int) l % 62;
                l /= 62;
            }
            StringBuilder sb = new StringBuilder();
            for (int i : url6) {
                sb.append(chars[i]);
            }
            System.out.println(sb.toString());
        }
    }
}

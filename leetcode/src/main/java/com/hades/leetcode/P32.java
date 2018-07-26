package com.hades.leetcode;

public class P32 {
    public int longestValidParentheses(String s) {
        int max = 0;
        int[] sizes = new int[s.length()];
        char[] chs = s.toCharArray();
        for (int i = 1; i < chs.length; i++) {
            if (chs[i] == ')' && i - sizes[i - 1] - 1 >= 0 && chs[i - sizes[i - 1] - 1] == '(') {
                sizes[i] = 2 + sizes[i - 1] + ((i - sizes[i - 1] - 2 >= 0) ? sizes[i - sizes[i - 1] - 2] : 0);
                max = sizes[i] > max ? sizes[i] : max;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        P32 p32 = new P32();
        System.out.println(p32.longestValidParentheses("(()"));// 2
        System.out.println(p32.longestValidParentheses("()(()"));// 2
        System.out.println(p32.longestValidParentheses("()(())"));// 6
        System.out.println(p32.longestValidParentheses("()()()"));// 6
        System.out.println(p32.longestValidParentheses("(()())"));// 6
        System.out.println(p32.longestValidParentheses(")(()())("));// 6
        System.out.println(p32.longestValidParentheses("(()(((()"));// 2
        System.out.println(p32.longestValidParentheses("(()(((()()"));// 4
        System.out.println(p32.longestValidParentheses("(()((((())()"));// 6
        System.out.println(p32.longestValidParentheses(")((()())("));// 6
        System.out.println(p32.longestValidParentheses(")(((((()())()()))()(()))("));// 22
    }
}

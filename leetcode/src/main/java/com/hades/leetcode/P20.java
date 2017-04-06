package com.hades.leetcode;

import java.util.Stack;

public class P20 {
    public boolean isValid(String s) {
        Stack<int[]> stack0 = new Stack<int[]>();
        Stack<int[]> stack1 = new Stack<int[]>();
        Stack<int[]> stack2 = new Stack<int[]>();
        int a0 = 0, a1 = 0, a2 = 0;
        int len = s.length();
        int[] pop;
        try {
            for (int l = 0; l < len; l++) {
                char ch = s.charAt(l);
                switch (ch) {
                case '(':
                    a0++;
                    stack0.push(new int[] { a1, a2 });
                    break;
                case '[':
                    a1++;
                    stack1.push(new int[] { a0, a2 });
                    break;
                case '{':
                    a2++;
                    stack2.push(new int[] { a0, a1 });
                    break;
                case ')':
                    a0--;
                    pop = stack0.pop();
                    if (pop[0] != a1 || pop[1] != a2) {
                        return false;
                    }
                    break;
                case ']':
                    a1--;
                    pop = stack1.pop();
                    if (pop[0] != a0 || pop[1] != a2) {
                        return false;
                    }
                    break;
                case '}':
                    a2--;
                    pop = stack2.pop();
                    if (pop[0] != a0 || pop[1] != a1) {
                        return false;
                    }
                    break;
                default:
                    return false;
                }
            }
        } catch (java.util.EmptyStackException e) {
            return false;
        }
        if (!stack0.isEmpty() || !stack1.isEmpty() || !stack2.isEmpty()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        P20 p20 = new P20();
        System.out.println(p20.isValid("()()()[]{}"));
        System.out.println(p20.isValid("()([)()]{}"));
    }
}

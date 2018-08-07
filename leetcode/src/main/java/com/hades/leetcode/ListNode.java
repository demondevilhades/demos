package com.hades.leetcode;

public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    static void print(ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            System.out.print(", ");
            head = head.next;
        }
        System.out.println();
    }

    static ListNode gen(int[] is) {
        ListNode head = new ListNode(is[0]);
        ListNode last = head;
        for (int i = 1; i < is.length; i++) {
            last.next = new ListNode(is[i]);
            last = last.next;
        }
        return head;
    }
}

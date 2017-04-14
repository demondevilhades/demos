package com.hades.leetcode;

public class P19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return head;
        }
        int ri = n;
        ListNode listNode = head;
        ListNode removelistNode = null;
        while (listNode != null) {
            if (ri-- == 0) {
                removelistNode = head;
            } else if (removelistNode != null) {
                removelistNode = removelistNode.next;
            }
            listNode = listNode.next;
        }
        if (removelistNode == null) {
            return ri == 0 ? head.next : head;
        } else {
            removelistNode.next = removelistNode.next.next;
            return head;
        }
    }

    public static void main(String[] args) {

    }
}

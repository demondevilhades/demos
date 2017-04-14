package com.hades.leetcode;

public class P21 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode node = null;
        int val;
        while (l1 != null && l2 != null) {
            val = l1.val < l2.val ? l1.val : l2.val;
            if (l1.val < l2.val) {
                val = l1.val;
                l1 = l1.next;
            } else {
                val = l2.val;
                l2 = l2.next;
            }
            if (node == null) {
                node = new ListNode(val);
                head = node;
            } else {
                node.next = new ListNode(val);
                node = node.next;
            }
        }
        while (l1 != null) {
            if (node == null) {
                node = new ListNode(l1.val);
                head = node;
            } else {
                node.next = new ListNode(l1.val);
                node = node.next;
            }
            l1 = l1.next;
        }
        while (l2 != null) {
            if (node == null) {
                node = new ListNode(l2.val);
                head = node;
            } else {
                node.next = new ListNode(l2.val);
                node = node.next;
            }
            l2 = l2.next;
        }
        return head;
    }

    public static void main(String[] args) {

    }
}

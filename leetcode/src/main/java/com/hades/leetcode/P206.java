package com.hades.leetcode;

public class P206 {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        try {
            return reverse(head);
        } finally {
            head.next = null;
        }
    }

    private ListNode reverse(ListNode node) {
        if (node.next != null) {
            try {
                return reverse(node.next);
            } finally {
                node.next.next = node;
            }
        } else {
            return node;
        }
    }

    public ListNode reverseList1(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        return newHead;
    }

    public static void main(String[] args) {
        P206 p206 = new P206();
        ListNode.print(p206.reverseList(ListNode.gen(new int[] { 1, 2, 3, 4, 5 })));
    }
}

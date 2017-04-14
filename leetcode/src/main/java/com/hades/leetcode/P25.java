package com.hades.leetcode;

public class P25 {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode l = null;
        if (head != null) {
            ListNode last = null;
            ListNode th = null;
            ListNode l0 = head;
            ListNode l1 = null;
            ListNode l2 = null;
            int i = k - 1;

            while (l0 != null) {
                th = l0;
                while (i-- > 0 && l0 != null) {
                    if (l2 != null) {
                        l1 = l2;
                        l2 = l0;
                        l0 = l0.next;
                        l2.next = l1;
                    } else if (l0.next != null) {
                        l1 = l0;
                        l2 = l1.next;
                        l0 = l2.next;
                        l2.next = l1;
                    }
                }
                if (l == null) {
                    l = l2 == null ? l0 : l2;
                }
                if (last != null) {
                    last.next = l2;
                }
                if (l0 != null && l0.next == null) {
                    th.next = l0;
                    break;
                } else {
                    th.next = null;
                }
                l1 = null;
                l2 = null;
                last = th;
                i = k - 1;
            }
        }
        return l;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
//        listNode.next = new ListNode(2);
//        listNode.next.next = new ListNode(3);
//        listNode.next.next.next = new ListNode(4);
//        listNode.next.next.next.next = new ListNode(5);
//        listNode.next.next.next.next.next = new ListNode(6);
//        listNode.next.next.next.next.next.next = new ListNode(7);
        P25 p25 = new P25();
        ListNode node = p25.reverseKGroup(listNode, 1);
        while (node != null) {
            System.out.print(node.val + ",");
            node = node.next;
        }
    }
}

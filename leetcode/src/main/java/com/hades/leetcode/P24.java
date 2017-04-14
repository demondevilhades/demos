package com.hades.leetcode;

public class P24 {
    public ListNode swapPairs(ListNode head) {
        ListNode l = null;
        ListNode l0 = null;
        ListNode l1 = null;
        ListNode l2 = null;

        if (head != null) {
            if (head.next == null) {
                l = head;
            } else {
                l = head.next;
                l1 = head;
                while (l1 != null) {
                    l2 = l1.next;
                    if (l2 != null) {
                        l1.next = l2.next;
                        l2.next = l1;
                        if (l0 != null) {
                            l0.next = l2;
                        }
                    } else {
                        if (l0 != null) {
                            l0.next = l1;
                        }
                    }
                    l0 = l1;
                    l1 = l1.next;
                }
            }
        }
        return l;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        listNode.next.next.next.next.next = new ListNode(6);
        listNode.next.next.next.next.next.next = new ListNode(7);
        P24 p24 = new P24();
        ListNode node = p24.swapPairs(listNode);
        while (node != null) {
            System.out.print(node.val + ",");
            node = node.next;
        }
    }
}

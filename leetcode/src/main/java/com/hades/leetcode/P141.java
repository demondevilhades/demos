package com.hades.leetcode;

public class P141 {
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode node0 = head, node1 = head;
        while (node1.next != null && node1.next.next != null) {
            node0 = node0.next;
            node1 = node1.next.next;
            if (node0 == node1) {
                return true;
            }
        }
        return false;
    }
}

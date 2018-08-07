package com.hades.leetcode;

public class P148 {
    public ListNode sortList(ListNode head) {
        if (head == null) {
            return null;
        }
        return ms(head);
    }

    private ListNode ms(ListNode head) {
        if (head.next == null) {
            return head;
        }
        ListNode node1 = head, node2 = head;
        while (node2 != null && node2.next != null) {
            node1 = node1.next;
            node2 = node2.next.next;
        }
        node2 = head;
        while (true) {
            if (node2.next == node1) {
                node2.next = null;
                break;
            }
            node2 = node2.next;
        }
        return s(ms(head), ms(node1));
    }

    private ListNode s(ListNode node0, ListNode node1) {
        ListNode head;
        if (node0.val > node1.val) {
            head = node1;
            node1 = node1.next;
        } else {
            head = node0;
            node0 = node0.next;
        }
        ListNode last = head;
        while (node0 != null || node1 != null) {
            if (node0 == null) {
                last.next = node1;
                node1 = node1.next;
            } else if (node1 == null) {
                last.next = node0;
                node0 = node0.next;
            } else if (node0.val > node1.val) {
                last.next = node1;
                node1 = node1.next;
            } else {
                last.next = node0;
                node0 = node0.next;
            }
            last = last.next;
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode ln;
        P148 p148 = new P148();
        ln = ListNode.gen(new int[] { 4, 2, 1, 3 });
        ListNode.print(p148.sortList(ln));
        ln = ListNode.gen(new int[] { 4, 19, 14, 5, -3, 1, 8, 5, 11, 15 });
        ListNode.print(p148.sortList(ln));
    }
}

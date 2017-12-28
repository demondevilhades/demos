package com.hades.leetcode;

/**
 * TODO
 * 
 * @author HaDeS
 */
public class P160 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode a = headA;
        ListNode b;
        L: while (a != null) {
            b = headB;
            while (b != null) {
                if (a == b) {
                    break L;
                }
                b = b.next;
            }
        }
        return a;
    }
}

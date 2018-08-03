package com.hades.leetcode;

public class P142 {
    /**
     * def : A, B, N<br>
     * A + B + kN = 2A + 2B<br>
     * A = kN - B<br>
     * result : ( A + B + kN ) + ( kN - B ) = A + 2kN
     * 
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode node0 = head, node1 = head, result = null;
        while (node1.next != null && node1.next.next != null) {
            node0 = node0.next;
            node1 = node1.next.next;
            if (node0 == node1) {
                result = head;
                while (result != node0) {
                    result = result.next;
                    node0 = node0.next;
                }
                return result;
            }
        }
        return result;
    }
}

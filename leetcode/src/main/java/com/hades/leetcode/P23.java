package com.hades.leetcode;

import java.util.LinkedList;
import java.util.List;

public class P23 {
    /**
     * TLE
     * 
     * @param lists
     * @return
     */
    public ListNode mergeKLists0(ListNode[] lists) {
        ListNode head = null;
        ListNode node = null;
        int val = 0;

        int minIndex;
        boolean firstFlag;

        while (true) {
            minIndex = -1;
            firstFlag = true;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null) {
                    if (firstFlag) {
                        val = lists[i].val;
                        minIndex = i;
                        firstFlag = false;
                    } else if (val > lists[i].val) {
                        val = lists[i].val;
                        minIndex = i;
                    }
                }
            }
            if (minIndex == -1) {
                break;
            } else {
                if (node == null) {
                    node = new ListNode(val);
                    head = node;
                } else {
                    node.next = new ListNode(val);
                    node = node.next;
                }
                lists[minIndex] = lists[minIndex].next;
            }
        }
        return head;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        P21 p21 = new P21();
        ListNode l1, l2;
        List<ListNode> listNodes = new LinkedList<ListNode>();
        for (ListNode listNode : lists) {
            listNodes.add(listNode);
        }
        while (listNodes.size() > 1) {
            l1 = listNodes.remove(0);
            l2 = listNodes.remove(0);
            listNodes.add(p21.mergeTwoLists(l1, l2));
        }
        return listNodes.isEmpty() ? null : listNodes.get(0);
    }

    public static void main(String[] args) {

    }
}

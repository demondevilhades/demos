package com.hades.leetcode;

import java.util.Random;

public class P382 {
    public class Solution {

        private ListNode head;
        private Random random = new Random();

        public Solution(ListNode head) {
            this.head = head;
        }

        public int getRandom() {
            int i = -1;
            int index = 1;
            ListNode node = head;
            while (node != null) {
                if(random.nextInt(index++) == 0){
                    i = node.val;
                }
                node = node.next;
            }
            return i;
        }
    }

    public static void main(String[] args) {

    }
}

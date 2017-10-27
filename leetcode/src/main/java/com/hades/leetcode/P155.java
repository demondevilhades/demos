package com.hades.leetcode;

public class P155 {
    static class MinStack {

        private Item top = null;

        public MinStack() {

        }

        public void push(int x) {
            top = new Item(x, top);
        }

        public void pop() {
            top = top.next;
        }

        public int top() {
            return top.x;
        }

        public int getMin() {
            if (top != null) {
                int min = top.x;
                Item item = top.next;
                while (item != null) {
                    min = min > item.x ? item.x : min;
                    item = item.next;
                }
                return min;
            }
            return 0;
        }

        class Item {
            int x;
            Item next;

            public Item(int x, Item next) {
                this.x = x;
                this.next = next;
            }
        }
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());// Returns -3.
        minStack.pop();
        System.out.println(minStack.top());// Returns 0.
        System.out.println(minStack.getMin());// Returns -2.
    }
}

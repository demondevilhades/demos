package com.hades.leetcode;

public class P687 {
    private int maxLen = 0;

    public int longestUnivaluePath(TreeNode root) {
        maxLen = 0;
        if (root != null) {
            s(root);
        }
        return maxLen;
    }

    private int s(TreeNode node) {
        int leftLen = 0;
        int rightLen = 0;
        if (node.left != null) {
            if (node.val == node.left.val) {
                leftLen = s(node.left) + 1;
            } else {
                s(node.left);
            }
        }
        if (node.right != null) {
            if (node.val == node.right.val) {
                rightLen = s(node.right) + 1;
            } else {
                s(node.right);
            }
        }
        maxLen = (leftLen + rightLen) > maxLen ? (leftLen + rightLen) : maxLen;
        return leftLen > rightLen ? leftLen : rightLen;
    }
}

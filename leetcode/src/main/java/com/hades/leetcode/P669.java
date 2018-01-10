package com.hades.leetcode;

public class P669 {
    public TreeNode trimBST(TreeNode root, int L, int R) {
        if (root == null) {
            return null;
        }

        while (root != null && (root.val < L || root.val > R)) {
            root = root.val > R ? root.left : root.right;
        }

        TreeNode node = root;
        while (node != null) {
            while (node.left != null && node.left.val < L) {
                node.left = node.left.right;
            }
            node = node.left;
        }
        node = root;
        while (node != null) {
            while (node.right != null && node.right.val > R) {
                node.right = node.right.left;
            }
            node = node.right;
        }
        return root;
    }
}

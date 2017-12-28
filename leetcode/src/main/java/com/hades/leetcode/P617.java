package com.hades.leetcode;

public class P617 {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 != null) {
            t1.val += t2.val;
            merge(t1, t2);
        }
        return t1;
    }

    private void merge(TreeNode t1, TreeNode t2) {
        if (t1.left != null && t2.left != null) {
            t1.left.val += t2.left.val;
            merge(t1.left, t2.left);
        } else if (t2.left != null) {
            t1.left = t2.left;
        }
        if (t1.right != null && t2.right != null) {
            t1.right.val += t2.right.val;
            merge(t1.right, t2.right);
        } else if (t2.right != null) {
            t1.right = t2.right;
        }
    }

}

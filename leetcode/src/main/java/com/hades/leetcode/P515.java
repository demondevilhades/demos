package com.hades.leetcode;

import java.util.LinkedList;
import java.util.List;

public class P515 {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        if (root != null) {
            List<TreeNode> nodeList = new LinkedList<TreeNode>();
            nodeList.add(root);
            while (!nodeList.isEmpty()) {
                List<TreeNode> nextNodeList = new LinkedList<TreeNode>();
                int max = Integer.MIN_VALUE;
                for (TreeNode node : nodeList) {
                    max = max < node.val ? node.val : max;
                    if (node.left != null) {
                        nextNodeList.add(node.left);
                    }
                    if (node.right != null) {
                        nextNodeList.add(node.right);
                    }
                }
                list.add(max);
                nodeList = nextNodeList;
            }
        }
        return list;
    }
}

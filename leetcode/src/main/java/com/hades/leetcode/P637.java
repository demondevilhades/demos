package com.hades.leetcode;

import java.util.LinkedList;
import java.util.List;

public class P637 {

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> list = new LinkedList<Double>();
        if (root != null) {
            List<TreeNode> nodeList = new LinkedList<TreeNode>();
            nodeList.add(root);
            while (!nodeList.isEmpty()) {
                List<TreeNode> nextNodeList = new LinkedList<TreeNode>();
                long sum = 0;
                for (TreeNode node : nodeList) {
                    sum += node.val;
                    if (node.left != null) {
                        nextNodeList.add(node.left);
                    }
                    if (node.right != null) {
                        nextNodeList.add(node.right);
                    }
                }
                list.add(Double.valueOf(1.0 * sum / nodeList.size()));
                nodeList = nextNodeList;
            }
        }
        return list;
    }
}

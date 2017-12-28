package com.hades.leetcode;

import java.util.Arrays;

public class P654 {

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        int rootIndex = getMaxIndex(nums);
        TreeNode rootNode = new TreeNode(nums[rootIndex]);
        m(nums, rootIndex, rootNode);
        return rootNode;
    }

    private void m(int[] nums, int rootIndex, TreeNode rootNode) {
        if (rootIndex > 0) {
            int[] arr = Arrays.copyOfRange(nums, 0, rootIndex);
            int index = getMaxIndex(arr);
            TreeNode node = new TreeNode(arr[index]);
            rootNode.left = node;
            m(arr, index, node);
        }
        if (rootIndex < nums.length - 1) {
            int[] arr = Arrays.copyOfRange(nums, rootIndex + 1, nums.length);
            int index = getMaxIndex(arr);
            TreeNode node = new TreeNode(arr[index]);
            rootNode.right = node;
            m(arr, index, node);
        }
    }

    private int getMaxIndex(int[] nums) {
        int index = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[index] < nums[i]) {
                index = i;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        new P654().constructMaximumBinaryTree(new int[] { 3, 2, 1, 6, 0, 5 });
    }
}

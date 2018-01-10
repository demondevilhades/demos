package com.hades.leetcode;

public class P496 {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ret = new int[nums1.length];
        L: for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                if (nums1[i] == nums2[j]) {
                    ret[i] = -1;
                    for (int k = j + 1; k < nums2.length; k++) {
                        if (nums2[k] > nums2[j]) {
                            ret[i] = nums2[k];
                            continue L;
                        }
                    }
                }
            }
        }
        return ret;
    }

}

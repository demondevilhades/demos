package com.hades.leetcode;

public class P35 {
    public int searchInsert(int[] nums, int target) {
        if (nums.length == 0) {
            return 0;
        }
        int left = 0, right = nums.length - 1;
        if (target > nums[right]) {
            return nums.length;
        }

        while (left < right - 1 && target < nums[right] && target > nums[left]) {
            int mid = (left + right + 1) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (target > nums[mid]) {
                left = mid;
            } else {
                right = mid;
            }
        }
        if (target > nums[right]) {
            return right + 1;
        } else if (target == nums[right]) {
            return right;
        } else if (target > nums[left]) {
            return left + 1;
        }
        return left;
    }

    public static void main(String[] args) {
        P35 p35 = new P35();
         System.out.println(p35.searchInsert(new int[] { 1, 3, 5, 6 }, 2));//1
         System.out.println(p35.searchInsert(new int[] { 1, 3, 5, 6 }, 3));//1
         System.out.println(p35.searchInsert(new int[] { 1, 3, 5, 6 }, 5));//2
         System.out.println(p35.searchInsert(new int[] { 1, 3, 5, 6 }, 7));//4
         System.out.println(p35.searchInsert(new int[] { 1, 3, 5, 6 }, 0));//0
        System.out.println(p35.searchInsert(new int[] { 1, 3, 5 }, 5));// 2
    }
}

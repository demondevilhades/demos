package com.hades.leetcode;

public class P27 {
    public int removeElement(int[] nums, int val) {
        int len = 0;
        if (nums.length > 0) {
            int i = 0;
            while (i < nums.length && nums[i] == val) {
                i++;
            }
            if(i < nums.length){
                int[] _nums = new int[nums.length];
                _nums[0] = nums[i];
                int j = 0;
                len++;
                for (i++; i < nums.length; i++) {
                    if (nums[i] == val) {
                        continue;
                    }
                    _nums[++j] = nums[i];
                    len++;
                }
                for (i = 0; i < _nums.length; i++) {
                    nums[i] = _nums[i];
                }
            }
        }
        return len;
    }

    public static void main(String[] args) {
        P27 p27 = new P27();
        System.out.println(p27.removeElement(new int[] { 3, 2, 2, 3 }, 3));
        System.out.println(p27.removeElement(new int[] { 1 }, 1));
    }
}

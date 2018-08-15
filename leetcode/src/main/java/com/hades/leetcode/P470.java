package com.hades.leetcode;

public abstract class P470 {
    public int rand10() {
        return (rand7() + rand7() + rand7() + rand7() + rand7() + rand7() + rand7() + rand7() + rand7() + rand7()) % 10 + 1;
    }

    public abstract int rand7();
}

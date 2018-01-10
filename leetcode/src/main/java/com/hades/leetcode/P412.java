package com.hades.leetcode;

import java.util.ArrayList;
import java.util.List;

public class P412 {
    public List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<String>(n);
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0) {
                if (i % 5 == 0) {
                    list.add("FizzBuzz");
                } else {
                    list.add("Fizz");
                }
            } else if (i % 5 == 0) {
                list.add("Buzz");
            } else {
                list.add(Integer.toString(i));
            }
        }
        return list;
    }
}

package com.hades.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 * 
 * @author HaDeS
 *
 */
public class P732 {
    class MyCalendarThree {

        private final Set<Range> set = new HashSet<Range>();

        public MyCalendarThree() {
        }

        public int book(int start, int end) {
            int count = 1;
            for (Range range : set) {
                if (!(start > range.end || end < range.start)) {
                    count++;
                }
                if (count == 3) {
                    break;
                }
            }
            set.add(new Range(start, end));
            return count;
        }

        class Range {
            int start;
            int end;

            Range(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
    }

    public static void main(String[] args) {
        P732 p = new P732();
        MyCalendarThree mct = p.new MyCalendarThree();// 1,1,2,3,3,3
        System.out.println(mct.book(10, 20));
        System.out.println(mct.book(50, 60));
        System.out.println(mct.book(10, 40));
        System.out.println(mct.book(5, 15));
        System.out.println(mct.book(5, 10));
        System.out.println(mct.book(25, 55));

        System.out.println();
        mct = p.new MyCalendarThree();// [null,1,2,3,3,3,3,3,3,3,3]
        System.out.println(mct.book(26, 35));
        System.out.println(mct.book(26, 32));
        System.out.println(mct.book(25, 32));
        System.out.println(mct.book(18, 26));
        System.out.println(mct.book(40, 45));
        System.out.println(mct.book(19, 26));
        System.out.println(mct.book(48, 50));
        System.out.println(mct.book(1, 6));
        System.out.println(mct.book(46, 50));
        System.out.println(mct.book(11, 18));
    }
}

package com.hades.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class P56 {
    public List<Interval> merge(List<Interval> intervals) {
        intervals.sort(new Comparator<Interval>() {

            @Override
            public int compare(Interval o1, Interval o2) {
                return o2.end < o1.start ? -1 : 1;
            }
        });
        Interval lastInterval = null;
        Interval interval = null;
        for (Iterator<Interval> iterator = intervals.iterator(); iterator.hasNext();) {
            interval = iterator.next();
            if (lastInterval == null || lastInterval.end < interval.start) {
                lastInterval = interval;
            } else {
                lastInterval.end = lastInterval.end >= interval.end ? lastInterval.end : interval.end;
                lastInterval.start = lastInterval.start <= interval.start ? lastInterval.start : interval.start;
                iterator.remove();
            }
        }
        return intervals;
    }

    public List<Interval> merge1(List<Interval> intervals) {
        List<Interval> result = new LinkedList<Interval>();// better than ArrayList

        Interval interval = null;
        L: for (Iterator<Interval> iterator = intervals.iterator(); iterator.hasNext();) {
            interval = iterator.next();
            if (result.size() == 0) {
                result.add(interval);
            } else {
                for (ListIterator<Interval> rIterator = result.listIterator(); rIterator.hasNext();) {
                    Interval rInterval = rIterator.next();
                    if (interval.start > rInterval.end) {
                        continue;
                    } else if (interval.start >= rInterval.start && interval.end <= rInterval.end) {
                        continue L;
                    } else if (interval.start <= rInterval.start && interval.end >= rInterval.end) {
                        rIterator.remove();
                    } else if (interval.end < rInterval.start) {
                        rIterator.previous();
                        rIterator.add(interval);
                        continue L;
                    } else {
                        rInterval.start = rInterval.start <= interval.start ? rInterval.start : interval.start;
                        // rInterval.end = rInterval.end >= interval.end ?
                        // rInterval.end : interval.end;
                        if (rInterval.end < interval.end) {
                            rInterval.end = interval.end;
                            while (rIterator.hasNext()) {
                                Interval interval2 = rIterator.next();
                                if (interval2.end <= rInterval.end) {
                                    rIterator.remove();
                                } else if (interval2.start <= rInterval.end) {
                                    rInterval.end = interval2.end;
                                    rIterator.remove();
                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                        continue L;
                    }
                }
                result.add(interval);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        P56 p56 = new P56();
        System.out.println(p56.merge1(Arrays.asList(new Interval[] { new Interval(1, 4), new Interval(1, 5) })));
        System.out.println(p56.merge1(Arrays.asList(new Interval[] { new Interval(1, 3), new Interval(2, 6),
                new Interval(8, 10), new Interval(15, 18) })));
        System.out.println(p56.merge1(Arrays.asList(new Interval[] { new Interval(2, 3), new Interval(4, 6),
                new Interval(5, 7), new Interval(3, 4) })));
        System.out.println(p56.merge1(Arrays.asList(new Interval[] { new Interval(5, 5), new Interval(1, 2),
                new Interval(2, 4), new Interval(2, 3), new Interval(4, 4), new Interval(5, 5), new Interval(2, 3),
                new Interval(5, 6), new Interval(0, 0), new Interval(5, 6) })));
    }
}

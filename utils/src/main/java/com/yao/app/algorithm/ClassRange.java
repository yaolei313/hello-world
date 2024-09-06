package com.yao.app.algorithm;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class ClassRange {


    TreeMap<Integer, Integer> intervals;

    public ClassRange() {
        intervals = new TreeMap<>();
    }

    public void addRange(int left, int right) {
        Map.Entry<Integer, Integer> e = intervals.higherEntry(left);
        if (e != intervals.firstEntry()) {
            Map.Entry<Integer, Integer> start = e == null ? intervals.lastEntry() : intervals.lowerEntry(left);
            if (start != null && right <= start.getValue()) {
                return;
            }
            if (start != null && start.getValue() >= left) {
                left = start.getKey();
                intervals.remove(start.getKey());
            }
        }

        while (e != null && e.getKey() <= right) {
            right = Math.max(e.getValue(), right);
            intervals.remove(e.getKey());
            e = intervals.higherEntry(left);
        }
        intervals.put(left, right);
    }

    public boolean queryRange(int left, int right) {
        return false;
    }

    public void removeRange(int left, int right) {

    }

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */
}

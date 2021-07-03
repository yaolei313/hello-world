package com.yao.app.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Solution5 {

    /**
     * <pre>
     * 给定1个二维字符数组m和1个单词w，搜索w是否在m中。搜索的定义是从m的任意位置开始，可以上下左右移动，依次和w每个字符匹配，如果w能匹配完，则存在，否则不存在。
     * - 例子："zoo"，"zoro"，"xtifx"都能够搜索成功，但"oto"搜索不成功。
     * 		a c d z
     * 		x t r o
     * 		f i o o
     * </pre>
     * @param args
     */
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //int a = in.nextInt();
        //System.out.println(a);
        System.out.println("Hello World!");
    }

    public static class Position {

        int i;
        int j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public boolean isNeighbor(Position p) {
            int diff = Math.abs(p.i - this.i);
            diff += Math.abs(p.j - this.j);
            if (diff == 1) {
                return true;
            } else {
                // ==2
                return false;
            }
        }
    }

    public boolean isExists(char[][] a, String target) {
        if (a == null || a.length == 0 || target == null || target.length() == 0) {
            return false;
        }

        Map<Character, List<Position>> map = new HashMap<>();
        int m = a.length;
        int n = a[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                List<Position> positions = map.get(a[m][n]);
                if (positions == null) {
                    positions = new ArrayList();
                    map.put(a[m][n], positions);
                }
                positions.add(new Position(m, n));
            }
        }

        char[] chars = target.toCharArray();
        List<Position> list = map.get(chars[0]);
        if (list == null || list.size() == 0) {
            return false;
        }
        if (chars.length == 1) {
            return true;
        }
        int i = 1;
        boolean found = false;
        for (Position root : list) {
            found = findByDeepScan(root, chars, i, map);
            if (found) {
                break;
            }
        }
        return found;
    }

    private boolean findByDeepScan(Position root, char[] chars, int i, Map<Character, List<Position>> map) {
        Stack<Position> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty() && i < chars.length) {
            Position p = stack.pop();
            List<Position> lst = map.get(chars[i++]);
            if (lst == null || lst.isEmpty()) {
                return false;
            }
            for (Position p2 : lst) {
                if (p.isNeighbor(p2)) {
                    stack.push(p2);
                }
            }
        }
        return true;
    }
}

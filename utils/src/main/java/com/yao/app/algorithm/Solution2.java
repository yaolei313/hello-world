package com.yao.app.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution2 {

    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //int a = in.nextInt();
        //System.out.println(a);
        int[] a = new int[]{1, 2, 4, 5, 3};
        int[] b = new int[]{4, 2, 5, 1, 3};
        findRightView(a, b);
    }

    public static class Node {

        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void findRightView(int[] preorder, int[] midorder) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        Map<Integer, Integer> midMap = new HashMap<>((int) (midorder.length / 0.75 + 1));
        for (int i = 0; i < midorder.length; i++) {
            midMap.put(midorder[i], i);
        }
        Node root = findRoot(0, map, preorder, 0, preorder.length - 1, midorder, 0, midorder.length - 1, midMap);
        int i = 0;
        List<Integer> lst = map.get(i);
        while (lst != null && lst.size() > 0) {
            System.out.println(lst.get(lst.size() - 1));
            i++;
            lst = map.get(i);
        }
        System.out.println();
    }

    public static Node findRoot(int level, Map<Integer, List<Integer>> map, int[] preorder, int pl, int pr, int[] midorder, int ml, int mr,
        Map<Integer, Integer> midMap) {
        if (pl == pr) {
            visit(level, map, preorder[pl]);
            return new Node(preorder[pl]);
        }
        if (pl > pr) {
            return null;
        }

        int rootVal = preorder[pl];
        visit(level, map, preorder[pl]);

        int idx = midMap.get(rootVal);
        int leftCount = idx - ml;
        Node left = findRoot(level + 1, map, preorder, pl + 1, pl + leftCount, midorder, ml, idx - 1, midMap);
        Node right = findRoot(level + 1, map, preorder, pl + leftCount + 1, pr, midorder, idx + 1, mr, midMap);

        Node node = new Node(rootVal, left, right);
        return node;
    }

    public static void visit(int level, Map<Integer, List<Integer>> map, int nodeVal) {
        List<Integer> lst = map.get(level);
        if (lst == null) {
            lst = new ArrayList<>();
            map.put(level, lst);
        }
        // 其实可以不用list，只记录最后一个就行
        lst.add(nodeVal);
    }
}

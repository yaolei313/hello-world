package com.yao.app.algorithm;

import java.util.Stack;

public class Solution3 {

    public static void main(String[] args) {
        Node node4 = new Node(4, null, null);
        Node node3 = new Node(3, null, null);

        Node node2 = new Node(2, node3, node4);
        Node node22 = new Node(2, node3, node4);
        Node root = new Node(1, node2, node22);

        boolean result = check(root);
        System.out.println(result);
    }

    public static boolean check(Node root) {
        if (root == null) {
            return true;
        }
        if (root.left == null && root.right == null) {
            return true;
        } else if (root.left != null && root.right != null) {

        } else {
            return false;
        }

        Stack<Node> stack1 = new Stack<>();
        stack1.push(root.left);
        Stack<Node> stack2 = new Stack<>();
        stack2.push(root.right);

        while (!stack1.empty()) {
            if (stack2.empty()) {
                return false;
            }
            Node item1 = stack1.pop();
            Node item2 = stack2.pop();
            if (item1.val == item2.val) {
                // 处理左侧
                if (item1.left != null && item2.left != null) {
                    stack1.push(item1.left);
                    stack2.push(item2.left);
                } else if (item1.left == null && item2.left == null) {
                    // 什么也不做
                } else {
                    return false;
                }

                // 处理右侧
                if (item1.right != null && item2.right != null) {
                    stack1.push(item1.right);
                    stack2.push(item2.right);
                } else if (item1.right == null && item2.right == null) {
                    // 什么也不做
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    public static class Node {

        int val;

        Node left;

        Node right;

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

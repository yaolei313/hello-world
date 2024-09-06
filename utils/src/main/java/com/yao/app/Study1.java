package com.yao.app;

import java.util.ArrayDeque;
import java.util.Queue;

public class Study1 {

    public static class Node {
        int val;
        Node left;
        Node right;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 分层打印，右视图
    public void printTree(Node root) {
        if (root == null) {
            return;
        }
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i=0;i<size;i++) {
                // Retrieves and removes the head of this queue
                Node item = queue.poll();
                System.out.print(item.val + "\t");
                if (item.right != null) {
                    queue.add(item.right);
                }
                if (item.left != null) {
                    queue.add(item.left);
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Node node4  = new Node(4);
        Node node5  = new Node(5);
        Node node2  = new Node(2, node4, node5);
        Node node6  = new Node(6);
        Node node3  = new Node(3, node6, null);

        Node node1  = new Node(1, node2, node3);

        Study1 instance = new Study1();
        instance.printTree(node1);
    }
}

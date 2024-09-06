package com.yao.app.algorithm;

import java.util.ArrayDeque;

public class SolutionLevelStudy {

    public static void main(String[] args) {
        Node n4 = new Node(null,null,4);
        Node n5 = new Node(null,null,5);
        Node n6 = new Node(null,null,6);
        Node n7 = new Node(null,null,7);

        Node n3 = new Node(n6,n7, 3);
        Node n2 = new Node(n4,n5, 2);

        Node n1 = new Node(n2,n3, 1);
        travel(n1);
    }

    public static void travel(Node root) {
        if (root == null) {
            return;
        }
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            if (level % 2 == 0) {
                for (int i=0;i<size;i++) {
                    Node node = queue.pollFirst();
                    System.out.println(node.value);
                    if (node.left != null) {
                        queue.addLast(node.left);
                    }
                    if (node.right != null) {
                        queue.addLast(node.right);
                    }
                }
            } else {
                for (int i=0;i<size;i++) {
                    Node node = queue.pollLast();
                    System.out.println(node.value);
                    if (node.left != null) {
                        queue.addFirst(node.right);
                    }
                    if (node.right != null) {
                        queue.addFirst(node.left);
                    }
                }
            }
        }
    }

    public static class Node {
        Node left;
        Node right;
        int value;

        public Node(Node left, Node right, int value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }
    }
}

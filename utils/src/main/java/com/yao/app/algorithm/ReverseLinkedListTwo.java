package com.yao.app.algorithm;

public class ReverseLinkedListTwo {

    public static void main(String[] args) {
        ReverseLinkedListTwo m = new ReverseLinkedListTwo();
        ListNode node5 = new ListNode(5);
        ListNode node4 = new ListNode(4);
        ListNode node3 = new ListNode(3);
        ListNode node2 = new ListNode(2);
        ListNode node1 = new ListNode(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        ListNode result = m.reverseBetween(node1, 3, 4);
        printLinkedList(result);
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null || left == right) {
            return head;
        }
        ListNode result = null;

        ListNode prefix = null;
        ListNode suffix = null;
        ListNode subleft = null;
        ListNode subright = null;

        ListNode pre = null;
        ListNode cur = null;

        int count = 1;
        if (left == 1) {
            subleft = head;
        } else {
            result = head;
            pre = null;
            cur = head;
            while (count <= left - 1) {
                pre = cur;
                cur = cur.next;
                count++;
            }
            prefix = pre;
            subleft = cur;
        }

        pre = null;
        cur = subleft;
        ListNode tmp = null;
        while (count <= right) {
            tmp = cur.next;
            cur.next = pre;

            pre = cur;
            cur = tmp;
            count++;
        }
        subright = pre;
        suffix = cur;

        if (prefix == null) {
            result = subright;
        } else {
            prefix.next = subright;
        }
        subleft.next = suffix;
        return result;
    }

    public static class ListNode {

        int val;

        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return String.valueOf(this.val);
        }
    }

    public static void printLinkedList(ListNode node) {
        while (node != null) {
            System.out.print(node.val + ",");
            node = node.next;
        }
        System.out.println();
    }
}

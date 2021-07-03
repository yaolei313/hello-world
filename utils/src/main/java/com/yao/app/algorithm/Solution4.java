package com.yao.app.algorithm;

public class Solution4 {

    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //int a = in.nextInt();
        //System.out.println(a);
        int[] a = new int[]{1, 2, 2, 2, 3, 3, 5, 5, 5, 7};
        Solution4 m = new Solution4();
        m.binaryVisitorArray(a, 3, 0, a.length - 1);
        System.out.println(m.left + "~" + m.right);
        if (m.left == null) {
            System.out.println("not found");
        } else {
            System.out.println(m.right - m.left + 1);
        }
    }

    private Integer left;

    private Integer right;

    public void binaryVisitorArray(int[] a, int target, int l, int r) {
        if (l > r) {
            return;
        }
        if (a[l] > target || a[r] < target) {
            // 为了提前中止
            return;
        }
        if (a[l] == target) {
            // null处理
            if (left == null) {
                left = l;
            } else {
                left = Math.min(left, l);
            }
        }
        if (a[r] == target) {
            // null处理
            if (right == null) {
                right = r;
            } else {
                right = Math.max(right, r);
            }
        }
        if (a[l] == a[r]) {
            // 为了提前中止
            return;
        }
        if (l == r) {
            return;
        }

        int mid = l + (r - l) / 2;
        if (a[mid] == target) {
            if (left == null) {
                left = mid;
            } else {
                left = Math.min(mid, l);
            }
            if (right == null) {
                right = mid;
            } else {
                right = Math.max(mid, r);
            }
            binaryVisitorArray(a, target, l, mid - 1);
            binaryVisitorArray(a, target, mid + 1, r);
        } else if (a[mid] < target) {
            binaryVisitorArray(a, target, mid + 1, r);
        } else {
            binaryVisitorArray(a, target, l, mid - 1);
        }
    }
}

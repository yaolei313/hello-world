package com.yao.app.algorithm;

public class Solution1 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int total = m + n;

        if ((total & 1) == 1) {
            return findKTh(nums1, 0, m, nums2, 0, n, total / 2 + 1);
        } else {
            return (findKTh(nums1, 0, m, nums2, 0, n, total / 2) + findKTh(nums1, 0, m, nums2, 0, n, total / 2 + 1)) / 2;
        }
    }

    private double findKTh(int[] a, int startA, int m, int[] b, int startB, int n, int k) {
        if (m > n) {
            return findKTh(b, startB, n, a, startA, m, k);
        }
        if (m == 0) {
            return b[startB + k - 1];
        }
        if(k==1){
            return Math.min(a[startA], b[startB]);
        }

        int pa = Math.min(k / 2, m);
        int pb = k - pa;

        if (a[startA + pa - 1] < b[startB + pb - 1]) {
            return findKTh(a, startA + pa, m - pa, b, startB, pb, k - pa);
        } else if (a[startA + pa - 1] > b[startB + pb - 1]) {
            return findKTh(a, startA, pa, b, startB + pb, n - pb, k - pb);
        } else {
            return a[startA + pa - 1];
        }
    }

    public static void main(String[] args) {
        Solution1 s = new Solution1();
        System.out.println(s.findMedianSortedArrays(new int[] {1, 2, 3, 5, 77, 88}, new int[] {22, 99, 100, 333}));
    }

}

package com.yao.app.algorithm;

public class QuickSort {

    public static void main(String[] args) {
        QuickSort t = new QuickSort();

        int[] a = new int[] {11, 2, 8, 111, 44, 66, 45, 23};

        QuickSort.printArray(a);
        t.quickSort(a, 0, a.length - 1);
        QuickSort.printArray(a);

    }

    public void quickSort(int a[], int l, int r) {
        if (l < r) {
            int mid = partition(a, l, r);
            quickSort(a, l, mid - 1);
            quickSort(a, mid + 1, r);
        }
    }

    private int partition(int[] a, int l, int r) {
        int tmp = a[l];
        while (l < r) {
            while (l < r && a[r] > tmp) {
                r--;
            }
            a[l] = a[r];

            while (l < r && a[l] < tmp) {
                l++;
            }
            a[r] = a[l];
        }
        a[l] = tmp;

        return l;
    }

    public static void printArray(int[] a) {
        for (int t : a) {
            System.out.print(t + "\t");
        }
        System.out.println();
    }

}

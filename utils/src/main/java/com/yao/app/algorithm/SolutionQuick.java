package com.yao.app.algorithm;

public class SolutionQuick {

    public void quickSort(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int pivotIndex = swap(nums, l, r);
        quickSort(nums, l ,pivotIndex-1);
        quickSort(nums, pivotIndex+1, r);
    }

    public int swap(int[] nums, int l, int r) {
        int val = nums[l];
        int i = l;
        int j = r;
        while (i<j) {
            while (i<j && nums[j] > val) {
                j--;
            }
            while (i<j && nums[i] <= val) {
                i++;
            }
            if (i<j) {
                int tmp = nums[j];
                nums[j] = nums[i];
                nums[i] = tmp;
            }
        }
        nums[l] = nums[j];
        nums[j] = val;
        return r;
    }

    public static void main(String[] args) {
        // int[] nums = new int[]{5,6, 2,3,1};
        int[] nums = new int[50000];
        for (int i=0;i<nums.length;i++) {
            nums[i] = 2;
        }
        SolutionQuick s = new SolutionQuick();
        s.quickSort(nums,0,nums.length-1);
        System.out.println(nums);
    }

}

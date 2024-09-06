package com.yao.app.algorithm;

import java.util.Stack;

public class Solution34 {

    public static void main(String[] args) {
        Solution34 s = new Solution34();

        int[] input = new int[]{5,7,7,8,8,10};
        int[] result = s.searchRange(input, 8);
        System.out.println(result);
    }

    public int[] searchRange(int[] nums, int target) {
        int left = findLeft(nums, target);
        if (left == -1) {
            return new int[]{-1,-1};
        }
        int right = findRight(nums, target);
        return new int[]{left, right};
    }

    public int findLeft(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                right = mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (left == nums.length) {
            return  -1;
        }
        if (nums[left] == target) {
            return left;
        }
        return -1;
    }

    public int findRight(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1) / 2 ;
            if (nums[mid] == target) {
                left = mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (right == -1) {
            return -1;
        }
        if (nums[right] == target) {
            return right;
        }
        return -1;
    }
}

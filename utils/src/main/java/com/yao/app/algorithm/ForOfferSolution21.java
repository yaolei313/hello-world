package com.yao.app.algorithm;

import java.util.Arrays;

public class ForOfferSolution21 {

    public static void main(String[] args) {
        ForOfferSolution21 s = new ForOfferSolution21();
        int[] input = new int[]{1, 2, 3, 4};
        int[] output = s.exchange(input);
        System.out.println(Arrays.toString(output));
    }

    public int[] exchange(int[] nums) {
        int i = 0;
        int j = nums.length - 1;
        while (i < j) {
            while (nums[i] % 2 == 1 && i < j) {
                i++;
            }
            if (i == j) {
                return nums;
            }
            while (nums[j] % 2 == 0 && j > i) {
                j--;
            }
            if (i == j) {
                return nums;
            }
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        return nums;
    }

}

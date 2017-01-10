package com.yao.app.algorithm;

import java.util.Scanner;

public class Solution {

    /**
     * 
     * Given a string, find the length of the longest substring without repeating characters. For
     * example, the longest substring without repeating letters for "abcabcbb" is "abc", which the
     * length is 3. For "bbbbb" the longest substring is "b", with the length of 1.
     * 
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int[] global = new int[s.length()];
        int[] local = new int[s.length()];
        char[] chars = s.toCharArray();


        global[0] = 1;
        local[0] = 1;

        for (int i = 1; i < s.length(); i++) {
            boolean exist = false;
            for (int j = i - 1; j >= i - local[i - 1]; j--) {
                if (chars[i] == chars[j]) {
                    local[i] = i - j;
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                local[i] = local[i - 1] + 1;
            }
            global[i] = Math.max(global[i - 1], local[i]);

        }

        return global[s.length() - 1];
    }

    public static void main(String[] args) {
        // Solution s = new Solution();
        // System.out.println(s.lengthOfLongestSubstring("dvdf"));

        System.out.println(7 / 2.0);
        System.out.println("enter any char to stop");
        try (Scanner in = new Scanner(System.in)) {
            in.next();
        }

    }
}

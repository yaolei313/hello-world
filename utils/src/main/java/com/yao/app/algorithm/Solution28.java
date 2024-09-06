package com.yao.app.algorithm;

public class Solution28 {

    public int strStr(String haystack, String needle) {
        int n = needle.length();
        int x = 0;
        int[][] dp = new int[n][26];
        dp[0][needle.charAt(0)-'a'] = 1;

        for (int i = 1; i < n; i++) {
            char ch = needle.charAt(i);
            for (int j = 0; j < 26; j++) {
                if (j == ch - 'a') {
                    dp[i][j] = i + 1;
                } else {
                    dp[i][j] = dp[x][j];
                }
            }
            x = dp[x][ch - 'a'];
        }

        int state = 0;
        for (int i = 0; i < haystack.length(); i++) {
            char ch = haystack.charAt(i);
            state = dp[state][ch - 'a'];
            if (state == n) {
                return i - n + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Solution28 s = new Solution28();
        int result = s.strStr("sadbutsad", "sad");
        System.out.println(result);
    }
}

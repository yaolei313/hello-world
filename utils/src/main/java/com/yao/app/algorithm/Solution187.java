package com.yao.app.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution187 {

    public List<String> findRepeatedDnaSequences(String s) {
        byte[] ns = new byte[s.length()];
        for (int i =0;i<s.length();i++) {
            char ch = s.charAt(i);
            if (ch == 'A') {
                ns[i] = 0;
            } else if(ch == 'C') {
                ns[i] = 1;
            } else if(ch == 'G') {
                ns[i] = 2;
            } else {
                ns[i] = 3;
            }
        }

        System.out.println(ns);

        Set<Integer> seen = new HashSet<>();
        Set<String> result = new HashSet<>();

        int highNumber = (int)Math.pow(4, 9);

        int left = 0;
        int right = 0;
        int windowHash = 0;
        while (right < s.length()) {
            byte ch = ns[right];
            right++;

            windowHash = 4 * windowHash + ch;

            if (right - left == 10) {
                if (seen.contains(windowHash)) {
                    String sub = s.substring(left, right);
                    result.add(sub);
                } else {
                    seen.add(windowHash);
                }

                windowHash = windowHash - ns[left] * highNumber;
                left++;
            }
        }
        return new ArrayList<>(result);
    }

    public static void main(String[] args) {
        Solution187 test = new Solution187();
        List<String> result = test.findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT");
        System.out.println(result);
    }
}

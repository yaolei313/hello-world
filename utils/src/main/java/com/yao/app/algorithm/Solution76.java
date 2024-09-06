package com.yao.app.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution76 {

    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        Map<Character,Integer> charCountMap = new HashMap<>((int)(n/0.75+1));
        for (int i=0;i<n;i++) {
            char ch = t.charAt(i);
            Integer count = charCountMap.get(ch);
            if (count == null) {
                count = 0;
            }
            count++;
            charCountMap.put(ch, count);
        }
        List<Integer> scanIndex = new ArrayList<>(m);
        for (int i=0;i<m;i++) {
            char ch = s.charAt(i);
            if (charCountMap.containsKey(ch)) {
                scanIndex.add(i);
            }
        }

        int resultLen = -1;
        int resultLeft = -1;
        int resultRight = -1;

        int left = 0;
        int right = 0;
        int foundCount = 0;
        while (right < scanIndex.size()) {
            while (right < scanIndex.size()) {
                if (findNewChar(charCountMap, s.charAt(scanIndex.get(right)))) {
                    foundCount++;
                }
                if (foundCount == n) {
                    break;
                }
                right++;
            }

            if (foundCount < n) {
                break;
            }

            while (left < right && canShrink(charCountMap, s, scanIndex, left)) {
                left++;
            }

            if (resultLen == -1) {
                resultLen = scanIndex.get(right) - scanIndex.get(left) + 1;
                resultLeft = scanIndex.get(left);
                resultRight = scanIndex.get(right);
            } else {
                int newLen = scanIndex.get(right) - scanIndex.get(left) + 1;
                if (newLen < resultLen) {
                    resultLen = newLen;
                    resultLeft = scanIndex.get(left);
                    resultRight = scanIndex.get(right);
                }
            }

            addFindingChar(charCountMap, s.charAt(scanIndex.get(left)));
            foundCount--;
            left++;
            right++;
        }

        if (resultLen == -1) {
            return "";
        }
        return s.substring(resultLeft, resultRight+1);
    }

    private boolean findNewChar(Map<Character,Integer> charCountMap, char ch) {
        Integer count = charCountMap.get(ch);
        if (count == null) {
            return false;
        }
        count--;
        charCountMap.put(ch, count);
        return count >=0;
    }

    private boolean canShrink(Map<Character,Integer> charCountMap, String s,
        List<Integer> scanIndex, int left) {
        char ch = s.charAt(scanIndex.get(left));
        Integer count = charCountMap.get(ch);
        if (count == null || count == 0) {
            return false;
        }
        count++;
        charCountMap.put(ch, count);
        return true;
    }

    private void addFindingChar(Map<Character,Integer> charCountMap, char ch) {
        Integer count = charCountMap.get(ch);
        if (count == null) {
            // never happen
            return;
        }
        count++;
        charCountMap.put(ch, count);
    }

    public static void main(String[] args) {
        Solution76 test = new Solution76();
        // ADOBECODEBANC
        // 0123456789012
        // ADOBECODEB
        String result = test.minWindow("ADOBECODEB", "ABC");
        System.out.println(result);
    }
}

package com.yao.app.algorithm;

import java.util.HashMap;
import java.util.Map;

public class Solution567 {

    public boolean checkInclusion(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        Map<Character, Integer> countMap = new HashMap<>((int)(m/0.75+1));
        for (int i=0;i<m;i++) {
            char ch = s1.charAt(i);
            plusCount(countMap, ch);
        }

        Map<Character, Integer> windowCountMap = new HashMap<>((int)(m/0.75+1));
        int left = 0, right = 0;
        int valid = 0;
        boolean foundFirst = false;
        while (right < n) {
            char ch = s2.charAt(right);
            // 进行窗口内数据的一系列更新
            if (countMap.containsKey(ch)) {
                if (!foundFirst) {
                    foundFirst = true;
                    left = right;
                }
                int nowCount = plusCount(windowCountMap, ch);
                int count = countMap.get(ch);
                if (nowCount == count) {
                    valid++;
                } else if(nowCount == count + 1) {
                    valid--;
                }
            } else {
                foundFirst = false;
                windowCountMap.clear();
                valid = 0;
                right++;
                continue;
            }

            if (valid == countMap.keySet().size()) {
                return true;
            }

            // 判断左侧窗口是否要收缩,窗口内出现字符个数超标
            while (windowCountMap.get(ch) > countMap.get(ch)) {
                char d = s2.charAt(left);
                left++;
                // 进行窗口内数据的一系列更新
                int nowCount = minusCount(windowCountMap, d);
                int count = countMap.get(d);
                if (nowCount == count) {
                    valid++;
                } else if(nowCount == count - 1) {
                    valid--;
                }

                if (valid == countMap.keySet().size()) {
                    return true;
                }
            }

            right++;
        }
        // 未找到符合条件的子串
        return false;
    }

    private int plusCount(Map<Character, Integer> countMap, char ch) {
        Integer count = countMap.get(ch);
        if (count == null) {
            count = 0;
        }
        count++;
        countMap.put(ch, count);
        return count;
    }

    private int minusCount(Map<Character, Integer> countMap, char ch) {
        Integer count = countMap.get(ch);
        if (count == null) {
            count = 0;
        }
        count--;
        countMap.put(ch, count);
        return count;
    }

    public static void main(String[] args) {
        Solution567 test = new Solution567();
        boolean result = test.checkInclusion("abbo", "eaoobbbo");
        System.out.println(result);
    }
}

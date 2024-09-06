package com.yao.app;

import java.util.*;

public class Test {

    public static void main(String[] args) {
        int[] input = new int[]{-4,6,-2,6,-6,2};
        int[] result = mergeArray(input);
        int[] expect = new int[]{-4,6,2};
        Map<String,String> map = new HashMap<>();
    }

    public static int[] mergeArray(int[] input) {
        Stack<Integer> s = new Stack<>();
        for (int i=0;i<input.length;i++) {
            if (input[i] < 0) {
                // 向左
                if (i-1>=0) {
                    // 左边有元素，符号相同，为负数
                    if (input[i-1] < 0) {
                        s.push(input[i]);
                        continue;
                    }
                    // 符号不同
                    if(Math.abs(input[i-1]) > Math.abs(input[i])){
                        // 左边绝对值大，不需要push i
                    } else if(Math.abs(input[i-1]) == Math.abs(input[i])) {
                        // 绝对值相同，同时消亡
                        s.pop();
                    } else {
                        // 右边绝对值大
                        s.pop();
                        s.push(input[i]);
                    }
                } else {
                    // 左边无
                    s.push(input[i]);
                }
            } else if(input[i] > 0) {
                // 向右
                if (i+1< input.length) {
                    // 右边有元素，符号相同，为正数
                    if (input[i+1]>0) {
                        s.push(input[i]);
                        continue;
                    }

                    // 符号不同
                    if(Math.abs(input[i]) > Math.abs(input[i+1])){
                        // 左边绝对值大
                        s.push(input[i]);
                    } else if(Math.abs(input[i]) == Math.abs(input[i+1])) {
                        // 绝对值相同，同时消亡
                    } else {
                        // 右边绝对值大
                        s.push(input[i+1]);
                    }
                    i++;
                } else {
                    s.push(input[i]);
                }
            }

        }
        int[] r = new int[s.size()];
        int idx = r.length -1;
        while (!s.isEmpty()) {
            r[idx] = s.pop();
            idx--;
        }
        return r;
    }

}

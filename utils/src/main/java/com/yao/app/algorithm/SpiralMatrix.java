package com.yao.app.algorithm;

import java.util.Arrays;

/**
 * / 给定一个m x n大小的矩阵（m行，n列），按螺旋的顺序返回矩阵中的所有元素。 //例如， //给出以下矩阵： //[ //  [ 1，2，3，4], //  [5，6，7，8], //  [9，10，11，12], //  [13，14，15，16]， //  [17，18，19，20] //] //
 * //你应该返回[1, 2, 3, 4, 8, 12, 16, 20, 19, 18, 17, 13, 9, 5, 6, 7, 11, 15, 14, 10]。
 */
public class SpiralMatrix {

    public static void main(String[] args) {
        int[][] input = new int[5][4];
        input[0] = new int[]{1, 2, 3, 4};
        input[1] = new int[]{5, 6, 7, 8};
        input[2] = new int[]{9, 10, 11, 12};
        input[3] = new int[]{13, 14, 15, 16};
        input[4] = new int[]{17, 18, 19, 20};

        // int[] result = spiralOrder(input);
        // System.out.println(Arrays.toString(result));


        int[][] input2 = new int[3][4];
        input2[0] = new int[]{1, 2, 3, 4};
        input2[1] = new int[]{5, 6, 7, 8};
        input2[2] = new int[]{9, 10, 11, 12};

        int[] result2 = spiralOrder(input2);
        System.out.println(Arrays.toString(result2));
    }

    public static int[] spiralOrder(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[] result = new int[m * n];
        int idx = 0;

        int horizontalLen = n;
        int verticalLen = m;

        int i = 0;
        int j = -1;

        while (verticalLen >= 1 && horizontalLen >= 1) {

            // 向右
            int count = 0;
            while (count < horizontalLen) {
                j++;
                result[idx++] = matrix[i][j];
                count++;
            }

            // 向下
            count = 0;
            while (count < verticalLen - 1) {
                i++;
                result[idx++] = matrix[i][j];
                count++;
            }

            if(verticalLen == 1 || horizontalLen == 1){
                // 高度或宽度为0，就不需要向左并向上了
                break;
            }

            // 向左
            count = 0;
            while (count < horizontalLen - 1) {
                j--;
                result[idx++] = matrix[i][j];
                count++;
            }

            // 向上
            count = 0;
            while (count < verticalLen - 2) {
                i--;
                result[idx++] = matrix[i][j];
                count++;
            }

            horizontalLen -= 2;
            verticalLen -= 2;

            System.out.println(Arrays.toString(result));
        }
        return result;
    }
}

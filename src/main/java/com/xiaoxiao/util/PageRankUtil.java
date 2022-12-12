package com.xiaoxiao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PageRankUtil {
    public static List<List<Integer>> loadData(String path, String fileName) {
        File file = new File(path+fileName);
        BufferedReader reader = null;
        List<List<Integer>> result = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine()) != null) {
                result.add(Arrays.asList(tempString.split(",")).stream().map(Integer::valueOf).collect(Collectors.toList()));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return result;
    }

    /**
     * 矩阵运算
     * @param matrix1 矩阵1
     * @param matrix2 矩阵2
     * @return 矩阵1 叉乘 矩阵2
     */
    public static double[][] matrixManipulation(double[][] matrix1, double[][] matrix2) throws Exception {
        // 矩阵1的列的个数
        int a = matrix1[0].length;
        // 矩阵1的行的个数
        int b = matrix1.length;
        // 矩阵2的列的个数
        int c = matrix2[0].length;
        // 矩阵2的行的个数
        int d = matrix2.length;

        if (a != d) {
            throw new Exception("矩阵无法相乘");
        }

        double[][] result = new double[b][c];
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < c; j++) {
                for (int k = 0; k < a; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }


        return result;
    }

}

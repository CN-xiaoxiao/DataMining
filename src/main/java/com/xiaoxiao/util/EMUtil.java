package com.xiaoxiao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EMUtil {

    /**
     * 计算是当前概率的物品的可能性
     * @param coinProbability
     * @param data
     * @return
     */
    public static double calculateCoinProbability(double coinProbability, int[] data) {
        return Math.pow(coinProbability, data[0]) * Math.pow((1-coinProbability), data[1]);
    }

    // 加载数据
    public static int[][] loadData(String path, String fileName) {
        File file = new File(path+fileName);
        BufferedReader reader = null;
        List<List<String>> result = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine()) != null) {
                result.add(Arrays.stream(tempString.split(",")).map(String::valueOf).collect(Collectors.toList()));
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

        int[][] data =  new int[result.size()][2];

        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                if (result.get(i).get(j).equals("H")) {
                    data[i][0]++;
                } else {
                    data[i][1]++;
                }
            }
        }



        return data;
    }
}

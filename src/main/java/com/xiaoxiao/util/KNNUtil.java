package com.xiaoxiao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class KNNUtil {

    public static List<List<String>> loadData(String path, String fileName) {
        File file = new File(path + fileName);
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
        return result;
    }

    /**.
     *
     * @param newData 下一条数据
     * @param list 带判断类别的数据
     * @param array 由k个数据组成的数组
     * @return
     */
    public static int isSimilar(List<String> newData, List<String> list, List<List<String>> array) {
        // 获取身高
        double newDataHigh = Double.parseDouble(newData.get(1));
        double listHigh = Double.parseDouble(list.get(1));
        double[] arrayHigh = new double[array.size()];
        for (int i = 0; i < array.size(); i++) {
            arrayHigh[i] = Double.parseDouble(array.get(i).get(1));
        }

        // 数据集中的下一条数据与带查询类别的数据的距离差
        double newAndList = Math.abs(newDataHigh - listHigh);

        // k个数据分别与带查询的数据的距离差看与newAndList哪个相差大，如果newDataHigh的更接近，
        // 将k个数据中最不接近list的替换掉（这里返回下标）
        double[] arrayDistance = new double[array.size()];
        boolean flag = false;
        for (int i = 0; i < array.size(); i++) {
            arrayDistance[i] = Math.abs(arrayHigh[i] - listHigh);
            // 问题：这里是绝对值，如果大于这个数和小于这个数的差的绝对值一样，该如何判断？这里没处理
            // 也就是说，加入还是不加入。感觉影响挺大的。
            // 或许不能直接相减？应该要优化他们差距的算法。
            if (arrayDistance[i] > newAndList) {
                flag = true;
            }
        }

        if (flag) {
            double temp = arrayDistance[0];
            for (int i = 1; i < arrayDistance.length; i++) {
                if (arrayDistance[i] > temp) {
                    temp = arrayDistance[i];
                }
            }
            for (int i = 0; i < arrayDistance.length; i++) {
                if (arrayDistance[i] == temp) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static String getTheMostLike(List<List<String>> array) {
        Map<String, Integer> map = new HashMap<>();
        String result = null;
        int max = -1;
        for (int i = 0; i < array.size(); i++) {
            if (!map.containsKey(array.get(i).get(2))) {
                map.put(array.get(i).get(2), 1);
            } else {
                int temp = map.get(array.get(i).get(2));
                temp++;
                map.put(array.get(i).get(2), temp);
                if (temp > max) {
                    max = temp;
                }
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == max) {
                result = entry.getKey();
                break;
            }
        }

        return result;
    }
}

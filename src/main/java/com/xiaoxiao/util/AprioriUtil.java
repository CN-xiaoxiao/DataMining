package com.xiaoxiao.util;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;

import java.io.*;
import java.util.*;

public class AprioriUtil {
    /**
     * 读取原始数据集
     * @param fileName 原始数据集的名称
     * @return 一个保存原始数据集的数组
     */
    public static List<String> loadData(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        List<String> data = new ArrayList<String>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine()) != null) {
                data.add(tempString);
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
        return data;
    }

    /**
     * 将原始数据集中的数据建立一个汉字（英文）-数值的索引表
     * 使用Apache Commons Collections 4.4，可以很好的解决一些集合操作，
     * 其他地方就先不改了，只弄这个hashMap的
     * @param array 数据集
     * @return 返回一个索引表
     */
    public static BidiMap<String, Integer> makeIndex(List<List<String>> array) {
        // 注意如果有相同的值存在BidiMap会覆盖掉已有的key，但是我们这个是索引表，不存在相同的值
        BidiMap<String, Integer> result = new TreeBidiMap<>();
        int index = 1;
        List<String> temp = new ArrayList<String>();
        // 1.遍历array数据集
        for (List<String> s1 : array) {
            for (String s2 : s1) {
                // 2.判断当前名词是否在temp中，如果在，则说明重复了，否则，将该名词（元素）加入temp中
                if (!temp.contains(s2)) {
                    temp.add(s2);
                }
            }
        }

        // 3.建立映射表
        for (String s : temp) {
            result.put(s, index++);
        }
        return result;
    }

    /**
     * 切割数组，每一个名词单独切割出来
     * @param array 数据集
     * @return 切割好的数组
     */
    public static List<List<String>> cattingArray(List<String> array) {
        List<List<String>> result = new ArrayList<List<String>>();
        List<String> curList = null;
        String[] temp = null;

        // 遍历数据集
        for (String s : array) {
            // 分割数组，分割每一行数据
            temp = s.split("、");
            // 创建一个新的arraylist数组来存放分割后的每一个名词
            curList = new ArrayList<>();
            // 遍历temp，逐个存入curList中
            for (String s2 : temp) {
                curList.add(s2);
            }
            // 将保存了每一行数据的curList数组存入新的数据集容器
            result.add(curList);
        }
        return result;
    }

    /**
     * 将文字数据集转变为数字数据集
     * @param array 分割好了的数据集
     * @param index 文字-数字 索引表
     * @return
     */
    public static List<List<Integer>> strToSum(List<List<String>> array, BidiMap<String, Integer> index) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> curList = null;

        for (List list : array) {
            curList = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                curList.add(index.get(list.get(i)));
            }
            result.add(curList);
        }

        return result;
    }

    /**
     * 将数字数据集变回文字
     * @param array
     * @param index
     * @return
     */
    public static List<List<String>> sumToStr(List<List<Integer>> array, BidiMap<String, Integer> index) {
        List<List<String>> result = new ArrayList<>();
        List<String> curList = null;
        BidiMap<Integer, String> iMap = index.inverseBidiMap();

        for (List list : array) {
            curList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                curList.add(iMap.get(list.get(i)));
            }
            result.add(curList);
        }

        return result;
    }

    /**
     * 将数字数据集变回文字
     * @param array
     * @param index
     * @return
     */
    public static List<String> sumToStrForList(List<Integer> array, BidiMap<String, Integer> index) {
        BidiMap<Integer, String> iMap = index.inverseBidiMap();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            list.add(iMap.get(array.get(i)));
        }

        return list;
    }

    /**
     * 将频繁项目集从map集合变为List集合，同时去掉该项目出现的次数
     * @param map
     * @return
     */
    public static List<List<Integer>> setToList(Map<List<Integer>, Integer> map) {
        List<List<Integer>> result = new ArrayList<>();

        for (Map.Entry<List<Integer>, Integer> entry : map.entrySet()) {
            List<Integer> mapKey = entry.getKey();
            result.add(mapKey);
        }

        return result;
    }

    /**
     * 得到频繁项集的次数，并返回一个map集合
     * @param dataSet 数据集
     * @param allFrequent 频繁项集
     * @return map集合
     */
    public static Map<List<Integer>, Integer> getFrequentCount(List<List<Integer>> dataSet, List<List<Integer>> allFrequent) {
        Map<List<Integer>, Integer> result = new LinkedHashMap<List<Integer>, Integer>();

        for (List<Integer> frequent : allFrequent) {
            int count = 0;
            for (List<Integer> list : dataSet) {
                if (list.containsAll(frequent)) {
                    count++;
                }
            }
            result.put(frequent,count);
        }

        return result;
    }

    /**
     * 将一个频繁项集切割组合成多个List数组，并以map的形式返回
     * 难点，如何把一个数组排列组合的拆分成两个数组。
     * @param frequent 频繁项集
     * @return 一个包含了所有该频繁项集的排列组合的map集合
     */
    public static Map<List<Integer>, List<Integer>> splitList(List<Integer> frequent) {

        Map<List<Integer>, List<Integer>> map = new HashMap<List<Integer>, List<Integer>>();

        // 全部的子集
        List<List<Integer>> allTrueSubset = new ArrayList<List<Integer>>();

        // 1.求出频繁项集的所有真子集
        //子集的数量
        int n = 1 << frequent.size();
        List<Integer> temp = null;

        for(int i = 0; i < n; i ++)
        {
            temp = new ArrayList<>();
            int j = i;
            int index = frequent.size() - 1;
            //循环前检测j是否是000
            while(j > 0)
            {
                if((j&1) == 1)
                {
                    temp.add(frequent.get(index));
                }
                j = j >> 1;
                index --;
            }

            temp.sort(Comparator.comparing(Integer::intValue));
            if (temp.size() != 0 && temp.size() != frequent.size()) {
                allTrueSubset.add(temp);
            }
        }

        // 2.遍历真子集，并在频繁项集中去除
        List<Integer> allOtherSubset = null;
        for (List<Integer> list : allTrueSubset) {
            allOtherSubset = new ArrayList<Integer>();
            HashSet h1 = new HashSet(frequent);
            HashSet h2 = new HashSet(list);
            h1.removeAll(h2);
            allOtherSubset.addAll(h1);

            map.put(list, allOtherSubset);
        }

        return map;
    }
}

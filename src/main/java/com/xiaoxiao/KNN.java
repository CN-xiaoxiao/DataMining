package com.xiaoxiao;


import com.xiaoxiao.util.KNNUtil;

import java.util.*;

/**
 * 思路：从给定的K的数据为基准，向要确认的类别靠近，依次把离需要确认的类别比较远的
 * 数据替换掉，直到数据集的最后一条，然后在根据最后的K个数据的类别来判断需要确认的
 * 列别的数据的类别是哪个（看谁占大多数）
 */
public class KNN {
    private int k;
    private List<List<String>> dataset;
    private List<List<String>> array;
    private List<String> list;

    public void knn() {
        for (int i = 0; i < dataset.size(); i++) {
            if (array.size() < k) {
                array.add(dataset.get(i));
            } else {
                int flag = KNNUtil.isSimilar(dataset.get(i), list, array);
                if (flag != -1) {
                    array.remove(flag);
                    array.add(dataset.get(i));
                }
            }
        }

        String sort = KNNUtil.getTheMostLike(array);

        System.out.println(sort);
    }

    public KNN(List<List<String>> dataset, List<String> list, int k) {
        this.dataset = dataset;
        this.array = new ArrayList<>();
        this.k = k;
        this.list = list;
    }
}

package com.xiaoxiao;


import java.util.*;

public class Apriori {
    private double minSupport;
    private double minConfidence;

    /**
     * 这里用来生成频繁1项集
     * @param dataSet
     * @return
     */
    public Map<List<Integer>, Integer> oneFrequentSet(List<List<Integer>> dataSet) {
        // 定义一个map数组，用来存放频繁项目集和他出现的次数
        Map<List<Integer>, Integer> result = new HashMap<>();

        // 1.遍历dataSet,找到每一个数据出现的次数
        for (List<Integer> list : dataSet) {
            for (int i = 0; i < list.size(); i++) {
                List<Integer> temp = new ArrayList<Integer>();
                temp.add(list.get(i));
                if (!result.containsKey(temp)) {
                    result.put(temp, 1);
                } else {
                    int a = result.get(temp);
                    a++;
                    result.put(temp, a);
                }
            }
        }

        return result;
    }

    /**
     *
     * @param result 待处理的候选集
     * @param count 候选集的总条数（多少行数据）
     * @return 返回处理好后的频繁项集
     */
    public Map<List<Integer>, Integer> getSupportItemsSet(Map<List<Integer>, Integer> result, int count) {
        // 返回频繁项目集
        Map<List<Integer>, Integer> oneFre = new HashMap<>();
        // 最小支持度
        double support = this.minSupport;

        // 2.生成频繁1项集
        for (Map.Entry<List<Integer>, Integer> entry : result.entrySet()) {
            List<Integer> mapKey = entry.getKey();
            int mapValue = entry.getValue();
            // 小于最小支持度的全部不要，保留支持度大于的
            if ((double)mapValue/count > support) {
                oneFre.put(mapKey, mapValue);
            }
        }
        return oneFre;
    }

    /**
     * 产生Ck候选集：频繁k-1项目集两两组合，新的组合的元素大小是原来的+1.
     * @param dataSet 频繁K-1项集
     * @param k 表示这是频繁几项集
     * @return 候选集Ck
     */
    public List<List<Integer>> lkToCk(List<List<Integer>> dataSet, int k) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> list2 = null;
        List<Integer> temp = null;

        // 1.遍历频繁K-1项目集
        for (int i = 0; i < dataSet.size() - 1; i++) {
            for (int j = i+1; j < dataSet.size(); j++) {
                // 2.将两个数组合并为一个，并去重
                temp = new ArrayList<>(dataSet.get(i));
                temp.addAll(dataSet.get(j));
                temp = new ArrayList<Integer>(new LinkedHashSet<>(temp));

                // 3.判断读取的候选集的元素个数，如果大于k，则去掉,等于k+1则保存起来
                if (temp.size() == k+1) {
                    list.add(temp);
                }
            }
        }

        return list;
    }

    /**
     * 需要重新写
     * @param dataSet
     * @param frequent
     * @return
     */
    public Map<List<Integer>, Integer> ckToLk(List<List<Integer>> dataSet, List<List<Integer>> frequent) {
        // 定义一个map数组，用来存放频繁项目集和他出现的次数
        Map<List<Integer>, Integer> result = new HashMap<>();

        // 1.遍历frequent,记录其每一个数据，在dataSet中是否存在，及他出现的次数
        for (List<Integer> list : frequent) {
            // 2.判断是否在数据集中存在
            if (dataSet.containsAll(list)) {
                result.put(list, 0);

                // 3.遍历dataSet
                for (List<Integer> listForData : dataSet) {
                    if (listForData.)
                }
//                // 3.判断是否已经存入result中，如果已经存入，则其出现的次数+1，否则将该数据存入result中
//                if (!result.containsKey(list)) {
//                    result.put(list, 1);
//                } else {
//                    int count = result.get(list);
//                    count++;
//                    result.put(list, count);
//                }
            }

        }

        return result;
    }

    public Apriori(double minSupport, double minConfidence) {
        this.minSupport = minSupport;
        this.minConfidence = minConfidence;
    }
    public Apriori() {
        this.minSupport = 0.5;
        this.minConfidence = 0.5;
    }

    public double getMinSupport() {
        return minSupport;
    }

    public void setMinSupport(double minSupport) {
        this.minSupport = minSupport;
    }

    public double getMinConfidence() {
        return minConfidence;
    }

    public void setMinConfidence(double minConfidence) {
        this.minConfidence = minConfidence;
    }
}

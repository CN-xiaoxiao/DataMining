package com.xiaoxiao.apriori;

import com.xiaoxiao.util.AprioriUtil;
import org.apache.commons.collections4.BidiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AprioriStart {
    private Apriori apriori;
    private List<List<Integer>> dataSet;

    private BidiMap<String, Integer> strToSumMap;

    private Map<List<Integer>, Integer> frequentCountMap;

    public List<List<Integer>> doApriori() {
        // 4.生成频繁1项集
        Map<List<Integer>, Integer> oneFreTemp = apriori.oneFrequentSet(dataSet);

        // 记录总个数（交易数、）
        int count = dataSet.size();

        // map形式的频繁1项集
        Map<List<Integer>, Integer> oneSupportItemsSet = apriori.getSupportItemsSet(oneFreTemp, count);

        // 记录一下频繁1项集的个数，可能会围绕其展开
        int oneFreCount = oneSupportItemsSet.size();

        // list形式的频繁1项集
        List<List<Integer>> oneFre = AprioriUtil.setToList(oneSupportItemsSet);

        List<List<Integer>> Lk = oneFre;
        List<List<Integer>> allFreSet = new ArrayList<List<Integer>>();
        allFreSet.addAll(Lk);
        // 5.循环执行Ck->Lk, Lk->Ck,直到没有频繁项集出现，就可以停止了。
        for (int k = 1; k <= count; k++) {
            // 6.根据频繁项集Lk生成候选Ck+1项集
            List<List<Integer>> CkAdd = apriori.lkToCk(Lk, k);
//            System.out.println(CkAdd);

            // 生成频繁项集Lk+1
            Map<List<Integer>, Integer> LkTempAdd = apriori.ckToLk(dataSet, CkAdd);
            Map<List<Integer>, Integer> LkAdd = apriori.getSupportItemsSet(LkTempAdd, count);

            Lk = AprioriUtil.setToList(LkAdd);
            // 7.判断频繁项集Lk+1是否为空，
            if (Lk.size() == 0) {
                break;
            } else {
                allFreSet.addAll(Lk);
            }
        }

        return allFreSet;
    }

    public void printFrequentSet(List<List<Integer>> frequentSet) {
        System.out.println(AprioriUtil.sumToStr(frequentSet, strToSumMap));
    }
    /**
     * 加载数据
     * @param fileName
     */
    public void loadDataSet(String fileName) {
        // 1.读取数据，将数据集读取出来，并保存到一个List数组里
        List<String> strings = AprioriUtil.loadData(fileName);
        // 2.将数据进行编码转换：汉字、英文--->阿拉伯数字
        List<List<String>> data = AprioriUtil.cattingArray(strings);
        this.strToSumMap = AprioriUtil.makeIndex(data);
        // 3.将原数组进行转换
        this.dataSet = AprioriUtil.strToSum(data, strToSumMap);
    }
    /**
     * 1.得到频繁项集
     * 2.从每一个频繁项集得到其真子集
     * 3.对每个真子集求出强关联规则
     * 4.数值转换
     * @param allFreSet 全部的频繁项集
     */
    public void getStrongAssociationRules(List<List<Integer>> allFreSet) {
        this.getFreCount(dataSet, allFreSet);
        int allCount = dataSet.size();
        // 1.遍历频繁项集
        for (List<Integer> list : allFreSet) {
            // 2.判断该频繁项集是否是频繁1项集，如果是跳过本次循环
            int index = 1;
            if (list.size() == 1) {
                continue;
            }
            // 3.求出该频繁项集的真子集
            Map<List<Integer>, List<Integer>> listListMap = AprioriUtil.splitList(list);
            // 4.循环遍历listListMap, 求出强关联规则
            for (Map.Entry<List<Integer>, List<Integer>> entry : listListMap.entrySet()) {
                List<Integer> mapKey = entry.getKey();
                int count = frequentCountMap.get(mapKey);
                double value = (double) count/allCount;
                if ( value >= apriori.getMinConfidence()) {
                    List<String> newListKey = AprioriUtil.sumToStrForList(mapKey, strToSumMap);
                    List<Integer> mapValue = entry.getValue();
                    List<String> newListValue = AprioriUtil.sumToStrForList(mapValue, strToSumMap);
                    System.out.println(newListKey + "------->" + newListValue + "----" + value);
                }
            }
        }
    }

    public void getFreCount(List<List<Integer>> dataSet, List<List<Integer>> allFrequent) {
        this.frequentCountMap = AprioriUtil.getFrequentCount(dataSet, allFrequent);
    }

    public AprioriStart(Apriori apriori, String fileName) {
        this.apriori = apriori;
        loadDataSet(fileName);
    }
}

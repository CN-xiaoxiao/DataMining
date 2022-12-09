package com.xiaoxiao;

import com.xiaoxiao.util.AprioriUtil;
import org.apache.commons.collections4.BidiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AprioriStart {
    private Apriori apriori;

    public List<List<Integer>> doApriori() {
        // 1.读取数据，将数据集读取出来，并保存到一个List数组里
        List<String> strings = AprioriUtil.loadData("src/main/resources/data1.txt");
        // 2.将数据进行编码转换：汉字、英文--->阿拉伯数字
        List<List<String>> data = AprioriUtil.cattingArray(strings);
        BidiMap<String, Integer> strToSumMap = AprioriUtil.makeIndex(data);
        // 3.将原数组进行转换
        List<List<Integer>> dataSet = AprioriUtil.strToSum(data, strToSumMap);

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

    public AprioriStart(Apriori apriori) {
        this.apriori = apriori;
    }
}

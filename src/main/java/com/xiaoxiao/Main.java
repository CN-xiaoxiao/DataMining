package com.xiaoxiao;

import com.xiaoxiao.knn.KNN;
import com.xiaoxiao.util.KNNUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /**
         * 1、Apriori算法
         */
//        AprioriStart aprioriStart = new AprioriStart(new Apriori(0.5,0.5), "src/main/resources/data1.txt");
//        List<List<Integer>> allFreSet = aprioriStart.doApriori();
//        aprioriStart.printFrequentSet(allFreSet);
//        aprioriStart.getStrongAssociationRules(allFreSet);

        /**
         * 4. EM算法
         */
//        EMStart emStart = new EMStart(new EM("src/main/resources/", "data4.txt", new double[]{0.2,0.7}, 0.01));
//        emStart.startEM();

        /**
         * 5、KNN算法
         */
        List<List<String>> dataSet = KNNUtil.loadData("src/main/resources/", "data5.txt");
        List<String> list = new ArrayList<String>();
        list.add("易昌");
        list.add("1.74");
        KNN Knn = new KNN(dataSet, list,5);
        Knn.knn();

        /**
         * 9. PageRank算法
         */
//        List<Integer> R = new ArrayList<Integer>(4);
//        R.add(1);
//        R.add(1);
//        R.add(1);
//        R.add(1);
//
//        PageRankStart pageRankStart = new PageRankStart(new PageRank("src/main/resources/",
//                "data9.txt",R,0.85,0.1));
//        pageRankStart.pageRankStart();
    }
}

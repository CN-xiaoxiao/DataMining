package com.xiaoxiao;

import com.xiaoxiao.apriori.Apriori;
import com.xiaoxiao.apriori.AprioriStart;

import java.util.ArrayList;
import java.util.Collections;
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
         * 9. PageRank算法
         */
        List<Integer> R = new ArrayList<Integer>(4);
        R.add(1);
        R.add(1);
        R.add(1);
        R.add(1);

        PageRankStart pageRankStart = new PageRankStart(new PageRank("src/main/resources/",
                "data9.txt",R,0.85,0.1));
        pageRankStart.pageRankStart();
    }
}

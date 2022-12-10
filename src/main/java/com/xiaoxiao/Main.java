package com.xiaoxiao;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        /**
         * 1、Apriori算法
         */
        AprioriStart aprioriStart = new AprioriStart(new Apriori(0.5,0.5), "src/main/resources/data1.txt");
        List<List<Integer>> allFreSet = aprioriStart.doApriori();
        aprioriStart.printFrequentSet(allFreSet);
        aprioriStart.getStrongAssociationRules(allFreSet);
    }
}

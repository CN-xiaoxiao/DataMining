package com.xiaoxiao;

import com.xiaoxiao.util.AprioriUtil;
import org.apache.commons.collections4.BidiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        /**
         * 1、Apriori算法
         */
        AprioriStart aprioriStart = new AprioriStart(new Apriori(0.5,0.5));
        List<List<Integer>> allFreSet = aprioriStart.doApriori();
        System.out.println(allFreSet);
    }
}

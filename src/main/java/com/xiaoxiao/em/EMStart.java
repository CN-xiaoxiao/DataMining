package com.xiaoxiao.em;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EMStart {
    private EM em;

    public void startEM() {
        double[] probabilityArray = em.getProbabilityArray();
        int[][] dataSet = em.getDataSet();
        double threshold = em.getThreshold();
        List<Integer> coinProbability = new ArrayList<Integer>();

        double[] oldProbably = em.getProbabilityArray();

        int count = 0;
        for (;;) {
            // 1.计算每一次实验中最可能是哪个硬币
            for (int i = 0; i < dataSet.length; i++) {
                coinProbability.add(em.mostLikelyCoin(oldProbably, dataSet[i]));
            }
            System.out.println("*** " + count++ + " ***");
            System.out.println("coinProbability: " + coinProbability);
            double[] newProbably = em.calculateNewProbability(coinProbability, dataSet);

            System.out.println("oldProbably: " + Arrays.toString(oldProbably));
            System.out.println("newProbably: " + Arrays.toString(newProbably));
            if (!em.isContinue(oldProbably, newProbably,threshold)) {
                for (int i = 0; i < oldProbably.length; i++) {
                    oldProbably[i] = newProbably[i];
                }
                break;
            }
            coinProbability.clear();
            for (int i = 0; i < oldProbably.length; i++) {
                oldProbably[i] = newProbably[i];
            }
        }
        System.out.println(Arrays.toString(probabilityArray));
    }

    public EMStart(EM em) {
        this.em = em;
    }
}

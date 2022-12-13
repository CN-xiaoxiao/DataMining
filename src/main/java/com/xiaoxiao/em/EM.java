package com.xiaoxiao.em;


import com.xiaoxiao.util.EMUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 思路：
 * 1.先初始化一个硬币A和硬币B正面向上的概率<p/>
 * 2.根据初始化的概率求每一次投的硬币的概率（每次硬币最可能是哪个硬币）<p/>
 * 3.根据上一步的硬币投出结果重新计算硬币A和硬币B正面向上的概率<p/>
 * 4.将重新计算出的概率与之前初始化的相对比，如果相差不大了，就可以判定该结果是最可能的结果
 * <p/>
 * 貌似不会求多种可能的情况下，怎么判断其他结果的可能性。
 * 比如三种可能，知道第一种可能的概率，其他两种可能的概率该如何求
 * 物品的个数无所谓。应该是条件少了，解决方案是再来一个数组存放第二种可能性的概率
 * 可能性越多，要求存的可能性的概率的数组越多，可考虑使用嵌套List,后续只需修改
 * 一些细节即可，（应该，数学问题
 * 这里只考虑两种可能的情况，即要么正确要么错误。
 */
public class EM {

    // 存放当前每个物品投正的概率
    private double[] probabilityArray;
    // 一共有多少个物品（求概率的总个数？）
    private int coinSize;
    // 数据集（每种结果转换成整型数组存储,两种）
    private int[][] dataSet;
    // 存放这一轮中每次可能是用谁实验的情况
    private List<Integer> coinProbability;

    private double threshold;

    /**
     * 根据初始化的概率求每一次投的硬币的概率（每次硬币最可能是哪个硬币）
     * @param data 一次实验
     * @return 该次实验中最可能使用的物品。
     */
    public int mostLikelyCoin(double[] probabilityArray, int[] data) {
        int max = -1;
        double temp = 0.0;

        // 1.遍历probabilityArray，看在data中谁的概率最大
        for (int i = 0; i < this.coinSize; i++) {
            double probability = EMUtil.calculateCoinProbability(probabilityArray[i], data);
            // 2.如果大于或者等于交换，即更新
            // 这里也可以把概率相等的时候单独考虑，即用一个数组来存每一种概率所对应的物品的概率，然后用伪随机等概率的
            // 挑选出概率相同的时候的物品中的一个（最后结果概率最大出现等概率的情况）
            if (probability >= temp) {
                max = i;
                temp = probability;
            }
        }

        return max;
    }


    /**
     * 根据上一步的硬币投出结果重新计算硬币A和硬币B正面向上的概率
     * @param coinProbability 猜测的 每次实验使用的硬币
     * @param dataSet 数据集
     * @return 新的使用每个硬币投正的概率
     */
    public double[] calculateNewProbability(List<Integer> coinProbability, int[][] dataSet) {
        int[][] count = new int[this.coinSize][2];
        double[] result = new double[this.coinSize];

        for (int i = 0; i < coinProbability.size(); i++) {
            count[coinProbability.get(i)][0] += dataSet[i][0];
            count[coinProbability.get(i)][1] += dataSet[i][1];
        }

        for (int i = 0; i < count.length; i++) {
            result[i] = (double) count[i][0] / (count[i][0] + count[i][1]);
        }

        return result;
    }

    /**
     * 将重新计算出的概率与之前初始化的相对比，如果相差不大了，就可以判定该结果是最困难的结果
     * @param newProbably
     * @param oldProbably
     * @param threshold 阈值
     * @return
     */
    public boolean isContinue(double[] newProbably, double[] oldProbably, double threshold) {
        for (int i = 0; i < newProbably.length; i++) {
            if (Math.abs(newProbably[i] - oldProbably[i]) > threshold) {
                return true;
            }
        }
        this.probabilityArray = newProbably;
        return false;
    }

    /**
     * @param probabilityArray 存放当前每个物品(硬币)投正的概率
     * @param threshold 阈值
     */
    public EM(String path, String fileName, double[] probabilityArray, double threshold) {
        this.probabilityArray = probabilityArray;
        this.dataSet = EMUtil.loadData(path, fileName);
        this.threshold = threshold;
        this.coinSize = probabilityArray.length;
        this.coinProbability = new ArrayList<>();
    }

    public double[] getProbabilityArray() {
        return probabilityArray;
    }

    public int getCoinSize() {
        return coinSize;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public int[][] getDataSet() {
        return dataSet;
    }

    public List<Integer> getCoinProbability() {
        return coinProbability;
    }

    public void setProbabilityArray(double[] probabilityArray) {
        this.probabilityArray = probabilityArray;
    }

    public void setDataSet(int[][] dataSet) {
        this.dataSet = dataSet;
    }


}

package com.xiaoxiao.kmeans;

import com.xiaoxiao.util.KMeansUtil;

import java.util.ArrayList;
import java.util.List;

public class KMeans {
    // k个对象作为初始的簇中心
    private int k;
    // 数据集
    private List<Point> dataSet;
    // 簇集合
    private List<List<Point>> clusters;
    // 簇中心
    private List<Point> center;

    private final double Threshold;

    public void kMeans() {
        this.center = KMeansUtil.initCenter(this.dataSet, this.k);
        List<Double> E = new ArrayList<Double>();
        int i;
        for (i = 0; ; i++) {
            clusters = KMeansUtil.setCluster(dataSet,center,k);
            E.add(KMeansUtil.getE(dataSet,center,clusters,k));

            if (i != 0) {
                if (Math.abs(E.get(i) - E.get(i-1)) <= this.Threshold) {
                    break;
                }
            }
            center = KMeansUtil.setNewCenter(dataSet, clusters, k);
            System.out.println("******第" + (i+1) + "次迭代**********");
            printClusters();
        }
        System.out.println("******最终**********");
        printClusters();
    }

    public void printClusters() {
        for (int i = 0; i < clusters.size(); i++) {
            System.out.print(center.get(i).getName() + "元素: ");
            for (int j = 0; j < clusters.get(i).size(); j++) {
                System.out.print(clusters.get(i).get(j).getName() + " ");
            }
            System.out.println();
        }
    }
    public KMeans(String path, String fileName, int k, double threshold) {
        this.k = k;
        this.Threshold = threshold;
        this.dataSet = KMeansUtil.loadData(path, fileName);
    }
}

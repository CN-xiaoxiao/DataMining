package com.xiaoxiao.pageRank;

import com.xiaoxiao.util.PageRankUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageRank {
    private List<List<Integer>> dataSet;
    private List<Integer> R;
    private double finalCondition;
    private double experiencePoints;

    /**
     * 求转移概率矩阵。
     * 公式：M = (1-d)*Q + d*A
     */
    public double[][] getMatrixTransitionProbability() {
        List<List<Integer>> dataSet = this.getDataSet();
        int n = dataSet.size();
        double[][] M = new double[n][n];
        double[][] Q = new double[n][n];
        double[][] A = new double[n][n];
        double d = this.getExperiencePoints();

        for (int i = 0; i < n; i++) {
            Arrays.fill(Q[i],0, n, 1.0/n);
        }


        // (1-d)*Q
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Q[i][j] = (1-d) * Q[i][j];
            }
        }

        // 求A
        // 1.获取这一个数组有多少个1
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (dataSet.get(j).get(i) == 1) {
                    count++;
                }
            }
            for (int j = 0; j < n; j++) {
                if (dataSet.get(j).get(i) == 0) {
                    A[j][i] = 0;
                } else {
                    A[j][i] = 1.0/count;
                }
            }
        }
//        for (int i = 0; i < n; i++) {
//            int count = 0;
//            for (int j = 0; j < dataSet.get(i).size(); j++) {
//                if (dataSet.get(i).get(j) == 1) {
//                    count++;
//                }
//            }
//            // 2.原有向图没有指向的地方为0，否则为全部指向的路径的个数分之一
//            for (int j = 0; j < dataSet.get(i).size(); j++) {
//                if (dataSet.get(i).get(j) == 0) {
//                    A[i][j] = 0;
//                } else {
//                    A[i][j] = 1.0/count;
//                }
//            }
//        }

        // 求(1-d)*Q+d*A
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                M[i][j] = Q[i][j]+A[i][j]*d;
            }
        }

        return M;
    }

    public double[][] pageRankStart() throws Exception {
        double[][] M = getMatrixTransitionProbability();
        List<Integer> R = this.getR();
        int n = M.length;
        double[][] oldR = new double[n][1];
        double[][] newR = new double[n][1];
        double finalCondition = this.getFinalCondition();

        for (int i = 0; i < R.size(); i++) {
            oldR[i][0] = 1.0*R.get(i);
        }

        for (;;) {
            newR = PageRankUtil.matrixManipulation(M,oldR);

            // 这里计算1范数，可以抽出来单独形成一个方法，但是因为是1列，所以可以直接加起来就行
            // ||Ri+1 - Ri||
            double sum = 0;
            for (int i = 0; i < n; i++) {
                sum += Math.abs(newR[i][0] - oldR[i][0]);
            }

            if (sum <= finalCondition) {
                return newR;
            }

            for (int i = 0; i < n; i++) {
                oldR[i][0] = newR[i][0];
            }
        }
    }

    public PageRank(String path, String fileName, List<Integer> R, double experiencePoints , double finalCondition) {
        this.dataSet = PageRankUtil.loadData(path, fileName);
        this.R = R;
        this.finalCondition = finalCondition;
        this.experiencePoints = experiencePoints;
    }

    public List<List<Integer>> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<List<Integer>> dataSet) {
        this.dataSet = dataSet;
    }

    public List<Integer> getR() {
        return R;
    }

    public void setR(List<Integer> r) {
        R = r;
    }

    public double getFinalCondition() {
        return finalCondition;
    }

    public void setFinalCondition(double finalCondition) {
        this.finalCondition = finalCondition;
    }

    public double getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(double experiencePoints) {
        this.experiencePoints = experiencePoints;
    }
}

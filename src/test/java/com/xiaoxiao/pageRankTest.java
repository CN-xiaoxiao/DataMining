package com.xiaoxiao;

import com.xiaoxiao.pageRank.PageRank;
import com.xiaoxiao.util.PageRankUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class pageRankTest {

    @Test
    public void testLoad() {
        System.out.println(PageRankUtil.loadData("src/main/resources/","data9.txt"));
    }

    @Test
    public void testList() {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        List<Integer> list2 = new ArrayList<Integer>();
        List<Integer> list3 = new ArrayList<Integer>();
        List<Integer> list4 = new ArrayList<Integer>();
        list2.add(1);
        list2.add(2);
        list2.add(3);
        list3.add(4);
        list3.add(5);
        list3.add(6);
        list4.add(7);
        list4.add(8);
        list4.add(9);
        list.add(list2);
        list.add(list3);
        list.add(list4);

//        System.out.println(list);

        for (int i = 0; i < list.size(); i++) {
            List<Integer> temp = list.get(i);
            for (int j = 0; j < temp.size(); j++) {
                System.out.println(temp.get(j));
            }
        }

        // 问题List<List<Integer>> 类型比int[][] 的操作好像难了一点，感觉没这么方便。算了。这里就不转换成二维数组了。

//        int[][] array = (int[][]) list.toArray();
//        for (int i = 0; i < array.length; i++) {
//            for (int j =0; j< array[i].length; j++) {
//                System.out.println(array[i][j]);
//            }
//        }
    }

    @Test
    public void testArrayFill() {
        double[][] Q = new double[5][5];
        Arrays.fill(Q[1], 0, 5, 1.0/5);
        System.out.println(Arrays.toString(Q[1]));
    }

    @Test
    public void testGetMatrixTransitionProbability() {
//        int n = 5;
//        double[][] Q = new double[n][n];
//        for (int i = 0; i < n; i++) {
//            Arrays.fill(Q[i],0, n, 1.0/n);
//        }
//
//        for (int i = 0; i < n; i++) {
//            System.out.println(Arrays.toString(Q[i]));
//        }
        List<Integer> R = new ArrayList<Integer>(4);
        R.add(1);
        R.add(1);
        R.add(1);
        R.add(1);

        PageRank pageRank = new PageRank("src/main/resources/",
                "data9.txt",R,0.85,0.1);
        double[][] matrixTransitionProbability = pageRank.getMatrixTransitionProbability();
        for (int i = 0; i < matrixTransitionProbability.length; i++) {
            for (int j = 0; j < matrixTransitionProbability.length; j++) {
                System.out.print(matrixTransitionProbability[i][j] + "  ");
            }
            System.out.println();
        }

    }

    @Test
    public void testPageRankStart() {
        List<Integer> R = new ArrayList<Integer>(4);
        R.add(1);
        R.add(1);
        R.add(1);
        R.add(1);

        PageRank pageRank = new PageRank("src/main/resources/",
                "data9.txt",R,0.85,0.1);
        System.out.println(pageRank.getR());
        double[][] array = pageRank.getMatrixTransitionProbability();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.println(array[i][j]);
            }
        }
    }

    @Test
    public void testDoubleAbs() {
        double a = -0.037500000000000006;
        double b = Math.abs(a);
        System.out.println("a = " + a + "  " + "b = " + b);
    }

    @Test
    public void testMatrixManipulation() throws Exception {
        double[][] a = new double[][]{{1,2,3},{3,2,1}};
        double[][] b = new double[][]{{1,1},{2,2},{3,3}};

        double[][] c = PageRankUtil.matrixManipulation(a,b);

        for (int i = 0; i < c.length; i++) {
            for (int j =0; j < c[i].length; j++) {
                System.out.print(c[i][j] + "   ");
            }
            System.out.println();
        }
    }
}

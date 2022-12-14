package com.xiaoxiao;

import com.xiaoxiao.knn.KNN;
import com.xiaoxiao.util.KNNUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class KNNTest {

    @Test
    public void testKNN() {
        List<List<String>> dataSet = KNNUtil.loadData("src/main/resources/", "data5.txt");
        List<String> list = new ArrayList<String>();
        list.add("易昌");
        list.add("1.74");
        KNN Knn = new KNN(dataSet, list,5);
        Knn.knn();
    }
}

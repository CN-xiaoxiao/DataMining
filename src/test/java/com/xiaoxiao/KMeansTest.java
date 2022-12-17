package com.xiaoxiao;

import com.xiaoxiao.kmeans.KMeans;
import com.xiaoxiao.kmeans.Point;
import com.xiaoxiao.util.KMeansUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class KMeansTest {

    @Test
    public void testInitCenter() {
        List<Point> dataSet = KMeansUtil.loadData("src/main/resources/" ,"data8.txt");
        int k = 3;

        KMeansUtil.initCenter(dataSet, k);
    }

    @Test
    public void testKMeans() {
        KMeans kMeans = new KMeans("src/main/resources/","data8.txt",3, 0.001);
        kMeans.kMeans();
    }
}

package com.xiaoxiao.util;

import com.xiaoxiao.kmeans.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KMeansUtil {

    public static List<Point> loadData(String path, String fileName) {
        File file = new File(path + fileName);
        BufferedReader reader = null;
        List<Point> result = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int count = 1;
            while ((tempString = reader.readLine()) != null) {
                String[] split = tempString.split(",");
                result.add(new Point().setName("P" + count).
                        setX(Double.parseDouble(split[0])).
                        setY(Double.parseDouble(split[1])).
                        setId(count));
                count++;
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    /**
     * 初始化簇
     * @param dataSet 数据集
     * @param k k个簇
     * @return 随机生成的k个簇
     */
    public static List<Point> initCenter(List<Point> dataSet, int k) {
        List<Point> center = new ArrayList<Point>();
        Random random = new Random();
        int[] randoms = new int[k];
        int dataLength = dataSet.size();
        int temp = random.nextInt(dataLength);
        randoms[0] = temp;

        for (int i = 1; i < k; i++) {
            boolean flag = false;
            temp = random.nextInt(dataLength);
            for (int j = 0; j <= i; j++) {
                if (randoms[j] == temp) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                randoms[i] = temp;
            }
        }

        for (int i = 0; i < k; i++) {
            center.add(dataSet.get(randoms[i]));
        }

        System.out.print("选取的中心点为：");
        for (int i = 0; i < k; i++) {
            System.out.print(center.get(i).getName() + " ");
        }
        System.out.println();
        return center;
    }

    /**
     * 将数据集中的数据放入该数据距离最近的簇中
     * @param dataSet 数据集
     * @param center 质心
     * @param k 簇的数目
     * @return 新的簇集合
     */
    public static List<List<Point>> setCluster(List<Point> dataSet, List<Point> center, int k) {
        List<List<Point>> cluster = new ArrayList<List<Point>>();
        for (int i = 0; i < k; i++) {
            List<Point> list = new ArrayList<>();
            cluster.add(list);
        }

        for (int i = 0; i < dataSet.size(); i++) {
            double[] distance = new double[k];
            for (int j = 0; j < k; j++) {
                distance[j] = calculateDistance(dataSet.get(i), center.get(j));
            }
            // 获得该点与质心最近的簇的下标
            int minDistanceIndex = getMinDistanceIndex(distance);

            cluster.get(minDistanceIndex).add(dataSet.get(i));

        }


        return cluster;
    }

    /**
     * 计算新的质心（簇心）
     * @param dataSet 数据集
     * @param clusters 簇集合
     * @param k k个簇
     * @return
     */
    public static List<Point> setNewCenter(List<Point> dataSet, List<List<Point>> clusters, int k) {
        List<Point> center = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            Point centerPoint = new Point();
            double x = 0.0;
            double y = 0.0;

            for (int j = 0; j < clusters.get(i).size(); j++) {
                x += clusters.get(i).get(j).getX();
                y += clusters.get(i).get(j).getY();
            }
            centerPoint.setId(i).
                    setName("簇心" + i).
                    setX(x/clusters.get(i).size()).
                    setY(y/clusters.get(i).size());
            center.add(centerPoint);
        }


        return center;
    }

    // TODO:计算平方误差总和
    /**
     * 计算准则函数E
     * @param dataSet 数据集
     * @param center 簇心
     * @param clusters 簇集合
     * @param k k个簇
     * @return  平方误差总和
     */
    public static double getE(List<Point> dataSet, List<Point> center, List<List<Point>> clusters, int k) {
        double result = 0.0;

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < clusters.get(i).size(); j++) {
                Point temp = clusters.get(i).get(j);
                double tempX = temp.getX();
                double tempY = temp.getY();
                double centerX = center.get(i).getX();
                double centerY = center.get(i).getY();

                result += Math.pow((tempX-centerX),2);
                result += Math.pow((tempY-centerY),2);
            }
        }


        return result;
    }

    /**
     *
     * @param newPoint 数据集中的点
     * @param center 簇心
     * @return 两者之间的距离
     */
    public static double calculateDistance(Point newPoint, Point center) {
        double num1 = Math.pow((newPoint.getX() - center.getX()), 2);
        double num2 = Math.pow((newPoint.getY() - center.getY()), 2);

        return Math.pow((num1+num2), 0.5);
    }

    /**
     * 获得最小距离的簇的下标
     * @param distance
     * @return
     * @throws Exception
     */
    public static int getMinDistanceIndex(double[] distance) {
        double temp = distance[0];
        int minDistanceIndex = 0;
        for (int i = 1; i < distance.length; i++) {
            if (distance[i] < temp) {
                temp = distance[i];
                minDistanceIndex = i;
            }
        }

        return minDistanceIndex;
    }
}

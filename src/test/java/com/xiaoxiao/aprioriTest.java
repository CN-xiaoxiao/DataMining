package com.xiaoxiao;

import com.xiaoxiao.apriori.Apriori;
import com.xiaoxiao.util.AprioriUtil;
import org.apache.commons.collections4.BidiMap;
import org.junit.Test;

import java.util.*;

public class aprioriTest {

    @Test
    public void testFileLoad() {
        List<String> arr = AprioriUtil.loadData("src/main/resources/data1.txt");
        for (String temp : arr) {
            System.out.println(temp);
        }
    }

    @Test
    public void testCattingArray() {
        List<List<String>> data = AprioriUtil.cattingArray(AprioriUtil.loadData("src/main/resources/data1.txt"));
        for (List<String> arr : data) {
            System.out.println(arr);
        }
    }

    /**
     * 这里偷懒了，一个方法测试多个
     */
    @Test
    public void testMakeIndex() {
        List<String> arr = AprioriUtil.loadData("src/main/resources/data1.txt");
        List<List<String>> data = AprioriUtil.cattingArray(arr);
        BidiMap<String, Integer> map = AprioriUtil.makeIndex(data);

//        for (String key : map.keySet()) {
//            System.out.println(key);
//        }
//        for (Integer value : map.values()) {
//            System.out.println(value);
//        }
        List<List<Integer>> dataSet = AprioriUtil.strToSum(data, map);
//        for (List<Integer> temp : dataSet) {
//            System.out.println(temp.toString());
//        }
//
//        List<List<String>> dataSet2 = AprioriUtil.sumToStr(dataSet, map);
//        for (List<String> temp : dataSet2) {
//            System.out.println(temp);
//        }
        Apriori apriori = new Apriori();
        System.out.println(apriori.oneFrequentSet(dataSet));
    }

    @Test
    public void testEquals() {
        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        list2.add(1);
        list2.add(2);

        System.out.println(list1.equals(list2));
    }

    @Test
    public void testMap() {
        Map<List<Integer>, Integer> map = new HashMap<>();
        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        list2.add(1);
        map.put(list1, 1);
        map.put(list2, 2);

        System.out.println(map.size());
        for (Map.Entry<List<Integer>, Integer> entry : map.entrySet()) {
            List<Integer> mapKey = entry.getKey();
            int mapValue = entry.getValue();
            System.out.println(mapKey + "：" + mapValue);
        }
        System.out.println("更新后......");
        map.put(list2, 3);
        for (Map.Entry<List<Integer>, Integer> entry : map.entrySet()) {
            List<Integer> mapKey = entry.getKey();
            int mapValue = entry.getValue();
            System.out.println(mapKey + "：" + mapValue);
        }
    }

    @Test
    public void testList() {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> test = null;
        list.add(1);
        list.add(2);
        System.out.println(list);
        test = list;
        System.out.println("-----------------");
        test.add(3);
        System.out.println(list);
    }

    @Test
    public void testContainsAll() {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();

        list.add(1);
        list.add(2);
        list.add(3);
        list2.add(1);

        System.out.println(list.contains(list2));
        System.out.println(list.containsAll(list2));
    }

    @Test
    public void testApriori() {
        // 1.读取数据，将数据集读取出来，并保存到一个List数组里
        List<String> strings = AprioriUtil.loadData("src/main/resources/data1.txt");
        // 2.将数据进行编码转换：汉字、英文--->阿拉伯数字
        List<List<String>> data = AprioriUtil.cattingArray(strings);
        BidiMap<String, Integer> strToSumMap = AprioriUtil.makeIndex(data);
        // 3.将原数组进行转换
        List<List<Integer>> dataSet = AprioriUtil.strToSum(data, strToSumMap);

        Apriori apriori = new Apriori(0.5, 0.5);
        // 4.生成频繁1项集
        Map<List<Integer>, Integer> oneFreTemp = apriori.oneFrequentSet(dataSet);

        // 记录总个数（交易数、）
        int count = dataSet.size();

        // map形式的频繁1项集
        Map<List<Integer>, Integer> oneSupportItemsSet = apriori.getSupportItemsSet(oneFreTemp, count);

        // 记录一下频繁1项集的个数，可能会围绕其展开
        int oneFreCount = oneSupportItemsSet.size();

        // list形式的频繁1项集
        List<List<Integer>> oneFre = AprioriUtil.setToList(oneSupportItemsSet);

        List<List<Integer>> Lk = oneFre;
        List<List<Integer>> allFreSet = new ArrayList<List<Integer>>();
        allFreSet.addAll(Lk);

        List<List<Integer>> CkAdd = apriori.lkToCk(Lk, 1);
        System.out.println("CkAdd: " + CkAdd);
        System.out.println("***********");
        System.out.println("Lk: " + Lk);
        System.out.println("***********");

        Map<List<Integer>, Integer> LkTempAdd = apriori.ckToLk(dataSet, CkAdd);
        Map<List<Integer>, Integer> LkAdd = apriori.getSupportItemsSet(LkTempAdd, count);

        Lk = AprioriUtil.setToList(LkAdd);
        System.out.println("LkAdd: " + Lk);
        System.out.println(Lk.size());
        System.out.println("************");
        CkAdd = apriori.lkToCk(Lk, 2);
        System.out.println("CkAdd: " + CkAdd);
        // 寄，没去重
        System.out.println(CkAdd.size());
        LkTempAdd = apriori.ckToLk(dataSet, CkAdd);
        LkAdd = apriori.getSupportItemsSet(LkTempAdd, count);
        Lk = AprioriUtil.setToList(LkAdd);
        System.out.println("LkAdd: " + Lk);
        System.out.println(Lk.size());
    }

    @Test
    public void testGetArr()
    {
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(4);
        list.add(5);

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        //子集的数量
        int n = 1 << list.size();
        System.out.println(n);
        List<Integer> temp = null;
        for(int i = 0; i < n; i ++)
        {
            temp = new ArrayList<>();
            int j = i;
            int index = list.size() - 1;
            //循环前检测j是否是000
            while(j > 0)
            {
                if((j&1) == 1)
                {
                    temp.add(list.get(index));
                }
                j = j >> 1;
                index --;
            }
            // ? 这排序简洁
            temp.sort(Comparator.comparing(Integer::intValue));
            if (temp.size() != 0 && temp.size() != list.size()) {
                result.add(temp);
            }
        }

        System.out.println(result);
    }

    @Test
    public void testDeleteLikeArr() {
        LinkedList<Integer> mAllList = new LinkedList<>();
        LinkedList<Integer> mSubList  = new LinkedList<>();
        mAllList.add(1);
        mAllList.add(3);
        mAllList.add(2);
        mAllList.add(4);
        System.out.println(mAllList);
        mSubList.add(3);

        // 随机打乱顺序
        Collections.shuffle(mSubList);
        System.out.println(mAllList);
        long before = System.currentTimeMillis();
        HashSet h1 = new HashSet(mAllList);
        HashSet h2 = new HashSet(mSubList);
        h1.removeAll(h2);
        mAllList.clear();
        mAllList.addAll(h1);
        System.out.println(h1);
        long now = System.currentTimeMillis();
        System.out.println(now-before);
    }

    @Test
    public void testSplitList() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(4);
        list.add(666);
        list.add(12);
        list.add(44);
        list.add(66);
        list.sort(Comparator.comparing(Integer::intValue));
        System.out.println(list);
        Map<List<Integer>, List<Integer>> listListMap = AprioriUtil.splitList(list);
        for (Map.Entry<List<Integer>, List<Integer>> entry : listListMap.entrySet()) {
            List<Integer> mapKey = entry.getKey();
            List<Integer> mapValue = entry.getValue();
            System.out.println(mapKey + "------->" + mapValue);
        }
    }
}

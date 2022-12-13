package com.xiaoxiao;

import com.xiaoxiao.em.EM;
import com.xiaoxiao.em.EMStart;
import com.xiaoxiao.util.EMUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class testEM {

    @Test
    public void testArray() {
        int[] array1 = new int[]{1,2,3};
        int[] array2 = new int[]{3,2,1};
        int[] array3 = new int[]{4,5,6};

        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));
        System.out.println(Arrays.toString(array3));

        System.out.println("*******************");
        array3 = array1;
        array3[0] = 9;
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));
        System.out.println(Arrays.toString(array3));
    }

    @Test
    public void testLoad() {
//        List<List<String>> lists = EMUtil.loadData("src/main/resources/", "data4.txt");
//        for (List<String> list : lists) {
//            System.out.println(list);
//        }
//        System.out.println(lists.get(0).get(1));

        int[][] data = EMUtil.loadData("src/main/resources/", "data4.txt");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + "  ");
            }
            System.out.println();
        }
    }

    @Test
    public void testEM() {

        EMStart emStart = new EMStart(new EM("src/main/resources/", "data4.txt", new double[]{0.9,0.2}, 0.01));

        emStart.startEM();
    }
}

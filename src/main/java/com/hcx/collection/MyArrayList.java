package com.hcx.collection;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Title: MyArrayList.java
 * @Package com.hcx.collection
 * @Description: (用一句话描述该文件做什么)
 * @Author: hongcaixia
 * @Date: 2024/5/14 10:25
 * @Version V1.0
 */
public class MyArrayList {

    private static void failFast(){
        ArrayList<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        for (Integer ele : list) {
            System.out.println(ele);
        }
        System.out.println(list);
    }

    private static void failSafe(){
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        for (Integer ele : list) {
            System.out.println(ele);
        }
        System.out.println(list);
    }

    public static void main(String[] args) {
        failFast();
    }


}

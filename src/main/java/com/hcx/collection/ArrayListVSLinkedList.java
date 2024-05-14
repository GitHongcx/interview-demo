package com.hcx.collection;

import org.openjdk.jol.info.ClassLayout;
import org.springframework.util.StopWatch;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.hcx.common.Utils.swap;

/**
 * @Title: ArrayListVSLinkedList.java
 * @Package com.hcx.collection
 * @Description: (用一句话描述该文件做什么)
 * @Author: hongcaixia
 * @Date: 2024/5/14 11:13
 * @Version V1.0
 */
public class ArrayListVSLinkedList {

    public static void main(String[] args) {
        int n = 1000;
        int insertIndex = n;
        for (int i = 0; i < 5; i++) {
            int[] array = randomArray(n);
            List<Integer> list1 = Arrays.stream(array).boxed().collect(Collectors.toList());
            LinkedList<Integer> list2 = new LinkedList<>(list1);

            //随机访问
            // randomAccess(list1, list2, n / 2); // ArrayList 快

            //头部插入
            //addFirst(list1,list2);  // linkedList快1倍

            //尾部插入
            //addLast(list1,list2); // 差不太多，但是ArrayList更快一些

            //中间插入
            // addMiddle(list1,list2,n/2);// ArrayList快了五六倍

            //linkedList的插入只有在头尾的插入效率高，中间的插入效率远不及ArrayList 因为中间的插入必须遍历找到才能进行插入。查找的过程很慢

            // 占用大小 linkedListSize占用空间更多 5倍
            arrayListSize((ArrayList<Integer>) list1);
            linkedListSize(list2);

        }
    }

    static void linkedListSize(LinkedList<Integer> list) {
        try {
            long size = 0;
            ClassLayout layout = ClassLayout.parseInstance(list);
//            System.out.println(layout.toPrintable());
            size += layout.instanceSize();
            Field firstField = LinkedList.class.getDeclaredField("first");
            firstField.setAccessible(true);
            Object first = firstField.get(list);
//            System.out.println(ClassLayout.parseInstance(first).toPrintable());
            long nodeSize = ClassLayout.parseInstance(first).instanceSize();
            size += (nodeSize * (list.size() + 2));
            long elementSize = ClassLayout.parseInstance(list.getFirst()).instanceSize();
            System.out.println("LinkedList size:[" + size + "],per Node size:[" + nodeSize + "],per Element size:[" + elementSize * list.size() + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void arrayListSize(ArrayList<Integer> list) {
        try {
            long size = 0;
            ClassLayout layout = ClassLayout.parseInstance(list);
//            System.out.println(layout.toPrintable());
            size += layout.instanceSize();
            Field elementDataField = ArrayList.class.getDeclaredField("elementData");
            elementDataField.setAccessible(true);
            Object elementData = elementDataField.get(list);
//            System.out.println(ClassLayout.parseInstance(elementData).toPrintable());
            size += ClassLayout.parseInstance(elementData).instanceSize();
            long elementSize = ClassLayout.parseInstance(list.get(0)).instanceSize();
            System.out.println("ArrayList size:[" + size + "],array length:[" + length(list) + "],per Element size:[" + elementSize * list.size() + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试随机访问能力
     * @param list1
     * @param list2
     * @param mid
     */
    static void randomAccess(List<Integer> list1, LinkedList<Integer> list2, int mid) {
        StopWatch sw = new StopWatch();
        sw.start("ArrayList");
        list1.get(mid);
        sw.stop();

        sw.start("LinkedList");
        list2.get(mid);
        sw.stop();

        System.out.println(sw.prettyPrint());
    }

    public static int[] randomArray(int n) {
        int lastVal = 1;
        Random r = new Random();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            int v = lastVal + Math.max(r.nextInt(10), 1);
            array[i] = v;
            lastVal = v;
        }
        shuffle(array);
        return array;
    }

    public static void shuffle(int[] array) {
        Random rnd = new Random();
        int size = array.length;
        for (int i = size; i > 1; i--) {
            swap(array, i - 1, rnd.nextInt(i));
        }
    }

    public static int length(ArrayList<Integer> list) {
        try {
            Field field = ArrayList.class.getDeclaredField("elementData");
            field.setAccessible(true);
            return ((Object[]) field.get(list)).length;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 头部插入
     * @param list1
     * @param list2
     */
    private static void addFirst(List<Integer> list1, LinkedList<Integer> list2) {
        StopWatch sw = new StopWatch();
        sw.start("ArrayList");
        list1.add(0, 100);
        sw.stop();

        sw.start("LinkedList");
        list2.addFirst(100);
        sw.stop();

        System.out.println(sw.prettyPrint());
    }

    /**
     * 中间插入
     * @param list1
     * @param list2
     * @param mid
     */
    private static void addMiddle(List<Integer> list1, LinkedList<Integer> list2, int mid) {
        StopWatch sw = new StopWatch();
        sw.start("ArrayList");
        list1.add(mid, 100);
        sw.stop();

        sw.start("LinkedList");
        list2.add(mid, 100);
        sw.stop();

        System.out.println(sw.prettyPrint());
    }

    /**
     * 尾部插入
     * @param list1
     * @param list2
     */
    private static void addLast(List<Integer> list1, LinkedList<Integer> list2) {
        StopWatch sw = new StopWatch();
        sw.start("ArrayList");
        list1.add(100);
        sw.stop();

        sw.start("LinkedList");
        list2.add(100);
        sw.stop();

        System.out.println(sw.prettyPrint());
    }

}

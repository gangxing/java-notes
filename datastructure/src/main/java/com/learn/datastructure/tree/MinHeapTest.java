package com.learn.datastructure.tree;

import java.util.Iterator;

/**
 * @ClassName MinHeapTest
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/15 16:33
 */
public class MinHeapTest {

    public static void main(String[] args) {
        MinHeap heap = new MinHeap();
        for (int i = 10; i > 0; i--) {
            heap.add(i);
        }
        heap.print();

        heap.remove(1);

        heap.print();

        Iterator<Integer> iterator = heap.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.err.println("iterator:" + next);
        }
    }
}

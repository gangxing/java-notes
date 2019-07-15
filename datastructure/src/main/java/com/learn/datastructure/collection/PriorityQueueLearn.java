package com.learn.datastructure.collection;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * @ClassName PriorityQueueLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/15 09:59
 */
public class PriorityQueueLearn {

    public static void main(String[] args) {
        PriorityQueue<Integer> queue=new PriorityQueue<>();
        for (int i=10;i>0;i--) {
            queue.add(i);
        }
        queue.remove(1  );
        Iterator<Integer> iterator=queue.iterator();
        while (iterator.hasNext()){
            Integer next=iterator.next();
            System.err.println("next:"+next);
        }
    }
}

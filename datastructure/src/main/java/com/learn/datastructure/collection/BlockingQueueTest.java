package com.learn.datastructure.collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BlockingQueueTest
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 14/11/2018 22:31
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        BlockingQueue<Integer> queue=new LinkedBlockingQueue<>(10);
        try {

            queue.add(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

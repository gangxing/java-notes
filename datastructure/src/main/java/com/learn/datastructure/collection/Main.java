package com.learn.datastructure.collection;

import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName Main
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 11/11/2018 21:19
 */
public class Main {
    public static void main(String[] args) {

    }




    private static class ConcurrentLinkedList<E> {

        private LinkedList<E> list;

        private ReadWriteLock lock;

        public ConcurrentLinkedList(LinkedList<E> list) {
            this.list = list;
            this.lock=new ReentrantReadWriteLock();
        }


    }
}

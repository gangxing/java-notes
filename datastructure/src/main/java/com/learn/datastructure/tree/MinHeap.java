package com.learn.datastructure.tree;

import java.util.Iterator;

/**
 * @ClassName MinHeap
 * @Description 最小堆 利用数组实现 实现简易的增删改查 暂不考虑扩容机制 允许元素重复
 * @Author xgangzai@gmail.com
 * @Date 2019/7/15 14:22
 */
public class MinHeap {

    private Integer[] queue;

    //节点个数
    private int size;

    public MinHeap() {
        this(16);
    }

    public MinHeap(int capacity){
        this.queue=new Integer[capacity];
        this.size=0;
    }

    public void add(int value) {
        //放入堆底
        //
        int k = size;
        queue[k] = value;
        size++;
        if (k == 0) {
            return;
        }
        //向上翻
        siftUp(k, value);
    }

    private void siftUp(int k, int value) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;//为什么是无符号右移
            if (queue[parent] == null) {
                throw new IllegalArgumentException("parent is null when add " + value);
            }
            if (value < queue[parent]) {
                //对换
                queue[k] = queue[parent];
                queue[parent] = value;
                //继续往上找
                k = parent;
            }else {
                break;
            }

        }
    }


    private void siftDown(int k) {
        int half = size >>> 1;
        while (k < half) {
            //跟子节点比较

            int least= k * 2 + 1;//先假设较小节点是左节点
            int c=queue[least];
            int right=least+1;
            if (right< size && queue[right]<c){
                c=queue[right];
                least=right;
            }

            if (queue[k]<c){
                break;
            }

            //交换
            queue[least]=queue[k];
            queue[k]=c;
            k=least;

        }
    }

    /**
     *
     * @param v value
     * @return true - exists ;false -not exists
     */
    public boolean remove(int v) {
        int p = indexOf(v);
        if (p == -1) {
            return false;
        }
        removeAt(p);
        return true;
    }

    private int indexOf(Integer o) {
        if (o != null) {
            for (int i = 0; i < size; i++)
                if (o.equals(queue[i]))
                    return i;
        }
        return -1;
    }

    /**
     *
     * @param i index
     * @return value
     */
    public int removeAt(int i) {

        int s = --size;//先得到末尾元素下标，同时将size减1

        int value=queue[i];
        int v = queue[s];
        queue[s] = null;
        if (s == i) {//删除的刚好是末尾这个元素
            return value;
        }

        //将最后一个元素放置在待删除的位置
        queue[i] = v;

        //下调操作
        siftDown(i);

        return value;

    }


    public void print() {
        System.err.println();
        for (Integer i : queue) {
            if (i != null) {
                System.err.println(i + " ");
            }
        }
        System.err.println();
    }

    public static void main(String[] args) {
        System.err.println(1 >>> 1);
    }


    public Iterator<Integer> iterator() {
        return new Itr();
    }


    class Itr implements Iterator<Integer> {

        private int cursor;

        public Itr() {
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public Integer next() {
            int c = cursor++;

            Integer value = queue[c];
            if (value == null) {
                throw new IllegalArgumentException("已经没有啦，不能再取啦");
            }
            return value;
        }
    }

}

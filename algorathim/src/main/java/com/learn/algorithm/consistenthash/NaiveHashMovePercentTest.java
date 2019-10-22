package com.learn.algorithm.consistenthash;

import java.util.*;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/22 19:19
 */
public class NaiveHashMovePercentTest {

    public static void main(String[] args) {
        for (int i=3;i<10000;i++){
            double d=i*100D/(i+1);
            System.err.println(i+"="+d);
        }
    }

    //传统hash取余 当新增一个节点后需要移动的数据占比
    public static void main1(String[] args) {
        int node = 10000;
        Set<Item> items = new HashSet<>(1000000);
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            items.add(new Item(random.nextInt(Integer.MAX_VALUE), node));
        }
        int sum = 0;
        int moved = 0;
        Iterator<Item> iterator = items.iterator();
        Item item;
        while (iterator.hasNext()) {
            item = iterator.next();
            sum++;
            //没有
            if (item.increaseNode()) {
                moved++;
            }
        }

        double percent=moved*100D/sum;
        System.err.println("sum="+sum+";moved="+moved+";percent="+percent);

    }


    private static class Item {
        int hash;

        int node;

        int index;

        int newIndex = -1;

        public Item(int hash, int node) {
            this.hash = hash;
            this.node = node;
            this.index = hash % node;
        }

        public boolean increaseNode() {
            int newNode = this.node + 1;
            this.newIndex = hash % newNode;
            return newIndex != this.index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Item)) return false;
            Item item = (Item) o;
            return hash == item.hash;
        }

        @Override
        public int hashCode() {
            return Objects.hash(hash);
        }
    }
}

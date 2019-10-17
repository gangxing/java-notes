package com.learn.datastructure.tree;

/**
 * @ClassName BTree
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/16 09:32
 */
public class BTree {

    /**
     * 阶数
     */
    private int m;

    /**
     * 根节点
     */
    private Node root;

    public BTree(int m) {
        this.m = m;
    }

    //元素，key - value
    private class Item {
        int key;

        String value;

        public Item(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    // 节点
    private class Node {
        Item[] items;

        int count;

        Node parent;

        Node left;

        Node right;

        public Node(Item item) {
            items = new Item[m];
            items[0] = item;
            count = 1;
        }

        public void add(Item item) {

        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

package com.learn.datastructure.tree;

import java.util.Iterator;

/**
 * @ClassName BinarySearchTree
 * @Description <href>https://juejin.im/post/5ad56de7f265da2391489be3</href>
 * @Author xgangzai@gmail.com
 * @Date 2019/7/11 01:02
 */
public class BinarySearchTree<V> {

    private Node<V> root;

    public BinarySearchTree(Node<V> root) {
        this.root = root;
    }

    public BinarySearchTree() {
        this(null);
    }


    /**
     * 返回当前节点的父节点
     *
     * @param key
     * @param value
     * @return 如果插入的节点为空节点，则返回null
     */
    public Node<V> add(int key, V value) {
        Node<V> node = root;

        if (node == null) {
            node = new Node<>(key, value);
            root = node;
            return null;
        }
        //因为没有重复的节点 所以遇到相等的节点则负载value
        //判断key跟root节点是否相等
        if (key == node.key) {
            return null;
        }

        Node<V> parent;
        while (true) {
            parent = node;
            if (key == node.key) {
                node.value = value;
                return null;
            } else if (key < node.key) {
                node = parent.left;
            } else {
                node = parent.right;
            }
            if (node == null) {
                node = new Node<>(key, value);
                if (key < parent.key) {
                    parent.left = node;
                } else {
                    parent.right = node;
                }
                break;
            }

        }

        return parent;
        //向下查找
        //如果key相当覆盖value么？？？


    }


//    private void insertLeft(Node<V> parent,int key,V value)

    public Node search(int key) {

        return null;
    }

    //删除 深度优先 广度优先
    public void remove(int key) {

    }

    public Iterator<Integer> iterator() {
        return new TreeIterator();
    }

    //遍历
    class TreeIterator implements Iterator<Integer> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Integer next() {
            return null;
        }
    }

    static class Node<V> {
        int key;

        V value;

        Node<V> left;

        Node<V> right;

        public Node(int key, V value, Node<V> left, Node<V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(int key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}

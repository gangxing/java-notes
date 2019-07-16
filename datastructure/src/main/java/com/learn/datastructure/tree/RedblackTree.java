package com.learn.datastructure.tree;

import java.util.Iterator;

/**
 * @ClassName RedblackTree
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/16 09:33
 */
public class RedblackTree<V> extends AbstractBinaryTree<V> {


    @Override
    public void add(int key, V value) {

    }

    @Override
    public void remove(int key) {

    }

    @Override
    public Iterator<Integer> iterator() {
        return null;
    }


    //获取祖父节点
    private Node<V> getGrandparent(Node<V> node) {
        if (node == null) {
            return null;
        }
        Node<V> parent = node.parent;
        if (parent == null) {
            return null;
        }
        return parent.parent;
    }

    //获取叔父节点
    private Node<V> getUncle(Node<V> node) {
        Node<V> grandparent = getGrandparent(node);
        if (grandparent == null) {
            return null;
        }
        return node.parent == grandparent.left ? grandparent.right : grandparent.left;
    }

    static class Node<V> {
        int key;

        V value;

        boolean isRed = true;//新增的节点都是红节点

        Node<V> parent;

        Node<V> left;

        Node<V> right;


        public Node(int key, V value) {
            this(key, value, null);
        }

        public Node(int key, V value, Node<V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }

        public boolean isBlack() {
            return !isRed();
        }

        public boolean isRed() {
            return isRed;
        }

        public void setBlack(){
            this.isRed=false;
        }

        public void setRed(){
            this.isRed=true;
        }


    }
}

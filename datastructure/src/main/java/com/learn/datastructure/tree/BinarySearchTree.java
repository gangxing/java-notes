package com.learn.datastructure.tree;

import java.util.Iterator;

/**
 * @ClassName BinarySearchTree
 * @Description <href>https://juejin.im/post/5ad56de7f265da2391489be3</href>
 * @Author xgangzai@gmail.com
 * @Date 2019/7/11 01:02
 */
public class BinarySearchTree {

    private Node root;

    //插入
    public void add(int value) {

    }

    public Node search(int value){

        return null;
    }

    //删除
    public void remove(int value) {

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

    static class Node {
        int value;

        Node left;

        Node right;

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(int value) {
            this(value,null,null);
        }
    }
}

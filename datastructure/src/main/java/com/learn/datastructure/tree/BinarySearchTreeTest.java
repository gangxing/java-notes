package com.learn.datastructure.tree;

import java.util.Iterator;

/**
 * @ClassName BinarySearchTreeTest
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/11 01:07
 */
public class BinarySearchTreeTest {


    public static void main(String[] args) {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        int[] keys = {11, 8, 5, 10, 16, 18};
        for (int key : keys) {
            BinarySearchTree.Node<String> parent = tree.add(key, "value" + key);
            System.err.println(key + " find parent " + (parent == null ? "null" : parent));
        }

        Iterator<Integer> iterator = tree.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.err.println(next);
        }


    }

}

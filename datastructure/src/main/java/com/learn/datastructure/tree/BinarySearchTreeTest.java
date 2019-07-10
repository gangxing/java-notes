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
        BinarySearchTree tree = new BinarySearchTree();
        tree.add(9);
        tree.add(10);
        tree.add(5);

        Iterator<Integer> iterator = tree.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.err.println(next);
        }


    }

}

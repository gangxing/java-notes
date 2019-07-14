package com.learn.datastructure.tree;

import java.util.Iterator;

/**
 * @ClassName BinarySearchTreeTest
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/11 01:07
 */
public class BinaryTreeTest {


    public static void main1(String[] args) {
        BinaryTree<String> tree = new BinarySearchTree<>();
        int[] keys = {11, 8, 5, 10, 16, 18,11};
//        int[] keys = {11};
//        int[] keys = {1,2,3,4,5,6,7, 8};
        for (int key : keys) {
            tree.add(key, "value" + key);
//            System.err.println(key + " find parent " + (parent == null ? "null" : parent));
        }

        String value=tree.search(8);
//        System.err.println(value);

        tree.print();

        System.err.println();
        tree.print();
        Iterator<Integer> iterator = tree.iterator();
        System.err.println("iterator");
        while (iterator.hasNext()) {
            Integer next= iterator.next();
            System.err.print(next+" ");
        }


    }

    public static void main(String[] args) {
        BinaryTree<String> tree = new AVLBinarySearchTree<>();
        int[] keys = {5,10,8};
        for (int key : keys) {
            tree.add(key, "value" + key);
        }


        tree.print();

        System.err.println();
        tree.print();
        Iterator<Integer> iterator = tree.iterator();
        System.err.println("iterator");
        while (iterator.hasNext()) {
            Integer next= iterator.next();
            System.err.print(next+" ");
        }


    }


}

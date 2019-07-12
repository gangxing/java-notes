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
        int[] keys = {11, 8, 5, 10, 16, 18,9,7,11};
//        int[] keys = {11};
//        int[] keys = {1,2,3,4,5,6,7, 8};
        for (int key : keys) {
            BinarySearchTree.Node<String> parent = tree.add(key, "value" + key);
//            System.err.println(key + " find parent " + (parent == null ? "null" : parent));
        }

        String value=tree.search1(8);
//        System.err.println(value);

        tree.print();

        tree.remove(11);
        System.err.println();
        tree.print();
//        Iterator<Integer> iterator = tree.iterator();
//        while (iterator.hasNext()) {
//            Integer next= iterator.next();
//            System.err.print(next+" ");
//        }


    }

}

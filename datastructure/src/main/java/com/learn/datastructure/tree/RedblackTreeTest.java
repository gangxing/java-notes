package com.learn.datastructure.tree;

import java.util.Iterator;

/**
 * @ClassName RedblackTreeTest
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/16 18:08
 */
public class RedblackTreeTest {

    public static void main(String[] args) {
        RedblackTree<String> tree=new RedblackTree<>();

        int[] keys = {10,8,5};
        for (int key : keys) {
            tree.add(key, "value" + key);
        }


        tree.print();

        System.err.println();
        tree.print();
        System.err.println();
        @SuppressWarnings("unchecked")
        Iterator<RedblackTree.Node<String>> iterator = tree.iterator();
        while (iterator.hasNext()) {
            RedblackTree.Node<String> next= iterator.next();
            System.err.print(next);
            System.err.println();
        }

    }
}

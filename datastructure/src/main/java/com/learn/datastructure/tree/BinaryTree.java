package com.learn.datastructure.tree;

import java.util.Iterator;

/**
 * @ClassName BinaryTree
 * @Description 二叉树
 * @Author xgangzai@gmail.com
 * @Date 2019/7/11 01:02
 */
public interface BinaryTree<V> {


  /**
   * 返回当前节点的父节点
   *
   * @param key
   * @param value
   * @return 如果插入的节点为空节点，则返回null
   */
  void add(int key, V value);


  V search(int key);


  void print();


  //删除
  void remove(int key);


  Iterator<Integer> iterator();


}

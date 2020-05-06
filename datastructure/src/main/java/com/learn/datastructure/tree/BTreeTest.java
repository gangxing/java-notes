package com.learn.datastructure.tree;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/17 15:51
 */
@Slf4j
public class BTreeTest {

  public static void main(String[] args) {
    int m = 5;
    BTree bTree = new BTree(m);
    int key = 4;
    String value = bTree.search(key);
    log.info(key + "->" + value);
//        Iterator<Integer> iterator = bTree.iterator();
//        while (iterator.hasNext()) {
//            log.info(iterator.next() + "");
//        }

  }
}

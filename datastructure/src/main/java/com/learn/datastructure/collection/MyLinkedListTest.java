package com.learn.datastructure.collection;

import java.util.Iterator;

/**
 * @ClassName MyLinkedListTest
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/24 16:25
 */
public class MyLinkedListTest {

  public static void main(String[] args) {
    MyLinkedList list = new MyLinkedList();
    for (int i = 0; i < 10; i++) {
      list.add(i);
    }

    Iterator<MyLinkedList.Node> iterator = list.iterator();
    while (iterator.hasNext()) {
      MyLinkedList.Node next = iterator.next();
      System.err.println(next);
    }

    System.err.println("reverse print");
    reversePrint(list);
  }

  private static void reversePrint(MyLinkedList list) {
    MyLinkedList.Node head = list.getHead();
    reversePrint(head);

  }

  //递归倒序输出
  private static void reversePrint(MyLinkedList.Node node) {
    if (node.getNext() != null) {
      reversePrint(node.getNext());
    }
    System.err.println(node);

  }
}

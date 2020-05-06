package com.learn.datastructure.collection;

import java.util.Iterator;

/**
 * @ClassName MyLinkedList
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/5 11:59
 */
public class MyLinkedList {

  private Node head;

  public static void main(String[] args) {
    MyLinkedList list = new MyLinkedList();
    for (int i = 0; i < 9; i++) {
      list.add(i);
    }

    System.err.println(list);

    list.reverse1();

    System.err.println(list);


  }

  public void add(int value) {
    Node node = new Node(value);
    link(node);
  }

  public void reverse() {
    Node node = this.head;
    reverse(null, node);
  }


  private void reverse(Node reversed, Node node) {
    if (node == null) {
      return;
    }

    Node next = node.next;
    node.next = reversed;
    this.head = node;
    reverse(node, next);

  }

  //先用快慢指针，定位到最后一个节点和中间节点
  //类似于数组翻转，头尾交换，直至中间节点
  public void reverse1() {
    if (head == null || head.next == null) {
      return;
    }
    Node prev = head;
    Node cur = head.next;
    Node temp = head.next.next;
    head.next = null;

    while (temp != null) {
      temp = cur.next;
      cur.next = prev;
      prev = cur;
      cur = temp;
    }
//        head.next=null;
    head = prev;


  }

  public Node getHead() {
    return head;
  }

  private Node lastNode() {
    Node n = this.head;
    if (n == null) {
      return null;
    }
    while (n.next != null) {
      n = n.next;
    }
    return n;
  }

  private void link(Node node) {
    Node last = lastNode();
    if (last == null) {
      last = node;
      this.head = last;
    } else {
      last.next = node;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    Iterator<Node> itr = iterator();
    while (itr.hasNext()) {
      sb.append(itr.next()).append(",");
    }
    sb.append("]");
    return sb.toString();
  }

  public Iterator<Node> iterator() {
    return new Itr();
  }

  public class Node {

    private int value;


    private Node next;

    public Node(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public Node getNext() {
      return next;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  class Itr implements Iterator<Node> {

    Node next;

    public Itr() {
      this.next = head;
    }

    @Override
    public boolean hasNext() {
      return next != null;
    }

    @Override
    public Node next() {
      Node n = next;
      if (n == null) {
        throw new IllegalArgumentException("已经没有啦");
      }
      next = n.next;
      return n;
    }
  }
}

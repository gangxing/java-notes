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

    private Node tail ;//要不要尾部引用 先要着 实现后再看JDK怎么实现的

    public class Node {

        private int value;


        private Node next;

        public int getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(int value) {
            this(value, null);
        }

        @Override
        public String toString() {
            return value+"";
        }
    }


    public void add(int value) {

        Node node=new Node(value);
        if (head==null || tail==null){
            head=tail=node;
        }else {
            link(node,tail);
        }

    }

    public Node getHead(){
        return head;
    }

    private void link(Node node,Node prev){
        prev.next=node;
        tail=node;
    }

    public Iterator<Node> iterator(){
        return new Itr();
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
            Node n=next;
            if (n==null){
                throw new IllegalArgumentException("已经没有啦");
            }
            next=n.next;
            return n;
        }
    }
}

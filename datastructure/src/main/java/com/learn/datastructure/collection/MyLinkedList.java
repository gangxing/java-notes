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

    private Node tail ;

    private class Node {

        private int value;


        private Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(int value) {
            this(value, null);
        }
    }


    public void add(int value) {

        Node node=new Node(value);
        if (head==null || tail==null){
            head=tail=node;
        }else {
            link(value,tail);
        }

    }

    private void link(int value,Node prev){
        Node node=new Node(value);
        prev.next=node;
        tail=node;
    }

    public Iterator<Node> iterator(){
        return new Itr();
    }

    class Itr implements Iterator<Node> {

        Node next;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Node next() {
            return next;
        }
    }
}

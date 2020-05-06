package com.learn.datastructure.collection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * @ClassName LinkedListLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/9 09:34
 */

/**
 * TODO 两个常见问题题 1.判断链表是否成环了，以及环链入口节点 思路：快慢两个哨兵
 * <p>
 * 2.快速翻转链表
 * <p>
 * https://juejin.im/post/5aa299c1518825557b4c5806
 * <p>
 * 原来就一个链表还可以问这么多 真实服了
 */
public class LinkedListLearn {

  static LinkedList<Student> list = new LinkedList<>();

  public static void main(String[] args) {

    for (int i = 0; i < 9; i++) {
      list.add(Student.random(i));
//            if (i%5==0){
//                try {
//                    Thread.sleep(80);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//            }
    }

    print();
    Iterator<Student> iterator = list.listIterator();
    while (iterator.hasNext()) {
      Student next = iterator.next();
      System.err.println(next);
    }
  }

  private static void print() {
    for (Student s : list) {
      System.err.println(s);
    }
  }

  //逆序输出
  private static void reversePrint() {

    for (Student s : list) {
      System.err.println(s);
    }
  }


  static class Student {

    private int age;
    private String name;


    public Student(int age, String name) {
      this.age = age;
      this.name = name;
    }

    public Student() {
    }

    public static Student random() {
      Random random = new Random();
      int age = random.nextInt(20);
      return random(age);
    }

    public static Student random(int init) {
      String name = "Name" + init;

      return new Student(init, name);
    }

    @Override
    public String toString() {
      return "Student{" +
          "age=" + age +
          ", name='" + name + '\'' +
          '}';
    }
  }

}

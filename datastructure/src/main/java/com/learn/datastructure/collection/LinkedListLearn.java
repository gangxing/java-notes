package com.learn.datastructure.collection;

import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/**
 * @ClassName LinkedListLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/9 09:34
 */
public class LinkedListLearn {

    static LinkedList<Student> list = new LinkedList<>();

    public static void main(String[] args) {

        for (int i=0;i<999999;i++) {
            list.add(Student.random());
            if (i%5==0){
                try {
                    Thread.sleep(80);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        print();
    }

    private static void print() {
        for (Student s : list) {
            System.err.println(s);
        }

        System.err.println(list);
    }


    static class Student{
        private int age;
        private String name;


        public Student(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public Student() {
        }

        public static Student random(){
            Random random=new Random();
            int age=random.nextInt(20);
            String name="Name"+random.nextInt(Integer.MAX_VALUE);

            return new Student(age,name);
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

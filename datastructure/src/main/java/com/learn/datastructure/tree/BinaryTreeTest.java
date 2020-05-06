package com.learn.datastructure.tree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

/**
 * @ClassName BinarySearchTreeTest
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/11 01:07
 */
public class BinaryTreeTest {


  static String fileName = "/Users/xgang/binaryTree";

  public static void main1(String[] args) {
    BinaryTree<String> tree = new BinarySearchTree<>();
    int[] keys = {11, 8, 5, 10, 16, 18, 11};
//        int[] keys = {11};
//        int[] keys = {1,2,3,4,5,6,7, 8};
    for (int key : keys) {
      tree.add(key, "value" + key);
//            System.err.println(key + " find parent " + (parent == null ? "null" : parent));
    }

    String value = tree.search(8);
//        System.err.println(value);

    tree.print();

    System.err.println();
    tree.print();
    Iterator<Integer> iterator = tree.iterator();
    System.err.println("iterator");
    while (iterator.hasNext()) {
      Integer next = iterator.next();
      System.err.print(next + " ");
    }


  }

  public static void main(String[] args) {
    AVLBinarySearchTree tree = new AVLBinarySearchTree();
    int[] keys = {5, 10, 8};
    for (int key : keys) {
      tree.add(key, "value" + key);
    }

    tree.print();

    System.err.println();
    tree.print();
    Iterator<Integer> iterator = tree.iterator();
    System.err.println("iterator");
    while (iterator.hasNext()) {
      Integer next = iterator.next();
      System.err.print(next + " ");
    }

    //序列化
    saveObjToFile(tree);

    //反序列化
    AVLBinarySearchTree tree1 = getObjFromFile();
    if (tree1 != null) {
      System.err.println("deserializable print");
      tree1.print();
    }


  }

  public static void saveObjToFile(BinaryTree p) {
    try {
      //写对象流的对象
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));

      oos.writeObject(p);                 //将Person对象p写入到oos中

      oos.close();                        //关闭文件流
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static AVLBinarySearchTree getObjFromFile() {
    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));

      AVLBinarySearchTree person = (AVLBinarySearchTree) ois.readObject();              //读出对象

      return person;                                       //返回对象
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }


}

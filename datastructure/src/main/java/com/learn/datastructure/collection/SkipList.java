package com.learn.datastructure.collection;

import org.apache.commons.lang3.RandomUtils;

/**
 * @Description 等效平衡二叉树，支持高效查询，核心思想，冗余部分数据，达到已空间换时间的目的
 * @see <href>https://www.jianshu.com/p/8ac45fd01548</href>
 * @Author xgangzai
 * @Date 2019/10/17 18:30
 */
public class SkipList<V> {

  private Node<V> head;

  private Node<V> tail;

  /**
   * 表长度（元素个数）
   */
  private int size;

  /**
   * 高度（层数）
   */
  private int height;

  /**
   * 最大高度
   */
  private int maxHeight;

  public SkipList(int maxHeight) {
    this.maxHeight = maxHeight;
  }

  /**
   * 新增
   *
   * @param value
   * @return return previous value matched the key,  otherwise return null.
   */
  public V add(int key, V value) {
    Node<V> n = getNode(key);
    V old = null;
    //说明新节点要成为head
    if (n == null) {
      n = new Node<>(key, value);
      Node oldHead = this.head;
      this.head = n;
      this.tail = n;
      this.size++;
      return null;
      //已存在，直接替换
    } else if (n.key == key) {
      old = n.value;
      //遍历找到对应的位置
      while (n.up != null) {
        n.value = value;
        n = n.up;
      }

    } else {
      //找了前置节点
      Node<V> newNode = new Node<>(key, value);
      Node<V> oldRight = n.right;
      n.right = newNode;
      newNode.left = n;
      newNode.right = oldRight;
      if (oldRight != null) {
        oldRight.left = newNode;
      }

      //处理随机高度
      int height = RandomUtils.nextInt(1, maxHeight);
      if (height > 1) {
        //逐层往上加
        Node<V> down = newNode;

        for (int i = 1; i < height; i++) {
          down = addUp(down);
        }
      }
    }

    if (old != null) {
      size++;
    }
    return old;
  }

  private Node<V> addUp(Node<V> down) {
    Node<V> up = new Node<>(down.key, down.value);
    up.down = down;
    down.up = up;
    //找左节点
    Node<V> left = down.left;
    while (true) {
      if (left == null || left.up != null) {
        break;
      } else {
        left = left.left;
      }
    }
    if (left != null) {

    }

    //找右节点

    return up;
  }

  /**
   * remove
   *
   * @param key
   * @return return previous value matched the key,  otherwise return null.
   */
  public V remove(int key) {
    Node<V> node = getNode(key);
    if (node != null && node.key == key) {
      size--;
      //删除节点
      remove(node);
      return node.value;
    }
    return null;
  }

  private void remove(Node<V> node) {
    if (node.left != null) {
      node.left.right = node.right;
    }
    if (node.right != null) {
      node.right.left = node.left;
    }
    if (node.up != null) {
      node.up.down = null;
      //递归处理上一层
      remove(node.up);
    }
  }

  /**
   * get
   *
   * @param key
   * @return
   */
  public V get(int key) {
    Node<V> n = getNode(key);
    if (n != null && n.key == key) {
      return n.value;
    }
    return null;
  }

  /**
   * 返回key<=key的节点
   *
   * @param key
   * @return
   */
  private Node<V> getNode(int key) {
    //从head节点开始
    Node<V> n = this.head;
    if (n == null) {
      return null;
    }
    return getDownNode(n, key);
  }

  private Node<V> getDownNode(Node<V> node, int key) {
    //先在当前层找
    Node<V> r = getRightNode(node, key);
    if (r.down != null) {
      //去下一层找（即便是匹配到了，也继续去下一层）
      return getDownNode(node.down, key);
    }
    return r;
  }

  //向右找，直到匹配或者到达链尾
  private Node<V> getRightNode(Node<V> node, int key) {
    while (node.key < key && node.right != null && node.right.key <= key) {
      node = node.right;
    }
    return node;
  }


  public String format() {
    //逐层遍历
    StringBuilder sb = new StringBuilder();
    Node<V> h = this.head;
    Node<V> d = h;
    while (d != null) {
      Node<V> r = d;
      while (r != null) {
        sb.append(r.key).append("->");
        r = r.right;
      }
      sb.append("\n");
      d = d.down;
    }

    return sb.toString();
  }

  private static class Node<V> {

    int key;

    V value;

    private Node<V> up;

    private Node<V> down;

    private Node<V> left;

    private Node<V> right;

    public Node(int key, V value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public String toString() {
      return key + "-" + value;
    }
  }


  public static void main(String[] args) {

    SkipList<Integer> manual = manual();
    System.err.println(manual.format());
    System.err.println("\n \n\n\n");

    Node node = manual.getNode(4);
    System.err.println(node.toString());

    for (int i = 0; i < 100; i++) {
      System.err.println(RandomUtils.nextInt(0, 64));
    }
//    SkipList<Integer> add = add();
//    System.err.println(add.format());

  }

  private static SkipList<Integer> add() {
    SkipList<Integer> skipList = new SkipList<>(64);
    for (int i = 1; i < 8; i++) {
      skipList.add(i, i);
    }
    return skipList;
  }

  private static SkipList<Integer> manual() {
    SkipList<Integer> skipList = new SkipList<>(64);

    Node<Integer> n11 = new Node<>(1, 1);
    skipList.head = n11;
    Node<Integer> n15 = new Node<>(5, 5);
    n11.right = n15;
    Node<Integer> n21 = new Node<>(1, 1);
    n11.down = n21;
    n21.up = n11;

    Node<Integer> n23 = new Node<>(3, 3);
    n21.right = n23;
    Node<Integer> n25 = new Node<>(5, 5);
    n23.right = n25;
    n15.down = n25;
    n25.up = n15;
    n25.left = n23;

    Node<Integer> n31 = new Node<>(1, 1);
    n21.down = n31;
    n31.up = n21;
    Node<Integer> n32 = new Node<>(2, 2);
    n31.right = n32;
    Node<Integer> n33 = new Node<>(3, 3);
    n32.right = n33;
    Node<Integer> n34 = new Node<>(4, 4);
    n33.right = n34;
    Node<Integer> n35 = new Node<>(5, 5);
    n34.right = n35;
    Node<Integer> n36 = new Node<>(6, 6);
    n35.right = n36;
    Node<Integer> n37 = new Node<>(7, 7);
    n36.right = n37;
    return skipList;
  }


}

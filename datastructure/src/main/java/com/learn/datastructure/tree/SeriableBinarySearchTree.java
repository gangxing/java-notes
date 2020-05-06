package com.learn.datastructure.tree;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName BinarySearchTree
 * @Description 实现自定义序列化和反序列化 根据定义，没有重复的key 所以直接存数据 不保留节点间的关系 反序列化时，重新构造一棵树，一次加入每个节点 序列化
 * 反序列化一般来讲都是框架，不跟具体的对象类型绑定，所以在反序列化时基本是通过反射来构造 实例，这个等后面专门找一个序列化和反序列框架来学习 比如fastjson
 * 主要学习它是怎么处理对象之间的引用的
 * @Author xgangzai@gmail.com
 * @Date 2019/7/11 01:02
 */
@Slf4j
@Getter
@Setter
public class SeriableBinarySearchTree implements Serializable {

  private Node root;

  public SeriableBinarySearchTree(Node root) {
    this.root = root;
  }

  public SeriableBinarySearchTree() {
    this(null);
  }

  public static void main(String[] args) {
//        SeriableBinarySearchTree tree=build();
//        SeriableBinarySearchTree tree1=build();

//        System.err.println(tree.equals(tree1));
    SeriableBinarySearchTree tree = new SeriableBinarySearchTree();
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      int key = random.nextInt(100000);
      tree.add(key, "value-" + key);
    }
//        tree.printInOrder(tree.root);

    String jsonString = JSON.toJSONString(tree);
    System.out.println(jsonString);

    SeriableBinarySearchTree tree1 = JSON.parseObject(jsonString, SeriableBinarySearchTree.class);
    System.err.println(tree.equals(tree1));

  }

  private static SeriableBinarySearchTree build() {
    SeriableBinarySearchTree tree = new SeriableBinarySearchTree();
    tree.add(100, "value-100");
    tree.add(101, "value-101");
    tree.add(102, "value-102");
    tree.add(99, "value-99");
    return tree;
  }

  /**
   * 返回当前节点的父节点
   *
   * @param key
   * @param value
   * @return 如果插入的节点为空节点，则返回null
   */
  public void add(int key, String value) {
    Node node = root;

    if (node == null) {
      node = new Node(key, value);
      root = node;
      return;
    }
//        //因为没有重复的节点 所以遇到相等的节点则负载value
//        //判断key跟root节点是否相等
//        if (key == node.key) {
//            return;
//        }

    Node parent;
    while (true) {
      parent = node;
      if (key == node.key) {
        node.value = value;
        return;
      } else if (key < node.key) {
        node = parent.left;
      } else {
        node = parent.right;
      }
      if (node == null) {
        node = new Node(key, value);
        if (key < parent.key) {
          parent.left = node;
        } else {
          parent.right = node;
        }
        break;
      }

    }

  }

  //删除
  //花了一个下午才写完这个逻辑 还不知道有没有bug 嗨，至少从打印结果来看 是没有预期的 TODO 逻辑还可以优化 提高复用程度
  public void remove(int key) {
    Node node = root;

    if (node == null) {
      return;
    }

    Node parent = null;
    while (node != null) {
      if (key == node.key) {
        break;
      } else if (key < node.key) {
        parent = node;
        node = parent.left;
      } else {
        parent = node;
        node = parent.right;
      }
    }

    //没有找到需要删除的节点
    if (node == null) {
      return;
    }

    Node newNode = null;
    //待删除的节点是叶子节点
    if (node.left == null && node.right == null) {
      if (parent != null) {
        if (parent.left == node) {
          parent.left = null;
        } else {
          parent.right = null;
        }
      }
      //只有左节点
    } else if (node.left != null && node.right == null) {
      newNode = node.left;
      replace(parent, node, newNode);

      //只有右节点
    } else if (node.left == null) {
      newNode = node.right;
      replace(parent, node, newNode);
      //左右节点都存在 用后继节点替代
    } else {
      newNode = node.right;
      Node newNodeParent = null;
      while (true) {
        if (newNode.left == null) {
          break;
        }
        newNodeParent = newNode;
        newNode = newNode.left;
      }

      replace(parent, node, newNode);

      newNode.left = node.left;
      //你的左节点已经高升啦
      if (newNodeParent != null) {
        newNodeParent.left = null;
      }

    }

    //如果删除的根节点，用新的节点来替代
    if (node == root) {
      root = newNode;
    }
  }

  private void replace(Node parent, Node node, Node newNode) {
    if (parent != null) {
      if (parent.left == node) {
        parent.left = newNode;
      } else {
        parent.right = newNode;
      }
    }
  }

  public Iterator<Integer> iterator() {
    return new TreeIterator();
  }

  public String search(int key) {
    Node node = compare(root, key);
    return node == null ? null : node.value;
  }

  public void print() {
    Node node = root;//永远从根节点开始
    if (node == null) {
      System.err.println("[]");
      return;
    }
//        printPreOrder(node);
    printInOrder(node);
//        printPostOrder(node);
  }

  /**
   * 前序 中 左 右
   *
   * @param node
   */
  private void printPreOrder(Node node) {
    doPrint(node);

    if (node.left != null) {
      printPreOrder(node.left);
    }

    if (node.right != null) {
      printPreOrder(node.right);
    }
  }

  /**
   * 中序 左 中 右
   *
   * @param node
   */
  private void printInOrder(Node node) {
    if (node.left != null) {
      printInOrder(node.left);
    }

    doPrint(node);

    if (node.right != null) {
      printInOrder(node.right);
    }
  }

  /**
   * 后序 左 右 中
   *
   * @param node
   */
  private void printPostOrder(Node node) {
    if (node.left != null) {
      printPostOrder(node.left);
    }

    if (node.right != null) {
      printPostOrder(node.right);
    }

    doPrint(node);
  }

  private void doPrint(Node node) {
    System.err.print(node.key + " ");
  }

  Node compare(Node node, int key) {
    if (node != null) {
      if (key == node.key) {
        return node;
      }
      if (key < node.key && node.left != null) {
        return compare(node.left, key);
      }
      if (key > node.key && node.right != null) {
        return compare(node.right, key);
      }
    }
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SeriableBinarySearchTree)) {
      return false;
    }
    SeriableBinarySearchTree tree = (SeriableBinarySearchTree) o;
    return Objects.equals(root, tree.root);
  }

  @Override
  public int hashCode() {
    return Objects.hash(root);
  }

  @Getter
  @Setter
  static class Node implements Serializable {

    int key;

    String value;

    Node left;

    Node right;

    public Node(int key, String value, Node left, Node right) {
      this.key = key;
      this.value = value;
      this.left = left;
      this.right = right;
    }

    public Node(int key, String value) {
      this.key = key;
      this.value = value;
    }

    public Node() {
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Node)) {
        return false;
      }
      Node node = (Node) o;
      return key == node.key &&
          Objects.equals(value, node.value) &&
          Objects.equals(left, node.left) &&
          Objects.equals(right, node.right);
    }

    @Override
    public int hashCode() {
      return Objects.hash(key, value, left, right);
    }

    @Override
    public String toString() {
      return "Node{" +
          "key=" + key +
          ", value=" + value +
          '}';
    }
  }

  //遍历
    /*
     所谓遍历，主要维护一个下一次要访问的元素，在这一次访问时更新
     */
  class TreeIterator implements Iterator<Integer> {

    ArrayDeque<Node> stack;


    public TreeIterator() {
      stack = new ArrayDeque<>();
      //从根节点开始，所有左节点组成的路径一次压入栈
      Node parent = root;
      pushAll(parent);
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public Integer next() {
      Node cur = stack.poll();
      //以cur为根节点，找出下一个
      if (cur == null) {
        throw new NoSuchElementException("已经没有啦，为什么还要来取");
      }
      //更新next
      if (cur.right != null) {
        pushAll(cur.right);
      }
      return cur.key;
    }

    private void pushAll(Node parent) {
      while (parent != null) {
        stack.push(parent);
        parent = parent.left;
      }
    }
  }

}

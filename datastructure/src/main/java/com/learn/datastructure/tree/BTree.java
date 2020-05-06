package com.learn.datastructure.tree;

import java.util.Arrays;
import java.util.Iterator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName BTree
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/16 09:32
 */
@Slf4j
public class BTree {

  /**
   * 阶数
   */
  private int m;

  /**
   * 根节点
   */
  private Node root;

  public BTree(int m) {
    this.m = m;
  }

  /**
   * 搜索
   *
   * @param key
   * @return
   */
  public String search(int key) {
    Node node = this.root;
    Entry entry = search(key, node);
    return entry == null ? null : entry.value;
  }

  private Entry search(int key, Node node) {
    if (node == null) {
      return null;
    }
    for (int i = 0; i < node.entryCount; i++) {
      Entry entry = node.entries[i];
      if (key == entry.key) {
        return entry;
      }
      if (key < entry.key) {
        //遍历当前key的左边节点
        if (node.children != null) {
          return search(key, node.children[i]);
        }
        return null;
      }

    }
    //找右边的节点
    if (node.children != null) {
      return search(key, node.children[node.entryCount]);
    }

    return null;
  }

  /**
   * 新增 不支持重复key
   *
   * @param key
   * @param value
   */
  public void add(int key, String value) {
    //先定位到叶子节点,如果在定位过程中遇到相等的key直接覆盖
    Node node = this.root;
    if (node == null) {
      node = new Node(new Entry(key, value));
      this.root = node;
      return;
    }

    add(key, value, node);

    //按照升序插入这个节点,

    //如果节点中的key数量达到m，则分裂，将中间的key或者前半部分最后一个key移到父节点中去,一直迭代直到父节点钟的
    //key数量小于m

  }

  //查增二合一
  private void add(int key, String value, Node node) {
    if (node == null) {
      log.warn("Node is null when add {}-{}", key, value);
      return;//FIXME 会不会存在这种场景呢？？？
    }

    for (int i = 0; i < node.entryCount; i++) {
      Entry entry = node.entries[i];
      if (key == entry.key) {
        entry.setValue(value);
        return;
      }
      if (key < entry.key) {
        //遍历当前key的左边节点,直至到达叶子节点
        if (node.children != null) {
          add(key, value, node.children[i]);
        } else {
          //已经到达叶子节点了
          node.add(new Entry(key, value));

          postAdd(node);

        }
//                return null;
      }

    }
    //找右边的节点
    if (node.children != null) {
//            return search(key, node.children[node.entryCount]);
    }


  }

  //加入key后调整
  private void postAdd(Node node) {
    //如果key数量已达到最大值，分裂当前节点
    if (node.notFull()) {
      return;
    }
    //分裂成两个Node
    //上升的Entry下标
    int upgradeIndex = (node.entryCount + 1) << 1;
    Entry upgradeEntry = node.entries[upgradeIndex];

    Node parent = node.parent;
    if (parent == null) {
      parent = new Node(upgradeEntry);
      //维护父子关系
    } else {
      parent.add(upgradeEntry);

    }
    postAdd(parent);
  }

  /**
   * 删除
   *
   * @param key
   * @return 返回被删除的entry
   */
  public Entry remove(int key) {

    return null;
  }

  Iterator<Integer> iterator() {
    return new Itr();
  }

  @Override
  public String toString() {
    return super.toString();
  }

  //元素，key - value
  @Getter
  @Setter
  private class Entry {

    int key;

    String value;

    public Entry(int key, String value) {
      this.key = key;
      this.value = value;
    }


    @Override
    public String toString() {
      return key + "-" + value;
    }
  }

  // 节点
  private class Node {

    int entryCount;

    Entry[] entries;

    //暂时先不用这个属性
//        boolean leaf;

    Node[] children;

    Node parent;

    Node(Entry entry) {
      //两个数组都预留一个长度
      entries = new Entry[m];
      children = new Node[m + 1];

      entries[0] = entry;
      entryCount = 1;
    }

    //加入并排序
    void add(Entry entry) {
      if (full()) {
        throw new IllegalArgumentException(this + "is full to add entry");
      }
      entries[entryCount] = entry;

      //原有的key已经是有序的，所以采用插入排序
      for (int i = entryCount; i > 0; i--) {
        if (entries[i].key < entries[i - 1].key) {
          swap(i, i - 1, entries);
        }
      }

      //计数加1
      entryCount += 1;

    }

    void swap(int i, int j, Entry[] arr) {
      Entry temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
    }

    boolean full() {
      return entryCount == entries.length;
    }

    boolean notFull() {
      return entryCount < entries.length;
    }

    @Override
    public String toString() {
      return "entryCount=" + entryCount +
          "entries=" + Arrays.toString(entries) +
          "parent=" + (parent == null ? "null" : Arrays.toString(parent.entries)) +
          "hasChild=" + (children != null && children.length > 0);
    }
  }

  class Itr implements Iterator<Integer> {

    @Override
    public boolean hasNext() {
      return false;
    }

    @Override
    public Integer next() {
      return null;
    }
  }
}

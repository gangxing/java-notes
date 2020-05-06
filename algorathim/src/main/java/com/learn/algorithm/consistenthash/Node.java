package com.learn.algorithm.consistenthash;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/22 16:06
 */
public class Node {

  private String name;

  private Map<String, String> container;

  private int hintCount;

  private int missCount;

  public Node(String name) {
    this.name = name;
    this.container = new HashMap<>();
    this.hintCount = 0;
    this.missCount = 0;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Node)) {
      return false;
    }
    Node that = (Node) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return System.identityHashCode(name);
//        return Objects.hash(name);
  }

  public void put(String key, String value) {
    if (key == null || value == null) {
      throw new IllegalArgumentException("key or value must not be null");
    }
    container.put(key, value);
  }

  public String get(String key) {
    if (key == null) {
      throw new IllegalArgumentException("key must not be null");
    }
    String value = container.get(key);
    if (value != null) {
      hintCount++;
    } else {
      missCount++;
    }
    return value;

  }

  @Override
  public String toString() {
    return "[name=" + name +
        ", hint/miss=" + hintCount + "/" + missCount +
        ",size=" + container.size() + "]";
  }
}

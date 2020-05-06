package com.learn.algorithm.consistenthash;

import java.util.Collection;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/22 16:12
 */
public class ConsistentHashRouter extends AbstractHashRouter {

  private int replicateCount;

  private TreeMap<Long, Node> nodeMap;

  public ConsistentHashRouter(int replicateCount) {
    this.replicateCount = replicateCount;
    this.nodeMap = new TreeMap<>();
  }

  @Override
  public void addNode(String name) {
    Node node = new Node(name);
    for (int i = 0; i < replicateCount; i++) {
      nodeMap.put(hash(name + i), node);
    }
  }

  @Override
  public void removeNode(String name) {
    for (int i = 0; i < replicateCount; i++) {
      nodeMap.remove(hash(name + i));
    }
    nodeMap.entrySet().forEach((entry) -> {
      if (name.equals(entry.getValue().getName())) {
        System.err.println(entry.getKey() + " is not removed");
      }
    });

  }


  @Override
  public Node getNode(String key) {
    if (nodeMap.isEmpty()) {
      throw new IllegalStateException("no node available");
    }
    long hash = hash(key);
    SortedMap<Long, Node> map = nodeMap.tailMap(hash);
    return map.size() > 0 ? map.get(map.firstKey()) : nodeMap.firstEntry().getValue();
  }

  @Override
  protected Collection<Node> allNodes() {
    return new HashSet<>(nodeMap.values());
  }
}

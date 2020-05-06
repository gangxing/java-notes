package com.learn.algorithm.consistenthash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/22 16:12
 */
public class HashRouter extends AbstractHashRouter {

  private List<Node> nodes;

  public HashRouter() {
    this.nodes = new ArrayList<>();
  }

  public void addNode(String name) {
    Node node = new Node(name);
    nodes.add(node);
  }

  public void removeNode(String name) {
    nodes.remove(new Node(name));
  }


  @Override
  public Node getNode(String key) {
    if (nodes.isEmpty()) {
      throw new IllegalStateException("no node available");
    }
    long hash = hash(key);
    return nodes.get((int) (hash % nodes.size()));
  }

  @Override
  protected Collection<Node> allNodes() {
    return nodes;
  }
}

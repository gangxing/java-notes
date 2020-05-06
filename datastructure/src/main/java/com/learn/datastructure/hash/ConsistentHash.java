package com.learn.datastructure.hash;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @ClassName ConsistentHash
 * @Description 一致性hash实现
 * @Author xianjun@ixiaopu.com https://juejin.im/post/5d0781c1f265da1b827a9ca2
 * @Date 26/11/2018 23:18
 */
public class ConsistentHash implements Hash {

  private static Set<String> physicalNodes = new TreeSet<>();


  static {
    physicalNodes.add("N0");
    physicalNodes.add("N1");
    physicalNodes.add("N2");
    physicalNodes.add("N3");
  }

  /**
   * 复制节点数 最终虚拟节点数=物理节点数* REPLICA_NUM 虚拟节点数越多 均衡性越好 FIXME 但是会空间复杂度和时间复杂度也会增大吧
   */
  private final int REPLICA_NUM = 1048576;

  //为什么要用treeMap 保证key的顺序
  private TreeMap<Long, String> virtualNodes = new TreeMap<>(); // 哈希值 => 物理节点

  // 32位的 Fowler-Noll-Vo 哈希算法
  // https://en.wikipedia.org/wiki/Fowler–Noll–Vo_hash_function


  // 根据物理节点，构建虚拟节点映射表
  public ConsistentHash() {
    for (String nodeIp : physicalNodes) {
      addPhysicalNode(nodeIp);
    }

  }

  private static long FNVHash(String key) {
    //2^24 + 2^8 + 0x93 =
    final int p = 16777619;
    Long hash = 2166136261L;
    for (int idx = 0, num = key.length(); idx < num; ++idx) {
      hash = (hash ^ key.charAt(idx)) * p;
    }
    hash += hash << 13;
    hash ^= hash >> 7;
    hash += hash << 3;
    hash ^= hash >> 17;
    hash += hash << 5;

    if (hash < 0) {
      hash = Math.abs(hash);
    }

    return hash;
  }

  public static void main(String[] args) {
    ConsistentHash ch = new ConsistentHash();

    // 初始情况
    ch.dumpObjectNodeMap("初始情况", 0, 65536);

    // 删除物理节点
    ch.removePhysicalNode("N2");
    ch.dumpObjectNodeMap("删除物理节点", 0, 65536);

    // 添加物理节点
    ch.addPhysicalNode("N5");
    ch.dumpObjectNodeMap("添加物理节点", 0, 65536);
  }

  // 添加物理节点
  public void addPhysicalNode(String nodeIp) {
    for (int idx = 0; idx < REPLICA_NUM; ++idx) {
      long hash = FNVHash(nodeIp + "#" + idx);
      virtualNodes.put(hash, nodeIp);
    }
  }

  // 删除物理节点
  public void removePhysicalNode(String nodeIp) {
    for (int idx = 0; idx < REPLICA_NUM; ++idx) {
      long hash = FNVHash(nodeIp + "#" + idx);
      virtualNodes.remove(hash);
    }
  }

  // 查找对象映射的节点
  public String getObjectNode(String object) {
    long hash = FNVHash(object);
    SortedMap<Long, String> tailMap = virtualNodes.tailMap(hash); // 所有大于 hash 的节点
    Long key = tailMap.isEmpty() ? virtualNodes.firstKey() : tailMap.firstKey();
    return virtualNodes.get(key);
  }

  // 统计对象与节点的映射关系
  public void dumpObjectNodeMap(String label, int objectMin, int objectMax) {
    // 统计
    Map<String, Integer> objectNodeMap = new TreeMap<>(); // IP => COUNT
    for (int object = objectMin; object <= objectMax; ++object) {
      String nodeIp = getObjectNode(Integer.toString(object));
      Integer count = objectNodeMap.get(nodeIp);
      objectNodeMap.put(nodeIp, (count == null ? 0 : count + 1));
    }

    // 打印
    double totalCount = objectMax - objectMin + 1;
    System.out.println("======== " + label + " ========");
    for (Map.Entry<String, Integer> entry : objectNodeMap.entrySet()) {
      long percent = (int) (100 * entry.getValue() / totalCount);
      System.out.println("IP=" + entry.getKey() + ": RATE=" + percent + "%");
    }
  }

  //一致性HASHING 暂时就研究到这里

  //问题 如果自己来实现轮询 、随机、权重随机一致性hash负载均衡 会吗？？？？


}

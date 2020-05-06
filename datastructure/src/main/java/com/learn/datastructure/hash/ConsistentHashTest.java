package com.learn.datastructure.hash;

/**
 * @ClassName ConsistentHashTest
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 22/11/2018 17:09
 */
public class ConsistentHashTest {

  public static void main(String[] args) {

    /**
     * 计算传统hash算法 节点数量变化后
     * 剩余节点的数据 为了满足重新hash规则 需要迁移的数据
     *
     * 可以达到40% 不知道怎么计算最高可以达到多少
     */

    String[] beforeNodes = new String[]{"N0", "N1", "N2", "N3", "N4"};
    String[] afterNodes = new String[]{"N0", "N1", "N2", "N3"};
    int dataLength = 1000;
    int beforeNodesLength = beforeNodes.length;
    int afterNodesLength = afterNodes.length;
//        String[] beforeArrange = new String[dataLength];
//        String[] afterArrange = new String[dataLength];
    int moved = 0;
    for (int i = 0; i < dataLength; i++) {
      String beforeNode = beforeNodes[i % beforeNodesLength];
      String afterNode = afterNodes[i % afterNodesLength];

//            if (!afterNode.equals(beforeNode) && !contains(afterNodes,beforeNode)) {
      if (!afterNode.equals(beforeNode)) {
        moved++;
      }
    }

    System.err.println("moved " + moved);


  }

  private static boolean contains(String[] arr, String element) {
    for (String e : arr) {
      if (e.equals(element)) {
        return true;
      }
    }
    return false;
  }
}

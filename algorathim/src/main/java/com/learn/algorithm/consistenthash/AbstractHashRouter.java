package com.learn.algorithm.consistenthash;

import java.util.Collection;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/22 16:51
 */
public abstract class AbstractHashRouter {

    protected long hash(String key) {
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

    public abstract void addNode(String name);

    public abstract void removeNode(String name);

    public void put(String key, String value) {
        Node node = getNode(key);
        node.put(key, value);
    }

    public String get(String key) {
        Node node = getNode(key);
        return node.get(key);
    }

    protected abstract Node getNode(String key);

    protected abstract Collection<Node> allNodes();

    public void print() {
        for (Node node : allNodes()) {
            System.err.println(node);
        }
    }
}

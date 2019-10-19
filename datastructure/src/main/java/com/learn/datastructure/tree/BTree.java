package com.learn.datastructure.tree;

/**
 * @ClassName BTree
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/16 09:32
 */
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

    //元素，key - value
    private class Entry {
        int key;

        String value;

        public Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    // 节点
    private class Node {
        int entryCount;

        Entry[] entries;

        //暂时先不用这个属性
//        boolean leaf;

        Node[] childNodes;

        public Node(Entry entry) {
            entries = new Entry[m];
            childNodes = new Node[m];
            entries[0] = entry;
            entryCount = 1;
        }

        public void add(Entry entry) {

        }
    }

    public String search(int key) {
        Node node = this.root;
        Entry entry= search(key, node);
        return entry==null?null:entry.value;
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
                if (node.childNodes != null) {
                    return search(key, node.childNodes[i]);
                }
                return null;
            }

        }
        //找右边的节点
        if (node.childNodes != null) {
            return search(key, node.childNodes[node.entryCount]);
        }

        return null;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}

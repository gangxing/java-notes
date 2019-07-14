package com.learn.datastructure.tree;

/**
 * @ClassName AbstractBinarySearchTree
 * @Description 没什么卵用
 * @Author xgangzai@gmail.com
 * @Date 2019/7/13 10:36
 */
public abstract class AbstractBinaryTree<V> implements BinaryTree<V> {


    protected Node<V> root;

    @Override
    public V search(int key) {
        Node<V> node = compare(root, key);
        return node == null ? null : node.value;
    }

    /*
        深度优先 广度优先
        先实现深度优先 Depth-First Search


     */
    @Override
    public void print() {
        Node<V> node = root;//永远从根节点开始
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
    private void printPreOrder(Node<V> node) {
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
    private void printInOrder(Node<V> node) {
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
    private void printPostOrder(Node<V> node) {
        if (node.left != null) {
            printPostOrder(node.left);
        }

        if (node.right != null) {
            printPostOrder(node.right);
        }

        doPrint(node);
    }

    private void doPrint(Node<V> node) {
        System.err.print(node.key + " ");
    }


     Node<V> compare(Node<V> node, int key) {
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

    static class Node<V> {
        int key;

        V value;

        Node<V> left;

        Node<V> right;

        public Node(int key, V value, Node<V> left, Node<V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(int key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}

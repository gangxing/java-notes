package com.learn.datastructure.tree;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @ClassName BinarySearchTree
 * @Description <href>https://juejin.im/post/5ad56de7f265da2391489be3</href>
 * @Author xgangzai@gmail.com
 * @Date 2019/7/11 01:02
 */
public class BinarySearchTree<V> implements BinaryTree<V> {

    private Node<V> root;

    public BinarySearchTree(Node<V> root) {
        this.root = root;
    }

    public BinarySearchTree() {
        this(null);
    }


    /**
     * 返回当前节点的父节点
     *
     * @param key
     * @param value
     * @return 如果插入的节点为空节点，则返回null
     */
    @Override
    public void add(int key, V value) {
        Node<V> node = root;

        if (node == null) {
            node = new Node<>(key, value);
            root = node;
            return;
        }
//        //因为没有重复的节点 所以遇到相等的节点则负载value
//        //判断key跟root节点是否相等
//        if (key == node.key) {
//            return;
//        }

        Node<V> parent;
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
                node = new Node<>(key, value);
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
    @Override
    public void remove(int key) {
        Node<V> node = root;

        if (node == null) {
            return;
        }

        Node<V> parent = null;
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

        Node<V> newNode = null;
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
            Node<V> newNodeParent = null;
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

    private void replace(Node<V> parent, Node<V> node, Node<V> newNode) {
        if (parent != null) {
            if (parent.left == node) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new TreeIterator();
    }

    //遍历
    /*
     所谓遍历，主要维护一个下一次要访问的元素，在这一次访问时更新
     */
    class TreeIterator implements Iterator<Integer> {

//        Node<V> next;

        ArrayDeque<Node<V>> stack;


        public TreeIterator() {
            stack = new ArrayDeque<>();
            //从根节点开始，所有左节点组成的路径一次压入栈
            Node<V> parent = root;
            pushAll(parent);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Integer next() {
            Node<V> cur = stack.poll();
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

        private void pushAll(Node<V> parent) {
            while (parent != null) {
                stack.push(parent);
                parent = parent.left;
            }
        }
    }


    @Override
    public V search(int key) {
        Node<V> node = compare(root, key);
        return node == null ? null : node.value;
    }

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

package com.learn.datastructure.tree;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @ClassName RedblackTree
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/16 09:33
 */
public class RedblackTree<V> implements BinaryTree<V> {

    private Node<V> root;

//    public RedblackTree(Node<V> root) {
//        this.root = root;
//    }

    public RedblackTree() {

    }


    @Override
    public void add(int key, V value) {
        //先按照二叉搜索树执行插入逻辑
        Node<V> node = root;

        if (node == null) {
            node = new Node<>(key, value);
            root = node;
            fix4Add(node);
            return;
        }
        //因为没有重复的节点 所以遇到相等的节点则负载value
        //判断key跟root节点是否相等
        if (key == node.key) {
            return;
        }

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
                node = new Node<>(key, value, parent);
                if (key < parent.key) {
                    parent.left = node;
                } else {
                    parent.right = node;
                }
                fix4Add(node);
                break;
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


    private void fix4Add(Node<V> node) {
        Node<V> parent = node.parent;
        //场景1
        if (parent == null) {//等效于parent==root
            node.setColor(Node.BLACK);
            //场景2
        } else if (parent.isRed()) {
            Node<V> grandparent = getGrandparent(node);//一定存在，并且是黑色

            Node<V> uncle = getUncle(node);//当uncle是黑色时，有可能是NIL节点
            //场景3
            if (isRed(uncle)) {
                parent.setColor(Node.BLACK);
                uncle.setColor(Node.BLACK);
                grandparent.setColor(Node.RED);
                fix4Add(grandparent);
            }else {
                //左左
                if (parent==grandparent.left){
                    //场景4
                    if (node==parent.left){//左左
                        //右旋G
                        rotateRight(grandparent);

                        //P,G颜色对换
                        parent.setColor(Node.BLACK);
                        grandparent.setColor(Node.RED);
                        //场景5
                    }else {//左右
                        //先左旋parent 再走场景4
                        rotateLeft(parent);
                        fix4Add(parent);
                    }
                }else {
                    //场景6
                    if (node==parent.right){//右右
                        //右旋G
                        rotateLeft(grandparent);

                        //P,G颜色对换
                        parent.setColor(Node.BLACK);
                        grandparent.setColor(Node.RED);

                        //场景7
                    }else {//右左
                        //先左旋parent 再走场景6
                        rotateRight(parent);
                        fix4Add(parent);
                    }
                }

            }

                //场景4 N是左节点
           /* } else if (node == parent.left) {


//                左左
                //右旋G
                rotateRight(grandparent);

                //P,G颜色对换
                parent.setColor(Node.BLACK);
                grandparent.setColor(Node.RED);


                //场景5 N是右节点
            } else {

                //左旋P TODO 这里要看P是做还是右
                rotateLeft(parent);
                //此时N和P的角色互换了，把原来的P当做新插入的节点，递归调用，走场景4修正
                fix4Add(parent);
            }*/
        }
        //P是黑色 no op

    }

    private void fix4Remove(Node<V> node) {

    }

    private boolean isRed(Node<V> node) {
        return node != null && node.isRed();
    }

    private boolean isBlack(Node<V> node) {
//        return node == null || node.isBlack();
        return !isRed(node);
    }

    /**
     * @param node 三角形原来的顶点,向左移动中领头的节点
     */
    private void rotateLeft(Node<V> node) {
        Node<V> parent = node.parent;

        Node<V> r = node.right;

        //1.父节点
        r.parent = parent;
        //新的顶点B来替代原来node
        if (parent != null) {
            if (node == parent.left) {
                parent.left = r;
            } else {
                parent.right = r;
            }
        }
        node.parent = r;

        node.right = null;

        Node<V> rl = r.left;
        if (rl != null) {
            rl.parent = node;
            node.right = rl;
        }

        r.left = node;

        if (node == root) {
            root = r;
        }

    }

    /**
     * @param node 三角形原来的顶点，向右移动中领头的节点
     */
    private void rotateRight(Node<V> node) {
        Node<V> parent = node.parent;

        Node<V> l = node.left;

        //1.父节点
        l.parent = parent;

        if (parent != null) {
            if (node == parent.left) {
                parent.left = l;
            } else {
                parent.right = l;
            }
        }
        node.parent = l;
        node.left = null;


        Node<V> lr = l.right;
        if (lr != null) {
            lr.parent = node;
            node.left = lr;
        }

        l.right = node;

        if (node == root) {
            root = l;
        }
    }

    @Override
    public void remove(int key) {

    }

    @Override
    public Iterator iterator() {
        return new TreeIterator();
    }

    class TreeIterator implements Iterator<Node<V>> {

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
        public Node<V> next() {
            Node<V> cur = stack.poll();
            //以cur为根节点，找出下一个
            if (cur == null) {
                throw new NoSuchElementException("已经没有啦，为什么还要来取");
            }
            //更新next
            if (cur.right != null) {
                pushAll(cur.right);
            }
            return cur;
        }

        private void pushAll(Node<V> parent) {
            while (parent != null) {
                stack.push(parent);
                parent = parent.left;
            }
        }
    }


    //获取祖父节点
    private Node<V> getGrandparent(Node<V> node) {
        if (node == null) {
            return null;
        }
        Node<V> parent = node.parent;
        if (parent == null) {
            return null;
        }
        return parent.parent;
    }

    //获取叔父节点
    private Node<V> getUncle(Node<V> node) {
        Node<V> grandparent = getGrandparent(node);
        if (grandparent == null) {
            return null;
        }
        return node.parent == grandparent.left ? grandparent.right : grandparent.left;
    }

    //获取兄弟节点
    private Node<V> getSiblling(Node<V> node) {
        if (node != null) {
            Node<V> parent = node.parent;
            if (parent != null) {
                return parent.left == node ? parent.right : parent.left;
            }
        }
        return null;
    }

    static class Node<V> {
        int key;

        V value;

        boolean isRed = true;//新增的节点都是红节点

        Node<V> parent;

        Node<V> left;

        Node<V> right;


        static final boolean RED = true;

        static final boolean BLACK = false;


        public Node(int key, V value) {
            this(key, value, null);
        }

        public Node(int key, V value, Node<V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return nestedNodeStr(this) + " parent=" + nestedNodeStr(parent) + " left=" + nestedNodeStr(left) + " right=" + nestedNodeStr(right);
        }

        private String nestedNodeStr(Node<V> node) {
            if (node == null) {
                return "null";
            }
            return node.key + "-" + (node.isRed ? "RED" : "BLACK");
        }

        public boolean isBlack() {
            return !isRed();
        }

        public boolean isRed() {
            return isRed;
        }

        public void setColor(boolean b) {
            this.isRed = b;
        }


    }
}

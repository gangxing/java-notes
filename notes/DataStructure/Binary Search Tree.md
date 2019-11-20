**二叉搜索树(BST)**

Binary Search Tree

**性质**

参照[维基百科定义](https://zh.wikipedia.org/wiki/二元搜尋樹),BST，其性质有以下4个性质：

1. 若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值；

2. 若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值；

3. 任意节点的左、右子树也分别为二叉查找树；

4. 没有键值相等的节点

   

BST的意义，可以实现类似有序数列的二分查找，时间复杂度为**$O(logN)$**，前提是树比较平衡，即任意一个节点的左右子树的深度相差不大。如果是极端情况，所有的节点的左(或右)节点为空，则BST退化成了一个链表，此时的查询时间复杂度为$O(N)$。为了保证二叉搜索树的查询效率，就需要在新增或删除节点时通过一定手段修正，尽可能保持树的平衡，比如<font color="#FF0000">平衡二叉树，红黑树</font>。这些树具体通过什么手段保持树的平衡，所付出的成本和达到的效果分别是怎样的，后面继续了解。

对于每一种数据结构，对外提供的操作有：*新增*、*删除*、*查询*和*遍历*，下面逐一讨论每种操作的实现。新增、删除操作都需要根据目标节点查询，所以先了解其查询操作的实现。所谓查询，按照一定的次序逐个比较，即按照何种方式来遍历树。

树的遍历有广度优先遍历和深度优先遍历

**深度优先**：一条路径遍历完之后，再遍历第二条路径。

根据二叉树每个节点最多只有两个子节点的特性，将父节点(P)，左节点(L)，右节点(R)这三个节点作为一个基本单元，深度优先又可以分为

前序：P—>L—>R

中序：L—>P—>R

后续：L—>R—>P

> 记忆这个顺序有个小窍门，所谓的前中后就是指P节点的次序是前、中还是最后。至于L和R，一个原则，先L后R

<font color="#6495ED">根据性质1和2，按照中序遍历即可得到一个递增序列。所以BST也叫有序二叉树</font>

**广度优先**：从根节点开始，依次从左到右遍历每一层。这种方式有什么作用，该怎么遍历，暂且不表



**查询操作**

从根节点开始，比较当前节点和目标节点的大小，如果目标节点和当前前节点相等，则查询结束，如果前者大于后者，说明要找的节点在左子树中，则进入当前节点的左子树继续比较，反之，进入右子树继续比较。

从代码实现来讲，这是一个递归操作，核心逻辑如下

```java
/*
 * @param node 当前比较的节点
 * @param key 目标节点的key
 */
Node<V> compare(Node<V> node, int key) {
    if (node != null) {
        if (key == node.key) {//找到目标节点
            return node;
        }
        if (key < node.key) {
            return compare(node.left, key);//进入左子树继续查找
        }
        if (key > node.key) {
            return compare(node.right, key);//进入右子树继续查找
        }
    }
    return null;
}
```



**新增操作**

根据BST的<font color="#FF0000">性质1</font>和<font color="#FF0000">性质2</font>，从根节点开始按照查找操作找新增节点的位置(新节点的父节点)，然后判断新增节点是其父节点的左节点还是右节点。核心逻辑如下

```java
public void add(int key, V value) {
        Node<V> node = root;

        if (node == null) {
            node = new Node<>(key, value);
            root = node;
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
```



**删除操作**

BST的删除操作，从根节点开始，按照查找逻辑，根据key定位到要删除的节点N，如果N存在，会有三种情况

1.N没有子节点：将其父节点对其的引用置为空即可

2.N只有一个节点：将其父节点对其的引用置换成其子节点的引用即可

3.N有两个子节点：

这种情况比较复杂，复杂在<font color="#FF0000">哪个节点来替代N的位置</font>？

根据BST的性质，按照中序遍历是一个递增数列，为了满足删除一个节点后，中序遍历仍能得到一个递增数列这一特性。因此删除后的节点要用其<font color="#FF0000">前驱节点</font>或<font color="#FF0000">后继节点</font>占位，即数列中的前一个或后一个节点。

**前驱节点**，在树中的表述为当前节点的左节点，左子树中最大的节点，即左子树的最右叶子节点

**后继节点**，右子树中最小的节点，即右子树的最左叶子节点。

两种方式的效果一样，下面代码采用后继节点补位的方式，删除操作的实现如下

```java
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
```



**迭代操作**

按照中序迭代，即递增返回节点。`hasNext()`和`next()`的平均时间复杂度为O(1),如果是这样，肯定需要额外的存储空间，[参照](https://zhuanlan.zhihu.com/p/27679447)

```
维护一个栈，从根节点开始，每次迭代地将根节点的左孩子压入栈，直到左孩子为空为止。

调用next()方法时，弹出栈顶，如果被弹出的元素拥有右孩子，则以右孩子为根，将其左孩子迭代压栈。
```

栈可以用`ArrayDeque` ,后续再了解其实现原理。 FILO

实现还是蛮简单的，所需要的空间复杂度为O(h),h为树的高度

初始化迭代器

```java
public TreeIterator() {
    stack = new ArrayDeque<>();
    //从根节点开始，所有左节点组成的路径一次压入栈
    Node<V> parent = root;
    pushAll(parent);
}
```

将一个节点开始的左节点路径上的所有节点压入栈，

```java
private void pushAll(Node<V> parent) {
    while (parent != null) {
        stack.push(parent);
        parent = parent.left;
    }
}
```

实现`hashNext()`,只要栈不为空，并且下一个就是置于栈顶的节点

实现`next()`,取出栈顶节点后，如果当前节点有右节点，则以右节点为根节点，将其左节点链路上的所有节点压入栈，这里有点递归的味道，实现逻辑如下

```java
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
```

至此，搜索二叉树的常规操作都实现了，虽然每种操作都还有更优秀的实现方案，比如迭代可以不用栈作为额外的辅助空间，给叶子节点添加前驱和后继两个节点的引用。不过这些都有点类似于茴香豆的茴有几种写法的味道，暂且不表。



BST搜索能达到类似于二分查找法的前提是树的平衡性足够好，每次遍历到一个节点都能过滤掉另一半的节点。但是BST在新增或删除节点过程中，树的平衡性是没有任何保证的，最极端的情况是BST退化成一个链表。接下来了解平衡二叉树和红黑树是怎么解决这个问题的。


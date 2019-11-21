Selection Sort 选择排序

**需求**

对N个整数升序排序

**思路**

类似插入排序，分有序和无序两部分，每次从无序部分中找出最小的一个元素放到有序部分的末尾。

**算法评判**

* 时间复杂度

  $O(N^2)$

* 空间复杂度

  $O(1)$

  > 只需要常量级的辅助空间，所以也叫原地排序

* 稳定性

  结论：**不稳定**

实现代码如下

```java
public void sort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        int min = i;
        for (int j = i; j < arr.length; j++) {
            if (arr[j] < arr[min]) {
                min = j;
            }
        }
        swap(arr, min, i);
    }
}
```



为什么选择排序是不稳定的，看下面的示例代码

```java
public void sort(Item[] arr) {
    for (int i = 0; i < arr.length; i++) {
        int min = i;
        for (int j = i; j < arr.length; j++) {
            if (arr[j].compareTo(arr[min]) < 0) {
                min = j;
            }
        }
        swap(arr, min, i);
    }
}

 void swap(Item[] arr, int i, int j) {
    Item t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
}

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Item implements Comparable<Item> {
        private int key;

        private String value;

        @Override
        public int compareTo(Item o) {
            return this.key - o.key;
        }

        @Override
        public String toString() {
            return key + ":" + value;
        }
    }

public static void main(String[] args) {
        SelectionSort sort = new SelectionSort();
        Item[] arr=new Item[5];
        arr[0]=new Item(5,"51");
        arr[1]=new Item(4,"41");
        arr[2]=new Item(5,"52");
        arr[3]=new Item(6,"61");
        arr[4]=new Item(4,"42");
  //[5:51, 4:41, 5:52, 6:61, 4:42]
        System.err.println(Arrays.toString(arr));

        sort.sort(arr);
  //[4:41, 4:42, 5:52, 5:51, 6:61]
        System.err.println(Arrays.toString(arr));
  //"52"和"51"两个元素的相对次序被改变了
    }
```

第一次选出的最小元素是41，与51交换，[ 4:41,5:51, 5:52, 6:61, 4:42]

第二次宣促的最小元素是42，与51交换，[ 4:41,4:42, 5:52, 6:61, 5:51]

只要有相同的元素，并且不在最后，则前面的元素一定会被交换到后面去。所以选择排序是不稳定的排序算法。

**冒泡排序、插入排序和选择排序三者对比**

三者的时间复杂度和空间复杂度相同，冒泡排序和插入排序是稳定的排序算法，选择排序不是，所以在这三者中，优先选择冒泡排序和插入排序。

那冒泡排序和插入排序各种维度的比较都一致，所以两种排序算法完全等效吗？

非也，虽然两者的时间复杂度一样，但深入观察，插入排序中每次移动元素只需要一次赋值操作，冒泡排序相邻元素交换时需要三次赋值操作，所以这两者比起来，插入顺序更胜一筹。

> **插入排序 > 冒泡排序 > 选择排序**
package com.learn.jdk;

/**
 * @ClassName Main
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/6/5 11:13
 */
public class Main {

  public static void main(String[] args) {
//        System.err.println("开始学习java基础知识");
//        int hashCode=testHashCode();
//        System.err.println("hashCode:"+"2".hashCode());
//        testReference();
//        testLoop();
//        testEquals();
//        testCeil();
//        testBitOperation();
//        testOOM();
//        int i=100;
//        testModifyPrimitive(i);
    Student student = new Student(10, "zhangsan");
    testModifyObject(student);
    System.err.println(student);

//        for (int i = 0; i < 100; i++) {
//            Random random = new Random();
//            int x = 1 + random.nextInt(100);
//            int y = 1 + random.nextInt(100);
//            testMod(x, y);
//        }

//        testMod(37,22);

    int n = 400;
    long start1 = System.currentTimeMillis();
    int res = f1(n);
    long end1 = System.currentTimeMillis();
    System.err.println("f1 " + (end1 - start1) + " ms " + res);

    long start2 = System.currentTimeMillis();
    int res2 = f2(n);
    long end2 = System.currentTimeMillis();
    System.err.println("f2 " + (end2 - start2) + " ms " + res2);
    System.err.println(res2 == res);
  }

  private static int testHashCode() {
    char[] arr = new char[Integer.MAX_VALUE / 5];
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < arr.length; i++) {
      arr[i] = 'z';
    }
    String s = new String(arr);
    return s.hashCode();
  }

  //测试引用
  private static void testReference() {

    Object org = new Object();
    System.err.println("org:" + org);
    Object newO = org;
    System.err.println("org:" + org);
    System.err.println("newO:" + newO);
    org = null;//虽然org放弃了对这个对象的引用，但是newO还持有这个对象的引用，所以这时候这个对象不会被回收？？？
    System.err.println("org:" + org);
    System.err.println("newO:" + newO);
    System.err.println(newO == null);
  }

  private static void testLoop() {
    for (int i = 0; i < 5; i++) {
      System.err.println(i);
    }
  }

  private static void testEquals() {
    Integer a = 3;
    Integer b = 3;
    System.err.println("a==b:" + (a == b));
    System.err.println("a.equals(b):" + (a.equals(b)));
    System.err.println("a.compareTo(b)==0:" + (a.compareTo(b) == 0));
  }

  private static void testCeil() {
    double d = -2.9D;
    System.err.println(Math.ceil(d));
  }

  private static void testBitOperation() {
    int i = 1;
    i <<= 4;
    System.err.println(i);
    System.err.println(2 << 4);
    System.err.println(1 << 5);
  }

  private static void testOOM() {
    System.err.println(-1 >>> 3);
    long[] arr = new long[(-1 >>> 4)];//256M
  }

  private static void testModifyPrimitive(int i) {
    i = 8;
  }

  private static void testModifyObject(Student student) {
//        student=new Student(30,"lisi");
    student.age = 90;
  }

  //x 被除数 y 除数
  //结论 只适用于y是2^n的场景
  private static void testMod(int x, int y) {
    int mod = x % y;
    int base = y | (y >> 1);
    base = base | (base >> 2);
    base = base | (base >> 4);
    base = base | (base >> 8);
    base = base | (base >> 16);
    int mod1 = x & base;
    System.err.println(x + " % " + y + " = " + mod + " " + (mod == mod1));
  }

  //这种递归严重耗性能 因为对于每一个n 都会重复计算
  private static int f1(int n) {
    if (n == 1) {
      return 0;
    }
    if (n == 2) {
      return 1;
    }
    return f1(n - 1) + f1(n - 2);

  }

  //迭代型
  private static int f2(int n) {
    int p = 1;
    int pp = 0;
    int res = 0;

    if (n == 1) {
      return 0;
    }
    if (n == 2) {
      return 1;
    }
    for (int i = 3; i <= n; i++) {
      res = pp + p;
      pp = p;
      p = res;
    }
    return res;

  }

  private static class Student {

    int age;

    String name;

    public Student(int age, String name) {
      this.age = age;
      this.name = name;
    }

    @Override
    public String toString() {
      return "Student{" +
          "age=" + age +
          ", name='" + name + '\'' +
          '}';
    }
  }
}

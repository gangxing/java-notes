package com.learn.concurrent;

/**
 * @ClassName MyAtomicInteger
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 05/11/2018 23:29
 */
public class MyAtomicInteger {

  private static final long valueOffset;

  static {
    try {
      valueOffset = UnsafeAccess.UNSAFE.objectFieldOffset
          (MyAtomicInteger.class.getDeclaredField("value"));
    } catch (Exception ex) {
      throw new Error(ex);
    }
  }

  private volatile int value;


  /**
   * 为什么这样不能实现线程安全？？？？
   *
   * @param value
   */
  //必须要借助Unsafe才能实现CAS 否则 只是利用volatile 依旧会出现并发问题
//    private static final Unsafe unsafe = Unsafe.getUnsafe();
  public MyAtomicInteger(int value) {
    this.value = value;
  }

  public static void main(String[] args) {

  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getAndIncrement() {

    //这是AtomicInteger中的实现方式 其内部实现方式跟下面一致
//        return UnsafeAccess.UNSAFE.getAndAddInt(this,valueOffset,1);
    //自旋 + CAS
    for (; ; ) {
      int cur = getValue();
      int next = cur + 1;
      if (UnsafeAccess.UNSAFE.compareAndSwapInt(this, valueOffset, cur, next)) {
        return cur;
      }
    }
  }

  /*
   * compare & set
   * 两个动作必须是原子性的
   * 借助JNI（Java  Native Invocation） 利用硬件提供的swap 和test_and_set 两个指令
   * 对这两个指令的封装 @see Unsafe
   * 从硬件层保证这两个指令的原子性
   *
   * https://www.jianshu.com/p/09477cec1478
   *
   * CAS思路：
   * 修改变量 ，只有值是预期的时候 才赋值新值 否则 将预期值改为现在获取到值 一直循环
   * 直至修改成功
   *
   * 有一个要点 被修改的变量一定要用volatile保证线程间的可见性 否则 也是取不到最新值的
   * 可能会导致一直循环。。。。耗费CPU资源
   *
   */

  public int incrementAndGet() {
    //自旋 + CAS
    for (; ; ) {
      int cur = getValue();
      int next = cur + 1;
      if (UnsafeAccess.UNSAFE.compareAndSwapInt(this, valueOffset, cur, next)) {
        return next;
      }
    }
  }


}

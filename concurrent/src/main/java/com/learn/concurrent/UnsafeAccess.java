package com.learn.concurrent;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/9 15:39
 */
public class UnsafeAccess {

  public static final boolean SUPPORTS_GET_AND_SET;
  public static final Unsafe UNSAFE;


  static {
    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      UNSAFE = (Unsafe) field.get((Object) null);
    } catch (Exception var3) {
      SUPPORTS_GET_AND_SET = false;
      throw new RuntimeException(var3);
    }

    boolean getAndSetSupport = false;

    try {
      Unsafe.class.getMethod("getAndSetObject", Object.class, Long.TYPE, Object.class);
      getAndSetSupport = true;
    } catch (Exception var2) {
      ;
    }

    SUPPORTS_GET_AND_SET = getAndSetSupport;
  }
}

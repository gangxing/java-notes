package com.learn.jdk.jvm;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Description 非标准的双亲委派模型自定义类加载器
 * @Author xgangzai
 * @Date 2019/11/20 15:12
 */
//有意思的是自定义loader也是一个Java类，所以它自己的类加载器是AppClassLoader
public class NonStandardCustomClassLoader extends ClassLoader {


  public static void main(String[] args) {

    NonStandardCustomClassLoader loader = new NonStandardCustomClassLoader();

//        如果不指定父类加载器，则默认父类加载器是AppClassLoader
    System.err.println(loader.getParent());

    try {

      Class<?> clazz = loader.loadClass("com.test.Person");
      System.err.println("loader:" + clazz.getClassLoader());
      System.err.println(clazz);

      Object object = clazz.newInstance();
      Method ageGetter = clazz.getDeclaredMethod("getAge");
      int age = (int) ageGetter.invoke(object);
      System.err.println("age=" + age);
      Method nameGetter = clazz.getDeclaredMethod("getName");
      String name = (String) nameGetter.invoke(object);
      System.err.println("name=" + name);
      Method maleGetter = clazz.getDeclaredMethod("getMale");
      boolean male = (boolean) maleGetter.invoke(object);
      System.err.println("male=" + male);
//            com.test.Person=(com.test.Person)clazz.newInstance();
//        } catch (ClassNotFoundException e) {
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  /**
   * 自定义加载类方法，不再委托父类加载器加载，直接自己加载
   *
   * @param name
   * @return
   * @throws ClassNotFoundException
   */
  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    //如果不是自己关注的类，交由
    if (!name.startsWith("com.test")) {
      return super.loadClass(name);
    }

    //不遵守双亲委派模型，自己直接处理
    synchronized (getClassLoadingLock(name)) {
      Class<?> c = findLoadedClass(name);
      if (c == null) {
        c = findClass(name);
      }
      resolveClass(c);
      return c;
    }
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {

    String base = "/Users/xgang/temp/";
    String namePath = name.replace(".", "/");
    System.err.println("name=" + name);
    try {
      byte[] classBytes = Files.readAllBytes(Paths.get(base + namePath + ".class"));
      return defineClass(name, classBytes, 0, classBytes.length);
    } catch (IOException e) {
      throw new ClassNotFoundException(name);
    }
  }


}

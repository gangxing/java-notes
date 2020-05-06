package com.learn.jdk;

/**
 * @ClassName MethodInvocation
 * @Description 研究方法调用是值传递还是引用传递，即所谓的形参、实参
 * @Author xgangzai@gmail.com
 * @Date 2019/6/12 10:08
 */
public class MethodInvocation {


  public static void main(String[] args) {
    Student hl = new Student(18, "hl", true);
    Student lmm = new Student(17, "lmm", false);

    System.err.println(hl);
    System.err.println(lmm);
  }


  private static void swap(Student s1, Student s2) {

  }

  private static class Student {

    private int age;

    private String name;

    private boolean male;

    public Student() {
    }

    public Student(int age, String name, boolean male) {
      this.age = age;
      this.name = name;
      this.male = male;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public boolean isMale() {
      return male;
    }

    public void setMale(boolean male) {
      this.male = male;
    }
  }
}

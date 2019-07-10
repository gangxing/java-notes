package com.learn.jdk;

/**
 * @ClassName InnerClassLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/11 00:29
 */
public class InnerClassLearn {
    private  Integer age;

    public InnerClassLearn(Integer age) {
        this.age = age;
    }

    public void pp(){
        Inner inner=new Inner();
        inner.p();
    }
    class Inner{
        public void p(){
            //内部类是可以直接访问外部类的成员变量的,但是不能在静态方法中实例化
            //静态内部类可以在静态方法中实例化，但是不再能访问外部类的非静态成员变量
            System.err.println(age);
        }

        public void ppp(){
            pp();
        }
    }

    public static void main(String[] args) {
        //
//        Inner learn=new InnerClassLearn.Inner();
//        learn.pp();
    }
}

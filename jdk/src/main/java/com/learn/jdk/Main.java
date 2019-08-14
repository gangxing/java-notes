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

        System.err.println((int)'a');
    }

    private static int testHashCode(){
        char[] arr=new char[Integer.MAX_VALUE/5];
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<arr.length;i++){
            arr[i]='z';
        }
        String s=new String(arr);
        return s.hashCode();
    }


    //测试引用
    private static void testReference(){

        Object org=new Object();
        System.err.println("org:"+org);
        Object newO=org;
        System.err.println("org:"+org);
        System.err.println("newO:"+newO);
        org=null;//虽然org放弃了对这个对象的引用，但是newO还持有这个对象的引用，所以这时候这个对象不会被回收？？？
        System.err.println("org:"+org);
        System.err.println("newO:"+newO);
        System.err.println(newO==null);
    }

    private static void testLoop(){
        for (int i=0;i<5;i++){
            System.err.println(i);
        }
    }

    private static void testEquals(){
        Integer a=3;
        Integer b=3;
        System.err.println("a==b:"+(a==b));
        System.err.println("a.equals(b):"+(a.equals(b)));
        System.err.println("a.compareTo(b)==0:"+(a.compareTo(b)==0));
    }

    private static void testCeil(){
        double d=-2.9D;
        System.err.println(Math.ceil(d));
    }

    private static void testBitOperation(){
        int i=1;
        i<<=4;
        System.err.println(i);
        System.err.println(2<<4);
        System.err.println(1<<5);
    }

    private static void testOOM(){
        System.err.println(-1>>>3);
        long[] arr=new long[(-1>>>4)];//256M
    }
}

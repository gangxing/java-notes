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
        System.err.println("hashCode:"+"2".hashCode());
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
}

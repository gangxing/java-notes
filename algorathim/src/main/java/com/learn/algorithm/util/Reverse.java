package com.learn.algorithm.util;

/**
 * @ClassName Reverse
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/3/20 00:48
 */
public class Reverse {

    public String reverse(String source){
        String[] arr=source.split(" ");
        String swap;
        int len=arr.length;
        for (int i=0;i<len/2;i++){
            swap=arr[i];
            arr[i]=arr[len-i-1];
            arr[len-i-1]=swap;
        }

        StringBuilder sb=new StringBuilder();
        for (String s:arr){
            sb.append(s).append(" ");
        }
        return sb.toString();
    }

    public String reverse1(String source){
        char[] arr=source.toCharArray();
        char swap;
        int len=arr.length;
        for (int i=0;i<len/2;i++){
            swap=arr[i];
            arr[i]=arr[len-i-1];
            arr[len-i-1]=swap;
        }
        return new String(arr);
    }

    public static void main(String[] args) {
        Reverse reverse=new Reverse();
        String s=reverse.reverse1("1234567890");
        System.err.println(s);
    }


}

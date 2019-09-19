package com.learn.datastructure.util;

import org.apache.commons.lang3.RandomUtils;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/9/5 18:33
 */
public class StringRandoms {

    public static String random(int length){
        char[] chars=new char[length];
        for (int i=0;i<chars.length;i++){
            chars[i]= (char) RandomUtils.nextInt(33,123);
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        char c='z';
        System.err.println(random(10));
//        for (char c=32;c<256;c++){
//            System.err.println(c);
//        }
    }
}

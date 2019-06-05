package com.learn.algorithm;

import com.learn.algorithm.util.RandomStringUtils;
import com.learn.algorithm.util.Reverse;

/**
 * @ClassName Main
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/3/19 23:37
 */
public class Main {

    public static void main(String[] args) {
        reverse();
    }

    public static void firstUniqueChar(String[] args) {

        int length = 100000000;
        String randomString = RandomStringUtils.random(length);
        FirstUniqueCharFinder finder = new FirstUniqueCharFinder();
        long start=System.currentTimeMillis();
        char c = finder.find(randomString);
        long time=System.currentTimeMillis()-start;

        System.err.println("duration "+time+" ms");
//        System.err.println(randomString);
        System.err.println("First unique char is " + c);
    }

    private static void reverse(){

        String s="random a string with specified";
        Reverse reverse=new Reverse();
        System.err.println(reverse.reverse(s));
    }
}

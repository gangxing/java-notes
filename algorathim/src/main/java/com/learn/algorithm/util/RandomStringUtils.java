package com.learn.algorithm.util;

import java.util.Random;

/**
 * @ClassName RandomStringUtils
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/3/19 23:40
 */
public class RandomStringUtils {

    private static char[] LOWWER_CASE = new char[26];

    static {
        for (int i = 0; i < 26; i++) {
            LOWWER_CASE[i] = (char) ('a' + i);
        }
    }

    /**
     * random a string with specified length
     *
     * @param length
     * @return
     */
    public static String random(int length) {

        if (length == 0) {
            return "";
        }

        char[] chars = new char[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            chars[i] = LOWWER_CASE[random.nextInt(LOWWER_CASE.length)];
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        char c = 'a';
//        System.err.println((char) (c + 1));

        int len=100;
        for (int i=0;i<100;i++){

            System.err.println(i+"-->"+(char) i);
        }
        System.err.println(1);

    }
}

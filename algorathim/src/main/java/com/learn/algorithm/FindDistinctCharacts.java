package com.learn.algorithm;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/12/2 16:41
 */
public class FindDistinctCharacts {

    public static void main(String[] args) {

        String s = "ABCAefldl88393BC";
        String ss = find(s);
        System.err.println(ss);
    }

//    private static String find(String s) {
//        char[] source = s.toCharArray();
//        char minChar = 'A';
//        for (char c : source) {
//            if (c < minChar) {
//                minChar = c;
//            }
//        }
//        System.err.println(Character.MAX_VALUE-Character.MIN_VALUE);
//        byte[] count = new byte[255];
//        for (char c : source) {
//            count[c - minChar] ++;
//        }
//        char[] target = new char[255];
//        int c = 0;
//        for (int i = 0; i < count.length; i++) {
//            if (count[i] > (byte) 1) {
//                target[c] = (char) (minChar + i);
//                c++;
//            }
//        }
//
//        return String.valueOf(target, 0, c);
//
//    }

    private static String find(String s) {
        char[] source = s.toCharArray();
        char minChar = 'A';
        char maxChar = 'A';
        for (char c : source) {
            if (c < minChar) {
                minChar = c;
            }
            if (c > maxChar) {
                maxChar = c;
            }
        }
        System.err.println(Character.MAX_VALUE - Character.MIN_VALUE);
        byte[] count = new byte[maxChar - minChar + 1];
        for (char c : source) {
            count[c - minChar]++;
        }
        char[] target = new char[maxChar-minChar+1];
        int c = 0;
        for (int i = 0; i < count.length; i++) {
            if (count[i] > (byte) 1) {
                target[c] = (char) (minChar + i);
                c++;
            }
        }

        return String.valueOf(target, 0, c);

    }
}

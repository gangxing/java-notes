package com.learn.datastructure.sort;

import java.util.Arrays;

/**
 * @Description 利用基数排序思想排序单词
 * @Author xgangzai
 * @Date 2019/8/14 23:46
 */
public class WordRadixSort {

    static char[] buketChars = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static void main(String[] args) {
        String source = "English Language Day was first celebrated in alongside Arabic Language Day Chinese Language Day French Language Day Russian Language Day and Spanish Language Day These are the six official languages of the United Nations and each has a special day designed to raise awareness of the history culture and achievements of these languages";
        String[] words = source.split("\\s");
        sort(words);
    }

    public static void sort(String[] words) {

        int maxLength = 0;
        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            words[i] = s.trim();
            if (words[i].length() > maxLength) {
                maxLength = s.length();
            }
        }
        System.err.println("origin " + Arrays.toString(words));

        int bucketCount = 53;

        int[] count = new int[bucketCount];
        //每个字符一个桶 ' ', 'A', 'B', 'C', 'D', ... 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', ... 'x', 'y', 'z'
        String[][] buckets = new String[bucketCount][words.length];
        for (int round = 0; round < maxLength; round++) {//从前往后比较
            for (String s : words) {
                char c = ' ';
                if (s.length() > round) {
                    c = s.charAt(round);
                }
                //已经到最后一位了 直接放入' '桶中
                int bucketIndex = bucketIndex(c);
                buckets[bucketIndex][count[bucketIndex]++] = s;
            }

            //回填到原数组中
            int bucketIndexNew = 0;
            for (int bucketIndex = 0; bucketIndex < bucketCount; bucketIndex++) {
                String[] bucket = buckets[bucketIndex];
                for (int i = 0; i < count[bucketIndex]; i++) {
                    words[bucketIndexNew++] = bucket[i];
                }
                count[bucketIndex] = 0;
            }
        }

        System.err.println("sorted " + Arrays.toString(words));
    }


    private static int bucketIndex(char c) {
        for (int i = 0; i < buketChars.length; i++) {
            if (buketChars[i] == c) {
                return i;
            }
        }
        throw new IllegalArgumentException(c + "不存在");
    }
}

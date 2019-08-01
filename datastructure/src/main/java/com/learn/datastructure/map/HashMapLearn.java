package com.learn.datastructure.map;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * @ClassName MapLearn
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 11/11/2018 20:14
 */
public class HashMapLearn {

    public static void main(String[] args) {
//        duplicateHashCode();
//        System.err.println("!~".hashCode());
//        randomString(3);
//        randomDuplicateHashCode();
        testHashMap();
    }

    private static void testHashMap() {

        int[] keys = {1, 5, 9, 13, 17, 25, 29, 33, 49};

        HashMap<Integer, String> map = new HashMap<>(3);
        for (int i = 1; i < 31; i++) {
            int key = (1 << i) - 1;
            map.put(key, "value" + key);
        }
        String v=map.get(31);
        Set<Map.Entry<Integer, String>> entrySet = map.entrySet();
        Iterator<Map.Entry<Integer, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> nextEntry = iterator.next();
            System.err.println(nextEntry.getKey() + "->" + nextEntry.getValue());
        }
    }


    private static void testStringHashCode() {
        char[] arr = new char[6000];
        int i = 0;
        for (char c = ' '; c <= '~'; c++) {
            arr[i++] = c;
        }
        String s = new String(arr);
        // !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~
        System.err.println(s);
    }


    private static void duplicateHashCode() {
        char c = '!';
        int hashCode = "!~".hashCode();
        char secod;
        while ((secod = secondChar(c, hashCode)) <= '~') {
            String s = new String(new char[]{c, secod});
            System.err.println(s + "-->" + s.hashCode());
            c++;
        }

    }

    //3位随机字符串
    private static void randomDuplicateHashCode() {
        Map<Integer, Set<String>> hashCode2StringListMap = new TreeMap<>();

        for (int i = 0; i < 9999999; i++) {
            String s = randomString(4);
            int hashCode = s.hashCode();
            Set<String> set = hashCode2StringListMap.get(hashCode);
            if (set == null) {
                set = new HashSet<>();
                hashCode2StringListMap.put(hashCode, set);
            }
            set.add(s);
        }

        hashCode2StringListMap.forEach(new BiConsumer<Integer, Set<String>>() {
            @Override
            public void accept(Integer integer, Set<String> strings) {
                int size = strings.size();
                if (size > 6) {
                    System.err.println(integer + "-->" + strings.size() + "->" + JSON.toJSONString(strings));
//                }else {
//                    System.out.println(integer + "-->" + strings.size() + "->" + JSON.toJSONString(strings));
                }
            }
        });

    }

    private static char[] chars = new char[94];

    static {
        int i = 0;
        for (char c = '!'; c <= '~'; c++) {
            chars[i++] = c;
        }
    }

    //94进制
    /*
     !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~
     0................94
     */

    private static void duplicateHashCode(int length) {
        Map<Integer, Set<String>> hashCode2StringListMap = new TreeMap<>();

//        int max=
        for (int i = 0; i < 9999999; i++) {
            String s = randomString(4);
            int hashCode = s.hashCode();
            Set<String> set = hashCode2StringListMap.get(hashCode);
            if (set == null) {
                set = new HashSet<>();
                hashCode2StringListMap.put(hashCode, set);
            }
            set.add(s);
        }

        hashCode2StringListMap.forEach(new BiConsumer<Integer, Set<String>>() {
            @Override
            public void accept(Integer integer, Set<String> strings) {
                int size = strings.size();
                if (size > 6) {
                    System.err.println(integer + "-->" + strings.size() + "->" + JSON.toJSONString(strings));
//                }else {
//                    System.out.println(integer + "-->" + strings.size() + "->" + JSON.toJSONString(strings));
                }
            }
        });

    }


    private static String randomString(int length) {
        char l = '!';
        char h = '~';
        char[] arr = new char[length];
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char cc = (char) (l + random.nextInt(h - l));
            arr[i] = cc;
        }
        return new String(arr);
    }

    private static char secondChar(char first, int hashCode) {
        return (char) (hashCode - 31 * first);
    }


}

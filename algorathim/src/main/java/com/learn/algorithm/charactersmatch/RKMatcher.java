package com.learn.algorithm.charactersmatch;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/23 11:17
 */

/**
 * 对BF算法的优化
 * 核心思想是对source中n-m+1个子串和target分别计算其hash值，
 * 然后比对n-m+1个hash中有没有跟target的hash相等的
 * <p>
 * 问题：
 * 1.时间复杂度是多少，计算n-m+2次hash，比对n-m+1次。所以关键就在于hash函数的选择
 * 2.怎么解决hash冲突，还是在于hash函数的选择,当hash值一样时，再次逐个字符比对，感觉这是hash冲突的通用套路
 * 解决办法：利用进制转换的思想，假如面对的字符集是52个英文字母和10个数字，那就将一个字符串当做一个62进制的数字，转换成
 * 10进制数字。当字符串过长时，会不会出现数字溢出的问题，先实现
 * 计算的时候，有两点可以优化
 * 1.计算第i个子串的时候，可以复用部分i-1个子串的计算结果。类似等比数列
 * 2.可以将62^n 次方的值缓存下来，用户的时候直接取就行了 n作为下标
 */
public class RKMatcher implements Matcher {

    /**
     * @param source
     * @param target
     * @return
     */
    @Override
    public boolean matches(String source, String target) {
        char[] sourceChars = source.toCharArray();
        char[] targetChars = target.toCharArray();

        int sourceLen = sourceChars.length;
        int targetLen = targetChars.length;
        if (sourceLen < targetLen) {
            return false;
        }

        int targetHash = trans62toDecimal(targetChars, 0, targetLen);
        for (int i = 0; i <= sourceLen - targetLen + 1; i++) {
            if (trans62toDecimal(sourceChars, i, targetLen) == targetHash) {
                return true;
            }
        }

        return false;
    }

    static int[] rediuxCache;

    static {
        rediuxCache = new int[10];
        rediuxCache[0] = 1;
        for (int i = 1; i < 10; i++) {
            rediuxCache[i] = rediuxCache[i - 1] * 62;
        }
    }


    //    private static final char[] BASE="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final String BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int trans62toDecimal(String s) {
        return trans62toDecimal(s.toCharArray(), 0, s.length());
    }

    private int trans62toDecimal(char[] chars, int from, int length) {

        int res = 0;

        for (int i = 0; i < length; i++) {
            char c = chars[from + i];
            int ii = BASE.indexOf(c);
//            for (int j = 0; j < length - i - 1; j++) {
//                ii *= 62;
//            }
            res += ii*rediuxCache[length-i-1];
        }

        return res;
    }

    public static void main(String[] args) {
        RKMatcher matcher = new RKMatcher();
        String s = "akdkdkdkkdkdkdkkdkdjdjjdjdjdjdjjd";
        int i = matcher.trans62toDecimal(s.toCharArray(), 0, s.length());
        System.err.println(i);
    }
}

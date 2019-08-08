package com.learn.algorithm;

import java.util.Random;

/**
 * @ClassName BitMap
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/8/8 10:40
 */

public class BitMap {
    /**
     * 实现redis setBit getBit 命令 并统计1的个数
     * 支持 2^30位 即128M
     */

    private byte[] bytes;

    /**
     * 容量 bit 数量
     * capacity = bytes.length * 8
     */
    private int capacity;

    /**
     * 最大offset所在byte的下标
     */
    private int maxByteIndex;


    public BitMap(int capacity) {
        //类似hashMap 先确定真正的capacity 是8的倍数 即确定数组的长度
        //先简单一点
        int len = (capacity + 7) / 8;
        bytes = new byte[len];
        this.capacity = len * 8;
        this.maxByteIndex = 0;
    }

    /**
     * @param offset 指定偏移bit位置 从0 ~ capacity-1
     * @param value  bit值 0或者1
     */
    public void setBit(int offset, int value) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset out of range");
        }
        if (offset >= capacity) {
//
            //扩容
            int len = (int) ((offset + 8L) / 8);
            byte[] newBytes = new byte[len];
            System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
            bytes = newBytes;
            capacity = len * 8;
        }

        if (value != 0 && value != 1) {
            throw new IllegalArgumentException("value must be 0 or 1");
        }
        //确定offset所在的byte的下标
        int byteIndex = (offset / 8);
        byte b = bytes[byteIndex];

        //确定bit在byte八个bit中的位置
        int bitIndex = (offset % 8);

        if (value == 1) {
            b = (byte) (1 << 7 - bitIndex | b);
            if (byteIndex > maxByteIndex) {
                maxByteIndex = byteIndex;
            }
            bytes[byteIndex] = b;
        } else {
            b = (byte) (~(1 << 7 - bitIndex) & b);
            bytes[byteIndex] = b;

            if (b == 0 && byteIndex == maxByteIndex) {
                for (int index = byteIndex - 1; index >= 0; index--) {
                    if (bytes[index] == 0) {
                        maxByteIndex--;
                    } else {
                        break;
                    }
                }
            }
        }

    }

    public int getBit(int offset) {
        if (offset < 0 || offset >= capacity) {
            throw new IllegalArgumentException("offset out of range");
        }

        //确定offset所在的byte的下标
        int byteIndex = (offset / 8);
        byte b = bytes[byteIndex];

        //确定bit在byte八个bit中的位置
        int bitIndex = (offset % 8);
        return b >>> (7 - bitIndex) & 1;
    }

    /**
     * 获取1的数量
     *
     * @return
     */
    public int bitCount() {
        int c = 0;
        for (int i = 0; i <= maxByteIndex; i++) {
            byte b = bytes[i];
            while (b != 0) {
                c += b & 1;
                b >>>= 1;//无符号右移
            }
        }

        return c;

    }


    //这下跟redis的完全一致了 哈哈哈哈
    @Override
    public String toString() {
        char[] chars = new char[maxByteIndex + 1 << 1];
        for (int i = 0; i <= maxByteIndex; i++) {
            byte b = bytes[i];
            chars[i << 1] = DIGITS[b >>> 4 & 0xF];
            chars[(i << 1) + 1] = DIGITS[b & 0xF];
        }
        return new String(chars);
    }

    private final static char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 经常看到redis中bitmap 今天花时间看看到底是个什么鬼
     * <link>https://blog.csdn.net/u011957758/article/details/74783347</link>
     */
    public static void main(String[] args) {
//        System.err.println(1L<<32);

//        System.err.println(14 ^ 1);
//        countOne();
        BitMap bitMap = new BitMap(100);
        bitMap.setBit(9, 1);
        System.err.println(bitMap);
        assert bitMap.getBit(9) == 1;
        assert bitMap.getBit(7) == 0;
        bitMap.setBit(8, 1);
        assert bitMap.getBit(8) == 1;
        bitMap.setBit(7, 1);
        bitMap.setBit(8, 0);
        bitMap.setBit(7, 0);
        bitMap.setBit(9, 0);
        bitMap.setBit(99, 1);
        bitMap.setBit(104, 1);
//        System.err.println(bitMap);
//        String s = "Hello World";
//        byte[] bytes = s.getBytes();

//        for (int b = 0; b < 256; b++) {
//            bytes[b] = (byte) b;
//        }
//
//        System.err.println(new String(bytes));
//        System.err.println(toBinaryString(bytes));
//
        bitMap.setBit(1 << 2, 1);
        System.err.println(bitMap.getBit(1 << 2));
        System.err.println(bitMap);
//        for (int i = 0; i < 1000; i++) {
//            bitMap.setBit(i, 1);
//            assert bitMap.bitCount() == i + 1;
//            System.err.println(bitMap);
//        }
//
//
//        byte[] bytes = new byte[]{10, 0, -128, 89};
//        System.err.println(bitMap.toBinaryString(bytes));
//        System.err.println(Integer.toHexString(Integer.MAX_VALUE));
//
//        byte b = -128;
//        int bb = b;
//        System.err.println(bb);
//        for (byte b:bytes){
//            String s=bitMap.toBinaryString(bytes);
//        }

    }

    private String toBinaryString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(" ").append(toBinaryString(b));
        }
        return sb.toString();
    }

    private String toBinaryString(byte b) {
        char[] chars = new char[8];
        for (int i = chars.length - 1; i >= 0; i--) {
            int v = b >>> chars.length - 1 - i & 1;
            chars[i] = (char) ('0' + v);
        }
        return new String(chars);
    }


    /**
     * 计算一个数字的二进制形式中1的个数
     * https://www.cnblogs.com/graphics/archive/2010/06/21/1752421.html
     */

    private static void countOne() {
        Random random = new Random();
        int n = random.nextInt(100);
        int c = countOne(n);
        int ic = iterCount(n);

        System.err.println("count " + c + " " + (c == ic));
    }

    //需要遍历最高位次数
    private static int countOne(int n) {
        int c = 0;
        while (n > 0) {
            c += n & 1;
            n >>= 1;
        }
        return c;
    }

    private static int iterCount(int n) {
        String s = Integer.toBinaryString(n);
        System.err.println(s);
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') {
                count++;
            }
        }
        return count;
    }
}

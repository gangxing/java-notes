package com.learn.concurrent.threadpool;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName BitOperation
 * @Description 位运算基础
 * @Author xianjun@ixiaopu.com
 * @Date 12/11/2018 22:43
 */
public class BitOperation {


    /*
     * 二进制码基础知识
     * @see https://blog.csdn.net/vickyway/article/details/48788769
     * 原码：
     * 符号位加上真值的绝对值
     * 计算76-10==66
     * 十进制     二进制
     *     76       01001100
     * +  -10       10001010
       ---------------------
     *     66       11010110（-86）
     *
     * 反码：
     * 因为原码不能解决减法运算，所以设计了反码
     * 符号位不变，其余为取反（0->1;1->0）
     *    十进制     二进制原码    二进制反码
     *     15      00001111    00001111
     * + -125      11111101    10000010
     *---------------------------------
     *   -110      11101110    10010001
     * 得到10010001(反码)==11101110(原码)==-110，正确。
     * 注意：使用反码计算得到的结果也是反码，需要再次转换成原码。
     *
     * 使用反码进行减法计算时存在两个问题
     * 1.当计算结果溢出时 需要舍弃最高位 在最低位加1 使得运算多了一步 降低了运算效率
     * 2.计算结果为0时，存在+0和-0两种码
     *
     *
     * 补码：
     * 补码=反码+1
     *
     * 减法可以将减数转换成其补数 然后进行加法计算 其结果与真实结果同余 认为是相等
     *
     * 在计算机计算过程中 正数的运算 直接用原码即可
     * 负数计算 需要将负数转换成其补数 才能计算
     * 为了统一 正数的计算也采用补数计算
     * 所以正数的反码和补码都等于原码
     * 计算机中存储的都是补码
     *
     * 补码的补充点
     * 7bit可以表示0~127这128个数字
     * 算上符号位 可以表示-127~127这255个数字
     * 但实际上8bit可以表示256个数字 因为-0  和0 同一个数字占了两个码
     * 10000000（补）其原码也是10000000
     * 所以人为规定10000000就是-128
     */

    /*
     * 补码的基础知识
     *
     * 模:一个轮回 比如12生肖 60甲子 钟表1圈
     *
     * 互补:两个数相加等于模,这两个数互补
     *
     * 同余:两个数除以模,余数相等
     *
     * 推导出的规律:使用互补值计算出的结果与实际值是同余关系。
     *
     */


    /*
     * 使用位运算的目的：直接操作Bit 更接近底层 运算更快 如果用于高度重复逻辑中 对系统性能（开销）会有
     * 明显的效果
     *
     * & 按位与 相同位 两个同为1时 结果才为1 ，另一种表述：只要有一个0 结果都为0
     * 其作用呢？？？？？使用场景呢？？？？？
     * 为帮助记忆 可以定义0为false 1为true &的规则可以套用布尔运算 两个都为true时 结果才为true
     * 暂且成为刚子记忆法
     * 用途：判断一个数的奇偶性
     *
     * | 按位或
     * 套用刚子记忆法 只有两个都为0（false）时 结果才为0(false)
     *
     * ~ 按位非
     * 求反码的规则0-->1;1-->0
     *
     * ^ 按位亦或
     * 对应位 相同为0，不同则为1
     * 刚子瞎jb记忆法：相同 找到了起点 所以圆满了
     *
     * 异或有什么卵用呢 最经典的场景 交换两个变量的值 不再需要中间变量
     *   a = a ^ b;
     *   b = a ^ b;
     *   a = a ^ b;
     *
     *
     * << 左位移
     * 符号位不变 高位溢出截断 低位补0
     * 可以计算 m << n == m * 2 ^ n
     *
     * >> 右位移
     * 符号位不变 低位溢出 高位补符号位
     *
     * >>> 无符号右位移
     * 所谓无符号 指不把符号位当做符号（当做值）参与移动
     * 低位溢出 高位补0
     * -1 >>> 1 ==Integer.MAX_VALUE 但是并没有什么卵用
     *
     * Java使用补码来表示二进制数。在补码表示中，最高位（左边第一位）为符号位，0表示正数；1表示负数
     *
     */


    //在计算机中 所谓的二进制 都是以补码表示
    private static String int2BinaryStr(int i) {
        String b = Integer.toBinaryString(i);
        StringBuilder sb = new StringBuilder(b);
        for (int k = 0; k < 32 - b.length(); k++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    private static int binaryStr2int(String binary) {
        return Integer.parseInt(binary, 2);
    }

    private static boolean isEven(int n) {
        return (n & 1) == 0;
    }

    private static void swap(int a, int b) {
        System.err.println("a=" + a + ";b=" + b);

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.err.println("a=" + a + ";b=" + b);

    }

    public static void main1(String[] args) {
        int i = -3;
//        String binaryStr=int2BinaryStr(i);
//        System.err.println(binaryStr);

//        int r=binaryStr2int("11010110");
        int a = 8, b = 9;
        swap(a, b);

        int x = 1;
        System.err.println(i << 3);

        int COUNT_BITS = Integer.SIZE - 3;//29
        System.err.println(COUNT_BITS);

//-1 1111111111...11111111
        int running = -1 << COUNT_BITS;

        //running 111000000000...000000

        System.err.println(Integer.toBinaryString(running));//111

        int shutdown = 0 << COUNT_BITS;//000...0000000
        System.err.println(Integer.toBinaryString(shutdown));//000


        int stop = 1 << COUNT_BITS;//0010
        System.err.println(Integer.toBinaryString(stop));//001

        int tidying = 2 << COUNT_BITS;//0100
        System.err.println(Integer.toBinaryString(tidying));//010

        int terminated = 3 << COUNT_BITS;//0110
        System.err.println(Integer.toBinaryString(terminated));//011


        int CAPACITY = (1 << COUNT_BITS) - 1;

        System.err.println(CAPACITY);
    }

    public static void main2(String[] args) {
        /*
         * 利用位运算将字符串类型的ip地址转换成一个数字存储
         * 因为有四段 所以每段用8位来存储
         * 搞了半天 终于搞出来了 位运算果然很有意思 哈哈哈哈哈
         */
        int count = 0;
        for (int i = 0; i < 10000; i++) {
            String ip = randomIp();
//        String ip = "239.202.146.90";

            Integer res = encodeIp(ip);
            if (res < 0) {
                count++;
            }
            String decode = decodeIp(res);
            System.err.println(ip + "-->" + res + " 解析" + (ip.equals(decode) ? "成功" : "失败") + " " + decode);
            System.err.println(count);
        }
    }

    public static void main(String[] args) {
//        for (int i=-10;i<100;i++){
//            boolean isEven=isEven(i);
//            System.err.println(i+(isEven?"是":"不是")+"偶数");
//        }
        System.err.println(int2BinaryStr(Integer.MAX_VALUE*2));
    }

    static Random random = new Random();

    private static String randomIp() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(255));
            if (i < 3) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    private static final Integer[] BASE_ARR = {
            255 << 24,
            255 << 16,
            255 << 8,
            255
    };


    private static Integer encodeIp(String ip) {
        String[] arr = ip.split("\\.");
        Integer[] arrNum = new Integer[4];
        int r = 0;
        for (int i = 0; i < arr.length; i++) {
            arrNum[i] = Integer.parseInt(arr[i]);
            int res = arrNum[i] << 24 - 8 * i;
            r |= res;
        }
        return r;

    }

    private static String decodeIp(Integer integer) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BASE_ARR.length; i++) {
            int res = (BASE_ARR[i] & integer) >>> 24 - 8 * i;
            sb.append(res);
            if (i < BASE_ARR.length - 1) {
                sb.append(".");
            }
        }
        return sb.toString();
    }


}

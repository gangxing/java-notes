package com.learn.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName OneZeroPacket
 * @Description 动态规划之01背包问题
 * @Author xgangzai@gmail.com
 * @Date 2019/8/9 11:15
 */
public class OneZeroPacket {


    public static void main(String[] args) {
        int c = 10;//背包容量
        int n = 3;//宝石个数
        int[] volume = {0, 5, 4, 3};//宝石重量
        int[] worth = {0, 20, 10, 12};//宝石价值
        //TODO 这是什么意思？
        int[][] d = new int[n + 1][c + 1];

//        d[0][c] = 0;
//        int n = 3;//初始状态 假设全部装入 逐个剔除
        for (int i = 0; i <= n; i++) {
            for (int j = 1; j <= c; j++) {
                if (i > 0 && j >= volume[i]) {
                    d[i][j] = Math.max(d[i - 1][j], d[i - 1][j - volume[i]] + worth[i]);
                    print(d);
                }
            }
        }
    }

    //一列代表一个数组元素
    private static void print(int[][] arr){
        System.err.println("------------------------------");
        for (int i=0;i<arr[0].length;i++) {
            StringBuilder sb=new StringBuilder();
            for (int j=0;j<arr.length;j++){
                sb.append(arr[j][i]).append(", ");
            }
            System.err.println(sb);
        }

        System.err.println("------------------------------");

    }


    private static class Packet {

        int maxWeight;

        List<Diamond> diamonds;

        public Packet(int maxWeight) {
            this.maxWeight = maxWeight;
            this.diamonds = new ArrayList<>();
        }

        public Packet add(Diamond diamond) {
            Diamond sum = sumDiamond();
            if (sum.weight + diamond.weight > maxWeight) {
                throw new IllegalArgumentException("放不下啦");
            }

            diamonds.add(diamond);

            return this;
        }

        public Diamond sumDiamond() {
            Diamond diamond = new Diamond();
            for (Diamond d : diamonds) {
                diamond.weight += d.weight;
                diamond.value += d.value;
            }
            return diamond;
        }
    }


    private static class Diamond {
        //重量
        int weight;

        //价值
        int value;

        static Diamond random() {
            Random random = new Random();
            Diamond diamond = new Diamond();
            diamond.weight = random.nextInt(100);
            diamond.value = random.nextInt(100);
            return diamond;
        }
    }
}

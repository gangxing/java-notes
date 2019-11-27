package com.learn.algorithm;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/27 15:46
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 12个外表一样的小球，其中11个球重量相同，一个球为坏球（重量可能偏大可能偏小），
 * 用天平秤找出这个坏球，需要比较几次能保证找出这个坏球？
 * 答案，最多只用三次
 * https://blog.csdn.net/Iamthedoctor123/article/details/79463150
 */
public class BadBallFinder {

    static int badIndex;

    static int count = 0;


    public static void main(String[] args) {
        BadBallFinder finder = new BadBallFinder();
        Bag whole = finder.random();

        int find = finder.find(whole);

        System.err.println(find == badIndex);

        System.err.println("bad ball[" + find + "] found int [" + count + "] times");
    }


    private int find(Bag bag) {
        //分成三组
        Bag bag1 = bag.fork(4);
        Bag bag2 = bag.fork(4);
        Bag bag3 = bag.fork(4);

        Bag bad = null;
        if (bag1.sameWeight(bag2)) {

        }


        return 0;
    }


    private static class Bag {
        List<Ball> balls;

        public Bag(List<Ball> balls) {
            this.balls = balls;
        }

        private int totalWeight() {
            int w = 0;
            for (Ball ball : balls) {
                w += ball.weight;
            }
            return w;
        }

        Bag fork(int count) {
            List<Ball> newList = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                newList.add(this.balls.remove(0));
            }
            return new Bag(newList);
        }

        public boolean sameWeight(Bag other) {
            return totalWeight() == other.totalWeight();
        }
    }

    private static class Ball {
        int weight;

        public Ball(int weight) {
            this.weight = weight;
        }
    }

    private Bag random() {
        int weight = 10;
        int badWeight = 11;
        badIndex = new Random().nextInt(12);
        List<Ball> list = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            list.add(new Ball(weight));
        }
        list.set(badIndex, new Ball(badWeight));
        return new Bag(list);
    }
}

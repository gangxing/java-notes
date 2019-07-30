package com.learn.jdk;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * @ClassName Game
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/26 11:51
 */
public class Game1 extends JFrame {


    private Ball ball = new Ball(100, 100, 30, Color.RED);

    public static void main(String[] args) {
        Game1 game1 = new Game1();
        game1.lauch();

    }

    private void lauch() {
        this.setTitle("移动小球");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        this.setVisible(true);

        Move move = new Move(ball);
        new Thread(new Paint(move)).start();
        new Thread(new RandomColor(ball)).start();

    }

    @Override
    public void paint(Graphics graphics) {
        System.err.println(Thread.currentThread().getName() + "调用了paint()");
        ball.draw(graphics);
    }

    private class Paint implements Runnable {
        private Move move;

        public Paint(Move move) {
            this.move = move;
        }

        public void run() {
            while (true) {
                move.move();
                repaint();
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class Ball {
        int x;

        int y;

        int radius;

        Color color;

        Ball(int x, int y, int radius, Color color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
        }

        void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x, y, radius, radius);
        }

    }

    private class RandomColor implements Runnable {

        private Ball ball;


        public RandomColor(Ball ball) {
            this.ball = ball;
        }

        @Override
        public void run() {
            while (true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //随机改变颜色
                Random random = new Random();
                int r = random.nextInt(256);
                int g = random.nextInt(256);
                int b = random.nextInt(256);

                ball.color = new Color(r, g, b);

            }
        }
    }

    private class Move {

        private Ball ball;

        Move(Ball ball) {
            this.ball = ball;
        }

        void move() {
            randomX();
            randomY();
        }

        private void randomX() {
            Random random = new Random();
            int xincrease = random.nextInt(10);
            boolean increase = random.nextBoolean();
            int newx;
            if (increase) {
                newx = ball.x + xincrease;
                if (newx + ball.radius >= 500) {
                    newx = 500 - newx;
                }
            } else {
                newx = ball.x - xincrease;
                if (newx - ball.radius <= 0) {
                    newx = -newx;
                }
            }
            ball.x = newx;
        }

        private void randomY() {
            Random random = new Random();
            int xincrease = random.nextInt(10);
            boolean increase = random.nextBoolean();
            int newy;
            if (increase) {
                newy = ball.y + xincrease;
                if (newy + ball.radius >= 500) {
                    newy = 500 - newy;
                }
            } else {
                newy = ball.y - xincrease;
                if (newy - ball.radius <= 0) {
                    newy = -newy;
                }
            }
            ball.y = newy;
        }
    }
}

package com.learn.jdk;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Game2 {

    private JFrame frame = new JFrame("移动小球");

    private Ball ball = new Ball(100, 100, 30, Color.RED);

    private BallCanvas canvas = new BallCanvas();


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
    }

    class BallCanvas extends Canvas {

        @Override
        public void paint(Graphics g) {
            g.setColor(ball.color);
            g.fillOval(ball.x, ball.y, 2 * ball.radius, 2 * ball.radius);
        }
    }

    class RandomColor {

        void random() {
            Random random = new Random();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);

            ball.color = new Color(r, g, b);

            canvas.repaint();

        }
    }


    class Move {
        int minStep = 0;

        int maxStep = 10;


        void move() {
            randomX();
            randomY();
            canvas.repaint();
        }


        void randomX() {
            Random random = new Random();
            int xincrease = minStep + random.nextInt(maxStep - minStep);
            boolean increase = random.nextBoolean();
            int newx;
            int d = 2 * ball.radius;
            if (increase) {
                newx = ball.x + xincrease;
                int c = newx + d - 200;
                if (c > 0) {
                    ball.x = 200 - d;
                    canvas.repaint();
                    newx = 200 - d - c;
                }
            } else {
                newx = ball.x - xincrease;
                int c = newx;
                if (c < 0) {
                    ball.x = 0;
                    canvas.repaint();
                    newx = -c;
                }
            }
            ball.x = newx;

        }

        void randomY() {
            Random random = new Random();
            int xincrease = minStep + random.nextInt(maxStep - minStep);
            boolean increase = random.nextBoolean();
            int newy;
            int d = 2 * ball.radius;
            if (increase) {
                newy = ball.y + xincrease;

                int c = newy + d - 200;
                if (c > 0) {
                    ball.y = 200 - d;
                    canvas.repaint();
                    newy = 200 - d - c;
                }
            } else {
                newy = ball.y - xincrease;
                int c = newy - d;
                if (c < 0) {
                    ball.y = ball.radius;
                    canvas.repaint();
                    newy = -c;
                }
            }

            ball.y = newy;


        }

    }

    void start() {
        Move move = new Move();
        Timer moveTimer = new Timer(100, e -> move.move());

        RandomColor randomColor = new RandomColor();

        Timer colorTimer = new Timer(1000, e -> randomColor.random());

        frame.add(canvas);

        frame.pack();

        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);

        moveTimer.start();
        colorTimer.start();
    }

    public static void main(String[] args) {
        Game2 game = new Game2();
        game.start();
    }


}

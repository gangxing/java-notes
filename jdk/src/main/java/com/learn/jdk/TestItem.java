package com.learn.jdk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @ClassName TestItem
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/26 10:12
 */
public class TestItem extends JFrame {

    ImageIcon image = new ImageIcon("mainBack.jpg");
    Bullet b = new Bullet(80,80);

    public static void main(String[] args) {
        new TestItem().lauchFrame();
    }

    public void lauchFrame() {
        this.setTitle("坦克大战");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        new Thread(new paintThread()).start();
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
//        super.paint(g);
        g.drawImage(image.getImage(), 0, 0, null);
        b.draw(g);
    }

    private class paintThread implements Runnable {
        public void run() {
            while(true) {
                repaint();
                try {
                    Thread.sleep(80);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class Bullet {
        private int x;
        private int y;
        private static final int width = 16;
        private static final int height = 16;
        private static final int speed = 8;
        Direction dir = Direction.D;
        private boolean bU = false,bR = false,bD = false,bL = false;

        public Bullet(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }
        public void setX(int x) {
            this.x = x;
        }
        public int getY() {
            return y;
        }
        public void setY(int y) {
            this.y = y;
        }

        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.yellow);
            g.fillOval(x, y, width, height);
            g.setColor(c);

            move();
        }

        private void move() {
            switch(dir) {
                case U:
                    y -= speed;
                    break;
                case R:
                    x += speed;
                    break;
                case D:
                    y += speed;
                    break;
                case L:
                    x -= speed;
                    break;
            }
        }

        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
            }

            licalDirection();
        }

        private void licalDirection() {
            if(bU && !bR && !bD && !bL) {
                dir = Direction.U;
            } else if(!bU && bR && !bD && !bL) {
                dir = Direction.R;
            } else if(!bU && !bR && bD && !bL) {
                dir = Direction.D;
            } else if(!bU && !bR && !bD && bL) {
                dir = Direction.L;
            }
        }

        public void keyReleased(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
            }
            licalDirection();
        }
    }

    public enum Direction {
        U,R,D,L;
    }




}

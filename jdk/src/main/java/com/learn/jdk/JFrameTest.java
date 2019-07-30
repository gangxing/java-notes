package com.learn.jdk;

import javax.swing.*;
import java.awt.*;

/**
 * @ClassName JFrameTest
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/26 10:05
 */
public class JFrameTest extends JFrame {

    //定义移动变量
    int x = 0;
    int y = 0;
    int m = 1;
    int n = 1;

    //主函数
    public static void main(String[] args) {
        new JFrameTest();
    }

    //使用构造器创建窗体并设置
    public JFrameTest() {
        this.setVisible(true);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(3);
        this.setLocation(400, 100);
        this.setResizable(false);
        this.setTitle("测试框架");
        move();
    }

    //重写画图方法
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.fillOval(x, 25 + y, 50, 50);
    }

    //定义小球移动轨迹
    public void move() {
        while (true) {
            x += m;
            y += n;
            if (x >= 450) {
                m = -m;
            }
            if (x < 0) {
                m = 1;
            }
            if (y >= 425) {
                n = -n;
            }
            if (y < 0) {
                n = 1;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }
}

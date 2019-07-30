package com.learn.jdk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * @ClassName AWTTest
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/26 13:32
 */
public class AWTTest extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        // super.windowClosing(e);
        System.exit(0);
    }

    private Random rand = new Random(); // 随机序列种子

    private Frame f = new Frame("Welcome to Ball Game!"); // 框架窗口
    private Button btn = new Button("Restart");

    // 球桌的配置
    // 球桌的宽和高
    private final int TABLE_WIDTH = 300;
    private final int TABLE_HEIGHT = 400;
    private BallCanvas table = new BallCanvas();

    // 小球的配置
    private final int BALL_SIZE = 16; // 球的直径
    // 小球的起始位置，要保证在球桌范围内（球的左上角）
    private int ballX; // = rand.nextInt(200) + 20;
    private int ballY; // = rand.nextInt(10) + 20;
    // 小球的启示速率（为单位时间在两个方向上运动多少距离）
    // 其中x和y方向上稍有比率差别，这样更加和谐（模拟随机过程）
    // 负值表示反向
    private int ySpeed = 10;
    private int xSpeed = (int)(ySpeed * 2.0 * (rand.nextDouble() - 0.5));

    // 球拍的配置
    // 球拍的宽和高
    private final int RACKET_WIDTH = 60;
    private final int RACKET_HEIGHT = 20;
    // 球拍的起始位置（矩形左上角），要保证在球桌范围之内
    private int racketX; // = rand.nextInt(200);
    private final int racketY = 340; // 球拍的Y坐标固定在340的位置
    private int racketSpeed = 10; // 球拍移动速度固定为10，球拍只横向运动

    private boolean isLose = false; // 是否输球

    Timer timer;

    class BallCanvas extends Canvas {

        @Override
        public void paint(Graphics g) {
            // TODO Auto-generated method stub
            // super.paint(g);
            if (isLose) {
                g.setColor(new Color(255, 0, 0));
                g.setFont(new Font("Times", Font.BOLD, 30));
                g.drawString("Game Over!", 50, 200);
            }
            else {
                int x = ballX;
                int y = ballY;

                if (ballX < 0) {
                    x = 0;
                }
                if (ballX + BALL_SIZE > TABLE_WIDTH) {
                    x = TABLE_WIDTH - BALL_SIZE;
                }
                if (ballY <= 0) {
                    y = 0;
                }
                if (ballY + BALL_SIZE > racketY &&
                        ballX + BALL_SIZE / 2 >= racketX &&
                        ballX + BALL_SIZE / 2 <= racketX + RACKET_WIDTH) {
                    y = racketY - BALL_SIZE;
                }

                g.setColor(new Color(240, 240, 80));
                g.fillOval(x, y, BALL_SIZE, BALL_SIZE);

                g.setColor(new Color(80, 80, 200));
                g.fillRect(racketX, racketY, RACKET_WIDTH, RACKET_HEIGHT);
            }
        }

    }

    private void initTable() {
        ballX = rand.nextInt(200) + 20;
        ballY = rand.nextInt(10) + 20;
        racketX = rand.nextInt(200);

        isLose = false;
        timer.start();
    }

    public void init() {

        f.addWindowListener(this);

        KeyAdapter keyCtrl = new KeyAdapter() { // 监听左右按键控制球拍动向

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                // super.keyPressed(e);

                // 球桌宽度刚好是球拍速度的整数倍，因此不用判断是否出界
                // 只需要防止穿墙即可
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (racketX > 0) {
                        racketX -= racketSpeed;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (racketX + RACKET_WIDTH < TABLE_WIDTH) {
                        racketX += racketSpeed;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

        };
        f.addKeyListener(keyCtrl); // 窗口和球桌同时监听按键，两者任意一个获得焦点都可进行游戏
        table.addKeyListener(keyCtrl);

        timer = new Timer(100, e -> {
            // 检测横向撞击
            if (ballX <= 0 || ballX + BALL_SIZE >= TABLE_WIDTH) { // 横向撞边就要反向
                xSpeed = -xSpeed;
            }
            // 检测纵向撞击
            if (ballY <= 0 || // 撞顶边，接下来检测是否撞拍
                    ballY + BALL_SIZE >= racketY &&
                            ballY + BALL_SIZE <= racketY + RACKET_HEIGHT &&
                            ballX + BALL_SIZE / 2 >= racketX && // (2) 并且球心位置在球拍范围之内
                            ballX + BALL_SIZE / 2 <= racketX + RACKET_WIDTH) { // 满足这两个条件就代表撞拍了
                // 纵向撞边或拍也要反向
                ySpeed = -ySpeed;
            }

            // 是否输球
            if (ballY + BALL_SIZE > racketY + RACKET_HEIGHT) { // 只要球的下边沿超过拍的上边沿就输了
                isLose = true;
                timer.stop();
                table.repaint();
            }

            // 球位移然后重画
            ballX += xSpeed;
            ballY += ySpeed;
            table.repaint();
        });

        btn.addActionListener(e -> {
            table.requestFocus();
            initTable();
        });

        table.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        f.add(table);
        f.add(btn, BorderLayout.SOUTH);

        initTable();

        f.pack();
        f.setVisible(true);

        table.requestFocus();
    }

    public static void main(String[] args) {
        new AWTTest().init();
    }


}

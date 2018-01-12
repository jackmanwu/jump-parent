package com.jackmanwu.jump.ui;

import com.jackmanwu.jump.config.JumpSetting;
import com.jackmanwu.jump.util.ScreenshotUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 * Created by JackManWu on 2018/1/11.
 */
public class JumpUI {
    private static JFrame jFrame;

    private static final String title = "跳一跳";

    private int fw = 0;

    private int fh = 0;

    public static Stack<BufferedImage> imgStack = new Stack<>();

    public JumpUI() throws Exception {
        ScreenshotUtil.screenshot();
        BufferedImage image = ScreenshotUtil.getImage();
        if (image == null) {
            System.out.println("image获取为空...");
            return;
        }
        if (!initUIConfig(image)) {
            System.out.println("初始化失败...");
            return;
        }
        imgStack.push(image);

        jFrame = new JFrame(title);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new BorderLayout(2, 2));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dimension.width - fw) / 2;
        int y = (dimension.height - fh) / 2;
        jFrame.setBounds(x, y, fw, fh);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jFrame.add(new ImageJPanel(fw, fh), BorderLayout.CENTER);
        jFrame.setVisible(true);
    }

    private boolean initUIConfig(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        String key = width + "x" + height;
        System.out.println("屏幕分辨率：" + key);
        Double zoomRate = JumpSetting.getZoomRate(key);
        if (zoomRate == null) {
            zoomRate = 0.4;
        }
        JumpSetting.setZoomRate(zoomRate);
        System.out.println("缩放系数：" + zoomRate);

        Double jumpRate = JumpSetting.getJumpRate(key);
        if (jumpRate == null) {
            jumpRate = 1.39;
        }
        JumpSetting.setJumpRate(jumpRate);
        System.out.println("跳跃系数：" + jumpRate);

        fw = width / 3;
        fh = height - (int) ((2 * width / 3) / zoomRate);
        System.out.println("宽：" + fw + "，高：" + fh);
        return true;
    }

    public static void refreshUI() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
            System.out.println("线程休眠失败...");
        }
        try {
            ScreenshotUtil.screenshot();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("重新获取截屏失败...");
            return;
        }
        BufferedImage image = ScreenshotUtil.getImage();
        if (image == null) {
            System.out.println("image获取为空...");
            return;
        }
        imgStack.push(image);
        jFrame.getComponent(0).validate();
        jFrame.getComponent(0).repaint();
    }
}

package com.jackmanwu.jump.auto;

import com.jackmanwu.jump.config.JumpSetting;
import com.jackmanwu.jump.util.ColorUtil;
import com.jackmanwu.jump.util.ScreenshotUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by JackManWu on 2018/1/12.
 */
public class JumpAuto {
    public void arithmetic() throws Exception {
//        while (true) {
        BufferedImage image = ScreenshotUtil.screenshot();
        if (image == null) {
            System.out.println("截屏图像为空...");
            return;
        }
        Point piecesPoint = getPiecePoint(image);
        System.out.println("棋子坐标：" + piecesPoint);
        Point targetPoint = getTargetCenterPoint(image, piecesPoint.y);
        System.out.println("目标坐标：" + targetPoint);
        jump(piecesPoint, targetPoint);
//            Thread.sleep(1000);
//        }
    }

    /**
     * 获取棋子坐标
     *
     * @param image
     * @return
     */
    private Point getPiecePoint(BufferedImage image) {
        int x = 0;
        int y = 0;
        for (int i = 300; i < image.getHeight(); i++) {
            int startX = 0;
            int endX = 0;
            for (int j = 50; j < image.getWidth(); j++) {
                int pixel = image.getRGB(j, i);
                int red = ColorUtil.getRed(pixel);
                int green = ColorUtil.getGreen(pixel);
                int blue = ColorUtil.getBlue(pixel);
                if (red > 50 && red < 55 && green > 50 && green < 55 && blue > 55 && blue < 65) {
                    if (startX == 0) {
                        startX = j;
                        endX = 0;
                    }
                } else if (endX == 0) {
                    endX = j;
                }

                if (red > 50 && red < 60 && green > 53 && green < 63 && blue > 95 && blue < 110) {
                    y = i;
                }
            }
            if (startX != 0 && x == 0) {
                x = (startX + endX) / 2;
            }
        }
        return new Point(x, y);
    }

    /**
     * 获取目标中心位置
     *
     * @param image
     * @return
     */
    private Point getTargetCenterPoint(BufferedImage image, int piecesY) {
        int topPixel = 0;
        int topY = 0;
        int bottomY = 0;
        int leftX = 0;
        int rightX = 0;

        for (int i = 300; i < piecesY; i++) {
            for (int j = 50; j < image.getWidth(); j++) {
                int currentPixel = image.getRGB(j, i);
                int pixel = image.getRGB(0, i);

                if (!ColorUtil.isSameRgb(currentPixel, pixel)) {
                    if (topY == 0) {
                        System.out.println("第一个顶点：" + i);
                        topY = i;
                        bottomY = i;
                        topPixel = image.getRGB(j, i);
                    }

                    if (topPixel != 0 && ColorUtil.isSameRgb(currentPixel, topPixel)) {
                        if (leftX == 0 || (j < leftX && leftX - j < 4)) {
                            leftX = j;
                        }
                    }

                    if (topPixel != 0 && ColorUtil.isSameRgb(image.getRGB(j - 1, i), topPixel)) {
                        if (rightX == 0 || (j - 1 > rightX && j - 1 - rightX < 4)) {
                            rightX = j - 1;
                        }
                    }
                    if (topPixel != 0 && ColorUtil.isSameRgb(image.getRGB(j, i - 1), topPixel)
                            && i - 1 > bottomY && i - 1 - bottomY < 2) {
                        bottomY = i - 1;
                    }
                }
            }
        }

        System.out.println("topY: " + topY + "," + "leftX: " + leftX + "," + "rightX: " + rightX + ",bottomY: " + bottomY);
        return new Point((leftX + rightX) / 2, (topY + bottomY) / 2);
    }

    /**
     * @param beginPoint
     * @param endPoint
     */
    private void jump(Point beginPoint, Point endPoint) {
        System.out.println("跳一跳");
        int dx = Math.abs(beginPoint.x - endPoint.x);
        int dy = Math.abs(beginPoint.y - endPoint.y);
        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        if (distance == 0) {
            System.out.println("距离为0...");
            return;
        }
        System.out.println("起点与终点距离：" + distance);
        int time = (int) (distance * JumpSetting.jumpRate);
        System.out.println("按压时间：" + time);
        try {
            ScreenshotUtil.screenTouch(time);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("模拟跳跃失败...");
        }
    }
}

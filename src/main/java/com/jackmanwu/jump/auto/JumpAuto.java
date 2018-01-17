package com.jackmanwu.jump.auto;

import com.jackmanwu.jump.util.ColorUtil;
import com.jackmanwu.jump.util.ScreenshotUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackManWu on 2018/1/12.
 */
public class JumpAuto {
    private static double jumpRate = 0;

    public void arithmetic() throws Exception {
        while (true) {
            BufferedImage image = ScreenshotUtil.screenshot();
//        BufferedImage image = ImageIO.read(new File(JumpSetting.BASE_DIR + JumpSetting.SCREENCAP_NAME));
            if (image == null) {
                System.out.println("截屏图像为空...");
                return;
            }
            if (jumpRate == 0) {
                jumpRate = 1.39 * 1080 / image.getWidth();
            }
            Point piecesPoint = getPiecePoint(image);
            System.out.println("棋子坐标：" + piecesPoint);
            piecesPoint.y -= 10;
            Point point = getTargetPoint(image, piecesPoint.y);
            System.out.println("目标坐标：" + point);
            getTargetPoint(image, piecesPoint.y);
            jump(piecesPoint, point);
            Thread.sleep(1000);
        }
    }

    /**
     * 获取棋子坐标
     *
     * @param image
     * @return
     */
    private Point getPiecePoint(BufferedImage image) {
        int y = 0;
        int totalX = 0;
        int total = 0;
        for (int i = 300; i < image.getHeight(); i++) {
            for (int j = 50; j < image.getWidth(); j++) {
                int pixel = image.getRGB(j, i);
                int red = ColorUtil.getRed(pixel);
                int green = ColorUtil.getGreen(pixel);
                int blue = ColorUtil.getBlue(pixel);

                if (red > 50 && red < 60 && green > 53 && green < 63 && blue > 95 && blue < 110) {
                    y = i;
                    totalX += j;
                    total++;
                }
            }
        }
        return new Point(totalX / total, y);
    }

    private Point getTargetPoint(BufferedImage image, int piecesY) {
        int minH = image.getHeight() / 6;
        int maxX = image.getWidth();

        int topPixel = 0;
        int topY = 0;
        int bottomY = 0;
        int leftX = 0;
        int rightX = 0;

        int whiteTopY = 0;
        int whiteBottomY = 0;
        int whiteLeftX = 0;
        int whiteRightX = 0;
        List<Point> list = new ArrayList<>();
        for (int i = minH; i < piecesY; i++) {
            int pixel = image.getRGB(0, i);
            for (int j = 0; j < maxX; j++) {
                int currentPixel = image.getRGB(j, i);
                //计算目标中心
                if (!ColorUtil.isSameRGB(pixel, image.getRGB(j, i))) {
                    if (topPixel == 0) {
                        topPixel = currentPixel;
                        topY = i;
                        bottomY = i;
                        leftX = j;
                        rightX = j;
                        continue;
                    }

                    if (ColorUtil.isSameRGB(currentPixel, topPixel, 5)) {
                        if (j < leftX && leftX - j < 4) {
                            leftX = j;
                        }
                    }
                    if (ColorUtil.isSameRGB(image.getRGB(j - 1, i), topPixel, 5)) {
                        if (j - 1 > rightX && j - 1 - rightX < 4) {
                            rightX = j - 1;
                        }
                    }
                    if (ColorUtil.isSameRGB(image.getRGB(j, i - 1), topPixel, 5)) {
                        if (i - 1 > bottomY && i - 1 - bottomY < 2) {
                            bottomY = i - 1;
                        }
                    }
                }

                //计算目标中心白点
                int red = ColorUtil.getRed(currentPixel);
                int green = ColorUtil.getGreen(currentPixel);
                int blue = ColorUtil.getBlue(currentPixel);
                if (topPixel != 0
                        && ColorUtil.isSameRGB(currentPixel, 245, 245, 245, 4)
                        && red <= 245 && green <= 245 && blue <= 245) {
                    if (ColorUtil.isSameRGB(image.getRGB(j - 1, i), topPixel, 5)
                            && ColorUtil.isSameRGB(image.getRGB(j, i - 1), topPixel, 5)
                            && whiteTopY == 0) {
                        whiteTopY = i;
                        whiteTopY = i;
                        whiteLeftX = j;
                        whiteRightX = j;
                    }
                    if (ColorUtil.isSameRGB(image.getRGB(j - 1, i), topPixel, 5)) {
                        if (j < whiteLeftX) {
                            whiteLeftX = j;
                        }
                    }
                    if (ColorUtil.isSameRGB(image.getRGB(j + 1, i), topPixel, 5)) {
                        if (j > whiteRightX) {
                            whiteRightX = j;
                        }
                    }
                    if (ColorUtil.isSameRGB(image.getRGB(j, i + 1), topPixel, 5)) {
                        if (i > whiteBottomY) {
                            whiteBottomY = i;
                        }
                    }
                }
            }
        }
        /*int totalX = 0;
        int totalY = 0;
        for (Point point : list) {
            totalX += point.x;
            totalY += point.y;
        }
        int len = list.size();*/
        if (whiteTopY != 0) {
            return new Point((whiteLeftX + whiteRightX) / 2, (whiteTopY + whiteBottomY) / 2);
        }
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
        int time = (int) (distance * jumpRate);
        System.out.println("按压时间：" + time);
        try {
            ScreenshotUtil.screenTouch(time);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("模拟跳跃失败...");
        }
    }
}

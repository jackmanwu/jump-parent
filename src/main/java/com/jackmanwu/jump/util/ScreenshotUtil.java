package com.jackmanwu.jump.util;

import com.jackmanwu.jump.config.JumpSetting;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

/**
 * Created by JackManWu on 2018/1/11.
 */
public class ScreenshotUtil {
    /**
     * 截屏并拉取到本地
     */
    public static BufferedImage screenshot() throws Exception {
        int code = AdbUtil.adbExec("shell", "screencap", "-p", JumpSetting.SCREENCAP_PATH);
        if (code != 0) {
            System.out.println("截屏失败...");
            return null;
        }
        System.out.println("截屏成功...");
        Thread.sleep(1000);
        int pullCode = AdbUtil.adbExec("pull", JumpSetting.SCREENCAP_PATH, JumpSetting.BASE_DIR);
        if (pullCode != 0) {
            System.out.println("拉取截图失败...");
            return null;
        }
        Thread.sleep(1000);
        System.out.println("拉取截图成功");
        /*int delCode = AdbUtil.adbExec("shell", "rm", JumpSetting.SCREENCAP_PATH);
        if (delCode == 0) {
            System.out.println("删除手机端截图成功...");
        } else {
            System.out.println("删除手机端截图失败...");
        }*/
        File file = new File(JumpSetting.BASE_DIR + File.separator + JumpSetting.SCREENCAP_NAME);
        return ImageIO.read(file);
    }

    public static BufferedImage getImage() {
        try {
            File file = new File(JumpSetting.BASE_DIR + File.separator + JumpSetting.SCREENCAP_NAME);
            return ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取image失败...");
        }
        return null;
    }

    public static void screenTouch(int time) throws Exception {
        String x1 = new Random().nextInt(100) + "";
        String y1 = new Random().nextInt(100) + "";
        String x2 = new Random().nextInt(200) + "";
        String y2 = new Random().nextInt(200) + "";
        AdbUtil.adbExec("shell", "input", "swipe", x1, y1, x2, y2,String.valueOf(time));
    }
}

package com.jackmanwu.jump.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JackManWu on 2018/1/11.
 */
public class JumpSetting {
    public static String ADB_PATH = "D:\\softwares\\Java\\Android\\Sdk\\platform-tools\\adb.exe";//adb路径

    public static String SCREENCAP_DIR = "/sdcard/";//截图文件路径

    public static String SCREENCAP_NAME = "jump.png";//截图名称

    public static String SCREENCAP_PATH = SCREENCAP_DIR + SCREENCAP_NAME;//截图路径

    public static String BASE_DIR = System.getProperty("user.dir")+ File.separator;//本地文件路径

    public static Double jumpRate = 1.392;

    public static Double zoomRate;

    /**
     * 分辨率与按压时长比
     */
    private static final Map<String, Double> jumpRateMap = new HashMap<>();

    static {
        jumpRateMap.put("1080x1920", 1.392);
    }

    /**
     * 缩放比
     */
    private static final Map<String, Double> zoomRateMap = new HashMap<>();

    static {
        zoomRateMap.put("1080x1920", (double) ((float) 1080 / (float) 1920));
    }

    public static Double getJumpRate(String key) {
        return jumpRateMap.get(key);
    }

    public static void setJumpRate(Double jumpRate) {
        JumpSetting.jumpRate = jumpRate;
    }

    public static Double getZoomRate(String key) {
        return zoomRateMap.get(key);
    }

    public static void setZoomRate(Double zoomRate) {
        JumpSetting.zoomRate = zoomRate;
    }
}

package com.jackmanwu.jump.util;

/**
 * Created by JackManWu on 2018/1/12.
 */
public class ColorUtil {
    public static int getRed(int pixel) {
        return offset(pixel >> 16);
    }

    public static int getGreen(int pixel) {
        return offset(pixel >> 8);
    }

    public static int getBlue(int pixel) {
        return offset(pixel);
    }

    private static int offset(int value) {
        return value & 0xff;
    }

    public static boolean isSameRgb(int rgb1, int rgb2) {
        int redDiff = Math.abs(getRed(rgb1) - getRed(rgb2));
        int greenDiff = Math.abs(getGreen(rgb1) - getGreen(rgb2));
        int blueDiff = Math.abs(getBlue(rgb1) - getBlue(rgb2));
        if (redDiff + greenDiff + blueDiff > 5) {
            return false;
        }
        return true;
    }
}

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

    public static boolean isSameRGB(int rgb1, int rgb2, int offset) {
        int redDiff = Math.abs(getRed(rgb1) - getRed(rgb2));
        int greenDiff = Math.abs(getGreen(rgb1) - getGreen(rgb2));
        int blueDiff = Math.abs(getBlue(rgb1) - getBlue(rgb2));
        if (redDiff + greenDiff + blueDiff > offset) {
            return false;
        }
        return true;
    }

    public static boolean isSameRGB(int rgb1, int rgb2) {
        return isSameRGB(rgb1, rgb2, 0);
    }

    public static boolean isSameRGB(int color, int red, int green, int blue, int offset) {
        int diff = Math.abs(getRed(color) - red)
                + Math.abs(getGreen(color) - green)
                + Math.abs(getBlue(color) - blue);
        if (diff > offset) {
            return false;
        }
        return true;
    }

    public static int getPixel(int red, int green, int blue) {
        return (offset(red) << 16) + (offset(green) << 8) + offset(blue);
    }
}

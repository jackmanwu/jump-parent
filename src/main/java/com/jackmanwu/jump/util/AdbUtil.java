package com.jackmanwu.jump.util;

import com.jackmanwu.jump.config.JumpSetting;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JackManWu on 2018/1/11.
 */
public class AdbUtil {
    private static int exec(String... cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        return process.waitFor();
    }

    public static int adbExec(String... cmd) throws Exception {
        List<String> list = new LinkedList<>();
        list.add(JumpSetting.ADB_PATH);
        list.addAll(Arrays.asList(cmd));
        return exec(list.toArray(new String[list.size()]));
    }
}

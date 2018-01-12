package com.jackmanwu.jump.service.impl;

import com.jackmanwu.jump.config.JumpSetting;
import com.jackmanwu.jump.service.JumpService;
import com.jackmanwu.jump.util.ScreenshotUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * Created by JackManWu on 2018/1/11.
 */
@Setter
@Getter
public class ManualJumpService implements JumpService {
    @Override
    public void jump(Point beginPoint, Point endPoint) {
        System.out.println("跳一跳");
        int dx = Math.abs((beginPoint.x - endPoint.x) * 3);
        int dy = Math.abs((beginPoint.y - endPoint.y) * 3);
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

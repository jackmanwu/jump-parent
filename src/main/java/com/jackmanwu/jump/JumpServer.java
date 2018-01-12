package com.jackmanwu.jump;

import com.jackmanwu.jump.ui.JumpUI;

import java.util.concurrent.CountDownLatch;

/**
 * Created by JackManWu on 2018/1/10.
 */
public class JumpServer {
    public static void main(String[] args) throws Exception {
        new JumpUI();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
}

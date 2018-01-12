package com.jackmanwu.jump.ui;

import com.jackmanwu.jump.service.JumpService;
import com.jackmanwu.jump.service.impl.ManualJumpService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by JackManWu on 2018/1/11.
 */
public class ImageJPanel extends JPanel {
    private static Queue<Point> queue = new ArrayDeque<>();

    private int width;

    private int height;

    public ImageJPanel(int width, int height) {
        this.setBorder(BorderFactory.createTitledBorder("left click"));
        this.setRequestFocusEnabled(true);
        this.addMouseListener(onMouseListener());
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(JumpUI.imgStack.peek(), 0, 0, width, height, null);
        JumpUI.imgStack.empty();
    }

    private MouseListener onMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    System.out.println(e.getPoint());
                    queue.add(e.getPoint());
                    System.out.println("当前点击数：" + queue.size());
                    if (queue.size() == 2) {
                        JumpService jumpService = new ManualJumpService();
                        jumpService.jump(queue.poll(), queue.peek());
                        queue.clear();
                        JumpUI.refreshUI();
                    }
                }

                if (MouseEvent.BUTTON3 == e.getButton()) {
                    JumpUI.refreshUI();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }
}

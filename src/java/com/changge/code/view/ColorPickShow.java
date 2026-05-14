package com.changge.code.view;

import com.changge.code.core.exception.SystemException;
import com.changge.code.core.parser.ColorParser;
import sun.awt.datatransfer.DataTransferer;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class ColorPickShow extends JButton implements CComponent {

    private static volatile boolean picking = false;

    MainWindow mainWindow;

    int fontSize = 12;

    private static final String ID = "color_pick_show";

    private final Robot robot;

    @Override
    public String getID() {
        return ID;
    }
    public ColorPickShow(MainWindow mainWindow) {
        this.setVisible(true);
        this.mainWindow = mainWindow;
        this.fontSize = mainWindow.fontSize;

        this.setBounds(0,(mainWindow.getHeight() / 2) + 10,this.fontSize * 8,(int)(this.fontSize * 2));
        this.setText("点击获取");
        this.setToolTipText("点击开始获取屏幕颜色，再次点击结束获取，并自动复制进粘贴板，直接粘贴即可使用");
        final Color color = new Color(0,171,255);
        this.setBackground(color);
        this.setForeground(new Color(255,255,255));
        this.setFont(new Font("黑体",Font.BOLD,(int)(this.fontSize * 1.2)));
        this.setBorderPainted(false);
        this.robot = createRobot();
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!picking){
                    startPick();
                }else{
                    cancelTimeTask();
                }
            }
        });
    }

    private void startPick() {
            picking = true;
            this.setText("任意键停止");
            pickColor();
    }

    private Robot createRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new SystemException("颜色拾取器初始化失败");
        }
    }

    private void pickColor(){
        new Thread(() -> {
            while(picking){
                Point point = MouseInfo.getPointerInfo().getLocation();
                Color color = this.robot.getPixelColor(point.x,point.y);
                if(color != null){
                    SwingUtilities.invokeLater(() -> {
                        this.mainWindow.resetColor(color,getID());
                    });
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println("系统出现异常，需排查处理");
                }
            }
        }).start();
    }

    public void cancelTimeTask() {
        if(picking){
            picking = false;
            this.setText("点击获取");
            this.mainWindow.copyColor();
        }
    }
}

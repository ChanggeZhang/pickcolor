package com.changge.code.view;

import com.changge.code.core.exception.SystemException;
import com.changge.code.core.parser.ColorParser;
import sun.awt.datatransfer.DataTransferer;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class ColorPickShow extends JButton implements CComponent {

    MainWindow mainWindow;

    private static final String ID = "color_pick_show";

    Timer timer;

    private Robot robot;

    @Override
    public String getID() {
        return this.ID;
    }
    public ColorPickShow(MainWindow mainWindow) {
        this.setVisible(true);
        this.mainWindow = mainWindow;
        this.setBounds(0,(mainWindow.getHeight() / 2) + 10,getFont().getSize() * 8,(int)(getFont().getSize() * 2));
        this.setText("获取颜色");
        this.robot = createRobot();
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    startPick();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    private Robot createRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new SystemException("颜色拾取器初始化失败");
        }
    }

    private void startPick(){
        if(timer == null){
            this.setText("结束获取");
            final ColorPickShow that = this;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Color color = that.pickColor();
                    if(color != null){
                        that.mainWindow.resetColor(color,that.getID());
                    }
                }
            }, 0, 2000);
        }else{
            this.setText("开始获取");
            this.cancelTimeTask();
        }
    }

    private Color pickColor(){
        Point point = MouseInfo.getPointerInfo().getLocation();
        Color color = this.robot.getPixelColor(point.x,point.y);
        return color;
    }

    public void cancelTimeTask() {
        if(timer != null){
            timer.cancel();
            timer = null;
            this.mainWindow.copyColor();
        }
    }
}

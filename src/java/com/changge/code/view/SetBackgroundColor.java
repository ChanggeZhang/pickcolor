package com.changge.code.view;

import com.changge.code.core.enums.MouseClick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Changge Zhang
 * @date 2026/5/16 16:13
 */
public class SetBackgroundColor extends JButton implements CComponent{

    private static final String ID = "set_background_color";

    private final MainWindow mainWindow;

    int fontSize;

    public SetBackgroundColor(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.fontSize = mainWindow.fontSize;
        this.setText("应用");
        this.setToolTipText("将当前颜色应用为背景色");
        final Color color = new Color(0,171,255);
        this.setBackground(color);
        this.setForeground(new Color(255,255,255));
        this.setFont(new Font("黑体",Font.BOLD,(int)(this.fontSize * 1.2)));
        this.setBorderPainted(false);
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("click");
                if(e.getButton() == MouseClick.LEFT_CLICK.getCode()){
                    mainWindow.setAsBgColor();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("press");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("release");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("enter");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("exit");
            }
        });
    }

    @Override
    public String getID() {
        return ID;
    }
}

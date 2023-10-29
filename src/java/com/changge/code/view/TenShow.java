package com.changge.code.view;

import com.changge.code.core.config.GlobalConfig;
import com.changge.code.data.DataDefault;
import com.changge.code.utils.Assert;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TenShow extends JPanel  implements CComponent{

    MainWindow mainWindow;

    private static final String ID = "ten_show";

    JTextField red = new JTextField("red");
    JTextField green = new JTextField("green");
    JTextField blue = new JTextField("blue");
    
    int fontSize = 12;

    public TenShow(MainWindow mainWindow) {
        Assert.isNotNull(mainWindow);
        this.setVisible(true);
        this.mainWindow = mainWindow;
        this.fontSize = this.mainWindow.fontSize;
        this.setSize(new Dimension(this.fontSize * 12,2*this.fontSize));
        this.initComponents();
        this.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        this.setColor(null);
    }

    private void initComponents() {
        Border border = BorderFactory.createEmptyBorder();
        Dimension rgbArea = new Dimension((int)(2.7*this.fontSize),2*this.fontSize);
        red.setPreferredSize(rgbArea);
        green.setPreferredSize(rgbArea);
        blue.setPreferredSize(rgbArea);
        red.setBorder(border);
        green.setBorder(border);
        blue.setBorder(border);
        JLabel label = new JLabel("RGB：",SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(this.fontSize * 5,2*this.fontSize));
        this.add(label);
        this.add("rgb",red);
        this.add("green",green);
        this.add("blue",blue);
        this.addEvent();
    }

    public void setColor(Color color){
        if(color == null){
            color = DataDefault.defaultColor;
        }
        red.setText(color.getRed() + "");
        green.setText(color.getGreen() + "");
        blue.setText(color.getBlue() + "");
    }

    public void addEvent(){
        red.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                focusLose();
            }
        });
        green.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                focusLose();
            }
        });
        blue.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                focusLose();
            }
        });
    }


    private void focusLose(){
        int r = Integer.valueOf(this.red.getText());
        int g = Integer.valueOf(this.green.getText());
        int b = Integer.valueOf(this.blue.getText());
        this.mainWindow.resetColor(new Color(r,g,b),this.ID);
    }

    @Override
    public String getID() {
        return this.ID;
    }

    @Override
    public void resetColor(Color color) {
        this.setColor(color);
    }
}

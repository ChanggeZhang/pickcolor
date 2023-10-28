package com.changge.code.view;

import com.changge.code.core.config.GlobalConfig;
import com.changge.code.data.DataDefault;
import com.changge.code.utils.Assert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TenShow extends JPanel  implements CComponent{

    MainWindow mainWindow;

    GlobalConfig globalConfig = GlobalConfig.instance();

    private static final String ID = "ten_show";

    TextField red = new TextField("red");
    TextField green = new TextField("green");
    TextField blue = new TextField("blue");

    public TenShow(MainWindow mainWindow) {
        Assert.isNotNull(mainWindow);
        this.setVisible(true);
        this.mainWindow = mainWindow;
        this.setBounds(globalConfig.getWidth() / 3 + 20,0,getFont().getSize() * 8,getFont().getSize() + 10);
        this.add("rgb",red);
        this.add("green",green);
        this.add("blue",blue);
        this.setColor(null);

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

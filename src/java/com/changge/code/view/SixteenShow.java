package com.changge.code.view;

import com.changge.code.core.parser.ColorParser;
import com.changge.code.data.DataDefault;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SixteenShow extends JPanel  implements CComponent{

    MainWindow mainWindow;

    private static final String ID = "sixteen_show";

    TextField textField = new TextField();

    @Override
    public String getID() {
        return this.ID;
    }
    public SixteenShow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        this.add(textField);
        this.setVisible(true);
        this.setSize(getFont().getSize() * 7,(int)(getFont().getSize() * 1.2));
    }


    private void focusLose(){
        String hexColor = this.textField.getText();
        Color color = new ColorParser().parse("#" + hexColor);
        if(color == null){
            color = DataDefault.defaultColor;
        }
        this.mainWindow.resetColor(color,this.ID);
    }

    @Override
    public void resetColor(Color color) {
        textField.setText(ColorParser.toHexString(color));
    }
}

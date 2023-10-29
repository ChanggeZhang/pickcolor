package com.changge.code.view;

import com.changge.code.core.parser.ColorParser;
import com.changge.code.data.DataDefault;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SixteenShow extends JPanel  implements CComponent{

    MainWindow mainWindow;

    private static final String ID = "sixteen_show";

    JTextField textField = new JTextField();

    int fontSize = 12;

    @Override
    public String getID() {
        return this.ID;
    }
    public SixteenShow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.fontSize = this.mainWindow.fontSize;
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                focusLose();
            }
        });
        JLabel label = new JLabel("16进制：",SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(this.fontSize * 5,2*this.fontSize));
        this.add(label);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        Border border = BorderFactory.createEmptyBorder();
        textField.setBorder(border);
        textField.setPreferredSize(new Dimension(this.fontSize * 9,this.fontSize * 2));
        this.add(textField);
        this.setVisible(true);
        this.setSize(this.fontSize * 12,this.fontSize * 2);
    }


    private void focusLose(){
        String hexColor = this.textField.getText();
        Color color = ColorParser.parse("#" + hexColor);
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

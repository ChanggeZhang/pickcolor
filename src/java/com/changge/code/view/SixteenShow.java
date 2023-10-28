package com.changge.code.view;

import com.changge.code.core.parser.ColorParser;

import javax.swing.*;
import java.awt.*;

public class SixteenShow extends JPanel  implements CComponent{

    MainWindow mainWindow;

    private static final String ID = "sixteen_show";

    JTextField textField = new JTextField();

    @Override
    public String getID() {
        return this.ID;
    }
    public SixteenShow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.add(textField);
        this.setVisible(true);
        this.setSize(getFont().getSize() * 7,(int)(getFont().getSize() * 1.2));
    }

    @Override
    public void resetColor(Color color) {
        textField.setText(ColorParser.toHexString(color));
    }
}

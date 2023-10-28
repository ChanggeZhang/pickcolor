package com.changge.code.view;

import com.changge.code.data.DataDefault;
import com.changge.code.utils.Assert;

import javax.swing.*;
import java.awt.*;

public class ColorShowPanel  extends JPanel implements CComponent {



    MainWindow mainWindow;

    private static final String ID = "color_show";

    @Override
    public String getID() {
        return this.ID;
    }

    public ColorShowPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.setVisible(true);
        this.setBounds(0,0,34,34);
        this.setBackground(DataDefault.defaultColor);
        this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,60),1));
    }

    public Color setBackgroundColor(Color color){
        Assert.isNotNull(color);
        Color originColor = this.getBackground();
        this.setBackground(color);
        return originColor;
    }

    @Override
    public void resetColor(Color color) {
        Color color1 = this.setBackgroundColor(color);
        mainWindow.addColorRecord(color1);
    }
}

package com.changge.code.view;

import com.changge.code.data.DataDefault;
import com.changge.code.utils.Assert;

import javax.swing.*;
import java.awt.*;

public class ColorDiffPanel extends JPanel implements CComponent {



    MainWindow mainWindow;

    private Color lastColor;

    private static final String ID = "color_diff";

    @Override
    public String getID() {
        return ID;
    }

    public ColorDiffPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
//        this.setPreferredSize(new Dimension(60,60));
        // 这里的bounds在父类无效，需要在父类设置才能在父类中有效
//        this.setBounds(0,0,60,60);
        this.setBackground(DataDefault.defaultColor);
//        this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,60),1));
        this.setVisible(true);
    }

    public void setBackgroundColor(Color color){
        Assert.isNotNull(color);
        Color originColor = this.getBackground();
        this.setBackground(color);
        this.lastColor = originColor;
        this.repaint();
    }

    /**
     * swing 默认不会擦除原有内容，需要售罄清除
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g){
        g.clearRect(0,0,this.getWidth(),this.getHeight());
        super.paintComponent(g);
    }
}

package com.changge.code.view;

import com.changge.code.core.config.GlobalConfig;
import com.changge.code.core.enums.MouseClick;
import com.changge.code.core.parser.ColorParser;
import com.changge.code.data.DataDefault;
import com.changge.code.utils.Assert;
import com.changge.code.utils.ToolkitUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        this.setOpaque(false);
        this.fontSize = this.mainWindow.fontSize;
        this.setSize(new Dimension(this.fontSize * 12,2*this.fontSize));
        this.initComponents();
        this.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        this.setColor(null);
        this.setToolTipText("单击鼠标右键复制rgb颜色值");
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseClick.RIGHT_CLICK.getCode()){
                    copyColor(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
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
        label.setBackground(this.mainWindow.getBackground());
        this.add(label);
        this.add("rgb",red);
        this.add("green",green);
        this.add("blue",blue);
        this.addEvent();
    }

    private void copyColor(MouseEvent e) {
        String red = this.red.getText();
        String green = this.green.getText();
        String blue = this.blue.getText();
        String color = String.format("rgb(%s,%s,%s)", red,green,blue);
        ToolkitUtils.copy(color);
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

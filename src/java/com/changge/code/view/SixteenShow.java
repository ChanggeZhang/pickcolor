package com.changge.code.view;

import com.changge.code.core.enums.MouseClick;
import com.changge.code.core.parser.ColorParser;
import com.changge.code.data.DataDefault;
import com.changge.code.utils.ToolkitUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

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
        this.setOpaque(false);
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
        label.setBackground(this.mainWindow.getBackground());
        this.add(label);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        Border border = BorderFactory.createEmptyBorder();
        textField.setBorder(border);
        textField.setPreferredSize(new Dimension(this.fontSize * 9,this.fontSize * 2));
        this.add(textField);
        this.setVisible(true);
        this.setSize(this.fontSize * 12,this.fontSize * 2);
        this.setToolTipText("单击鼠标右键复制16进制颜色值");
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

    private void copyColor(MouseEvent e) {
        String color = "#" + this.textField.getText();
        ToolkitUtils.copy(color);
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

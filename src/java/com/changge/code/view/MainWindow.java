package com.changge.code.view;

import com.changge.code.core.config.GlobalConfig;
import com.changge.code.core.exception.SystemException;
import com.changge.code.core.parser.ColorParser;
import com.changge.code.data.DataDefault;
import com.changge.code.utils.StreamUtil;
import com.changge.code.utils.ToolkitUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame {

    private GlobalConfig config;

    private Container con;

    private JPanel jp;

    private JPanel recordPanel = new JPanel();

    private List<Color> record = new ArrayList<>();

    private Map<String,CComponent> components = new HashMap<>();


    public int fontSize;

    public MainWindow(GlobalConfig config){
        this.config = config;
        this.fontSize = this.config.getFontSize();
        this.init();
        this.setIconImage();
    }

    private void registryComponent() {
        this.con = this.getContentPane();
        this.jp = this.config.getBackgroundImage() != null ? createBackgroundImage() : new JPanel();
        this.jp.setBackground(this.getBackground());
        this.setForeground(ColorParser.diffColor(this.getBackground()));
        this.jp.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        this.components.put("colorPanel",new ColorShowPanel(this));
        this.components.put("color_diff",new ColorDiffPanel(this));
        this.components.put("colorPick",new ColorPickShow(this));
        this.components.put("tenShow",new TenShow(this));
        this.components.put("sixteenShow",new SixteenShow(this));
        this.components.put("set_background_color",new SetBackgroundColor( this));

        JPanel previewPanel = new JPanel();
        previewPanel.setLayout(null);
        previewPanel.setPreferredSize(new Dimension(60,60));

        // 获取两个子组件
        JComponent colorShow = (JComponent) components.get("colorPanel"); // 大的 60x60
        JComponent colorDiff = (JComponent) components.get("color_diff"); // 小的 40x40
        colorShow.setBounds(10,10,50,50);
        colorDiff.setBounds(0,0,60,60);
//        colorShow.setAlignmentX(Component.CENTER_ALIGNMENT);
//        colorShow.setAlignmentY(Component.CENTER_ALIGNMENT);
//        colorDiff.setAlignmentX(Component.CENTER_ALIGNMENT);
//        colorDiff.setAlignmentY(Component.CENTER_ALIGNMENT);
        previewPanel.add(colorShow);
        previewPanel.add(colorDiff);

        this.jp.add(previewPanel);

        JPanel colorTextPanel = new JPanel();
        colorTextPanel.setLayout(new FlowLayout());
        colorTextPanel.setFont(new Font("宋体",Font.BOLD,this.fontSize));
        colorTextPanel.setPreferredSize(new Dimension(17*this.fontSize, 6 * this.fontSize));
        colorTextPanel.add((JComponent)components.get("tenShow"));
        colorTextPanel.add((JComponent)components.get("sixteenShow"));
        colorTextPanel.setOpaque(false);
        this.jp.add(colorTextPanel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add((JComponent)components.get("colorPick"));
        this.jp.add(buttonPanel);
        this.jp.add((JComponent)components.get("set_background_color"));
        this.con.add(this.jp);
        this.resetColor(DataDefault.defaultColor,"");
    }

    private JPanel createBackgroundImage() {
        MainWindow that = this;
        ImageIcon imageIcon = new ImageIcon(this.config.getBackgroundImage());
        return new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                g.drawImage(imageIcon.getImage(),0,0,imageIcon.getImageObserver());
            }
        };
    }

    private void init(){
        try {
            this.setBackground(config.getBackground());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"窗体背景色不支持透明设置","错误",JOptionPane.ERROR_MESSAGE);
        }
        this.setForeground(ColorParser.diffColor(this.getBackground()));
        this.registryComponent();
        this.setSize(config.getWidth(),config.getHeight());
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle(config.getTitle());
    }

    public void setIconImage() {
        BufferedImage image = StreamUtil.streamToImage(config.getIcon());
        this.setIconImage(image);
    }

    public void resetColor(Color color, String id){
        for (CComponent value : this.components.values()) {
            if (!value.getID().equals(id)) {
                value.resetColor(color);
            }
        }
    }

    public void copyColor() {
        JComponent component = (JComponent)this.components.get("colorPanel");
        Color color = component.getBackground();
        String text = ColorParser.toColorString(color);
        ToolkitUtils.copy(text);
    }

    public void setAsBgColor() {
        JComponent component = (JComponent)this.components.get("colorPanel");
        ColorDiffPanel colorDiff = (ColorDiffPanel)this.components.get("color_diff");
        Color color = component.getBackground();
        colorDiff.setBackgroundColor(color);
    }
}

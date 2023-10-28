package com.changge.code.view;

import com.changge.code.core.config.GlobalConfig;
import com.changge.code.core.parser.ColorParser;
import com.changge.code.data.DataDefault;
import com.changge.code.utils.StreamUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame {

    private GlobalConfig config;

    private List<Color> record = new ArrayList<>();

    private Map<String,CComponent> components = new HashMap<>();

    public MainWindow(GlobalConfig config){
        this.config = config;
        this.init();
        this.setIconImage();
    }

    private void registryComponent() {
        components.put("colorPanel",new ColorShowPanel(this));
        components.put("colorPick",new ColorPickShow(this));
        components.put("tenShow",new TenShow(this));
        components.put("sixteenShow",new SixteenShow(this));
        this.add((JComponent)components.get("colorPanel"));
        this.add((JComponent)components.get("tenShow"));
        this.add((JComponent)components.get("sixteenShow"));
        this.add((JComponent)components.get("colorPick"));
        this.resetColor(DataDefault.defaultColor,"");
    }

    private void init(){
        this.registryComponent();
        this.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        this.setSize(config.getWidth(),config.getHeight());
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBackground(config.getBackground());
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
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text),null);
    }

    public void addColorRecord(Color color1) {
        if(record.size() > 6){
            record = new ArrayList<>(record.subList(record.size() - 6,record.size() - 1));
        }
        record.add(color1);
    }
}

package com.changge.code.core.config;


import com.changge.code.utils.StrUtils;

import java.awt.*;

public final class GlobalConfig {

    //region 单例创建
    private static volatile GlobalConfig globalConfig;

    private GlobalConfig(){}

    public static synchronized GlobalConfig instance(){
        if(globalConfig == null){
            synchronized (GlobalConfig.class){
                if(globalConfig == null){
                    globalConfig = new GlobalConfig();
                }
            }
        }
        return globalConfig;
    }
    //endregion

    private String icon;

    private Integer width = 234;

    private Integer height = 134;

    private Color background;

    private Image backgroundImage;

    private String title;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        if (StrUtils.isBlank(this.icon) && StrUtils.isNotBlank(icon)) {
            this.icon = icon;
        }
    }
}

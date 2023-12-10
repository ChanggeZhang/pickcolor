package com.changge.code.core.config;


import com.changge.code.core.annotation.Alias;
import com.changge.code.data.DataDefault;
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

    private String icon = "classpath:images/icon.ico";

    private Color background = DataDefault.defaultColor;

    @Alias("background")
    private Image backgroundImage;

    private String title = "颜色拾取器";

    private int fontSize = 12;

    private Integer width = 270;

    private Integer height = 155;

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        this.setWidth(this.fontSize * 22);
        this.setHeight(this.fontSize * 12);
    }

    public Integer getWidth() {
        return width;
    }

    private void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    private void setHeight(Integer height) {
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

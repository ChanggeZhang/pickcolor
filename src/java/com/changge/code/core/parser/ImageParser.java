package com.changge.code.core.parser;

import com.changge.code.utils.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class ImageParser implements Parser {

    private static final Logger log = Logger.getLogger(ImageParser.class.getName());


    @Override
    public boolean support(Class<?> type) {
        return Image.class.isAssignableFrom(type);
    }

    @Override
    public boolean parse(String val, Object target, Field field, Method setter)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        InputStream inputStream = null;
        try {
            inputStream = ResourceUtils.read(val);
        } catch (Exception e) {
            log.severe("背景图片加载失败，后续将采用纯色背景");
        }
        if(inputStream != null){
            Image image = null;
            try {
                image = ImageIO.read(inputStream);
                setter.invoke(target,image);
            } catch (IOException e) {
                log.severe("图片流转换失败，后续将使用纯色背景");
                return false;
            }
            return true;
        }
        return false;
    }
}

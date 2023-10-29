package com.changge.code.core.parser;

import com.changge.code.core.exception.SystemException;
import com.changge.code.utils.Assert;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.logging.Logger;

public class ColorParser implements Parser {

    private static final Logger log = Logger.getLogger(ColorParser.class.getName());

    public static String toColorString(Color color) {
        return "{\r\n" +
                "   \"rgb\": rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "),\r\n" +
                "   \"hex\": #" + toHexString(color) + "\r\n" +
                "}";
    }

    public static String toHexString(Color color) {
        String r = Integer.toHexString(color.getRed());
        String g = Integer.toHexString(color.getGreen());
        String b = Integer.toHexString(color.getBlue());
        return padding(r) + padding(g) + padding(b);
    }

    private static String padding(String b) {
        return (b.length() == 1 ? ("0" + b) : b);
    }

    public static Color parse(String s){
        Color color = null;
        if (s.startsWith("#")) {
            try {
                int r = Integer.valueOf(s.substring(1,3),16);
                int g = Integer.valueOf(s.substring(3,5),16);
                int b = Integer.valueOf(s.substring(5,7),16);
                color = new Color(r,g,b);
            } catch (Exception e) {
                log.severe("颜色值不合法：" + s);
            }
        }else if(s.startsWith("rgb(")){
            s = s.replace("rgb(","").replace(")","").replace("rgba(","");
            String[] ses = s.split(",");
            Assert.isTrue(ses.length == 3 || ses.length == 4,"颜色配置错误:{0}",s);
            if(ses.length == 3){
                color = new Color(Integer.parseInt(ses[0].trim()),Integer.parseInt(ses[1].trim()),Integer.parseInt(ses[2].trim()));
            }else{
                color = new Color(Integer.parseInt(ses[0].trim()),Integer.parseInt(ses[1].trim()),Integer.parseInt(ses[2].trim()),Integer.parseInt(ses[3].trim()));
            }
        }
        return color;
    }

    @Override
    public boolean parse(String val, Object target, Field field, Method setter) throws InvocationTargetException, IllegalAccessException {
        Color color = parse(val);
        if (color != null) {
            setter.invoke(target,color);
        }
        return true;
    }

    @Override
    public boolean support(Class<?> type) {
        return Color.class.isAssignableFrom(type);
    }
}

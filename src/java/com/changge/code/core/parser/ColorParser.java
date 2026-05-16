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
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = color.getAlpha();
        String copied = "";
        if (alpha != 255) {
            copied = String.format("rgba(%s, %s, %s, %s)", red,green,blue,alpha);
        }else{
            copied = String.format("rgb(%s, %s, %s)",red,green,blue);
        }
        return copied + "\r\n#" + toHexString(color);
    }

    public static String forShowAlpha(int alpha) {
        return String.format("%.2f",alpha / 255.0);
    }

    public static int forColorAlpha(double alpha) {
        return (int)(alpha * 255);
    }

    public static String toHexString(Color color) {
        String a = "";
        int a_int = color.getAlpha();
        if (a_int != 255) {
            a = Integer.toHexString(color.getAlpha());
        }
        String r = Integer.toHexString(color.getRed());
        String g = Integer.toHexString(color.getGreen());
        String b = Integer.toHexString(color.getBlue());
        return padding(a) + padding(r) + padding(g) + padding(b);
    }

    private static String padding(String b) {
        return (b.length() == 1 ? ("0" + b) : b);
    }

    public static Color parse(String s){
        Color color = null;
        if (s.startsWith("#")) {
            try {
                int[] rgba = hexToRgba(s);
                color = new Color(rgba[0],rgba[1],rgba[2],rgba[3]);
            } catch (Exception e) {
                log.severe("颜色值不合法：" + s);
                throw new SystemException("颜色值不合法：" + s);
            }
        }else if(s.startsWith("rgb(") || validRgb(s)){
            s = s.replace("rgb(","").replace(")","");
            String[] ses = s.split(",");
            Assert.isTrue(ses.length == 3,"颜色配置错误:{0}",s);
            color = new Color(Integer.parseInt(ses[0].trim()),Integer.parseInt(ses[1].trim()),Integer.parseInt(ses[2].trim()));
        }else if(s.startsWith("rgba(") || validRgba(s)){
            s = s.replace(")","").replace("rgba(","");
            String[] ses = s.split(",");
            Assert.isTrue(ses.length == 4,"颜色配置错误:{0}",s);
            color = new Color(Integer.parseInt(ses[0].trim()),Integer.parseInt(ses[1].trim()),Integer.parseInt(ses[2].trim()),forColorAlpha(Double.parseDouble(ses[3].trim())));
        }
        return color;
    }

    public static boolean validRgba(String s) {
        String[] srgba = s.split(",");
        if(srgba.length == 4){
            return validRgb(new String[]{srgba[0], srgba[1], srgba[2]}) && validAlpha(srgba[3]);
        }
        return false;
    }

    public static boolean validRgb(String s) {
        String[] srgb = s.split(",");
        return validRgb(srgb);
    }

    private static boolean validRgb(String[] srgb) {
        if(srgb.length == 3){
            return false;
        }
        for (String s : srgb) {
            if(!s.matches("\\d+")){
                return false;
            }
            int i = Integer.parseInt(s);
            if(i < 0 || i > 255){
                return false;
            }
        }
        return true;
    }

    private static boolean validAlpha(String s) {
        return s.matches("\\d+(\\.\\d+)?") && Double.parseDouble(s) >= 0 && Double.parseDouble(s) <= 1;
    }

    public static boolean validHexColor(String s){
        boolean v = false;
        if (s.startsWith("#")) {
            try {
                hexToRgba(s);
            } catch (Exception e) {
                System.err.println("颜色格式错误：" + s);
            }
        }
        return v;
    }

    /**
     *
     * @param hex
     * @return 数组，依次是r，g，b，a
     */
    private static int[] hexToRgba(String hex) {
        int r = 255,g = 255,b = 255,a = 255;
        if (hex.length() == 7) {
            r = Integer.valueOf(hex.substring(1,3),16);
            g = Integer.valueOf(hex.substring(3,5),16);
            b = Integer.valueOf(hex.substring(5,7),16);
        }else if(hex.length() == 9){
            a = Integer.valueOf(hex.substring(1,3),16);
            r = Integer.valueOf(hex.substring(3,5),16);
            g = Integer.valueOf(hex.substring(5,7),16);
            b = Integer.valueOf(hex.substring(7,9),16);
        }
        return new int[]{r,g,b,a};
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

    public static Color diffColor(Color color){
        return new Color(255 - color.getRed(),255 - color.getGreen(), 255 - color.getBlue());
    }
}

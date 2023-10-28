package com.changge.code.utils;

import java.lang.reflect.Field;

public class StrUtils {

    public static boolean isBlank(CharSequence string){
        return string == null || string.toString().replaceAll("\\s+","").trim().length() == 0;
    }

    public static boolean isNotBlank(CharSequence s){
        return !isBlank(s);
    }

    public static boolean isNull(CharSequence s){
        return s == null;
    }

    public static boolean isNotNull(CharSequence s){
        return !isNull(s);
    }

    public static boolean isEmpty(CharSequence s){
        return s == null || s.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence s){
        return !isEmpty(s);
    }

    public static String buildGetName(Field field) {
        String getPrefix = "get";
        if (field.getType().isAssignableFrom(Boolean.class) && field.getType().equals(boolean.class)) {
            getPrefix = "is";
        }
        return getPrefix + buildGetSetName(field.getName());
    }

    public static String buildSetName(Field field) {
        return "set" + buildGetSetName(field.getName());
    }

    private static String buildGetSetName(String fieldName) {
        if(fieldName.length() == 1){
            return fieldName.toUpperCase();
        }else{
            return fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        }
    }
}

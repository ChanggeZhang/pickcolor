package com.changge.code.utils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldUtil {

    public static Field findField(Class<?> clazz,String name){
        if(clazz == null) return null;
        Field field = Arrays.stream(clazz.getDeclaredFields()).filter(f -> f.getName().equals(name)).findFirst().orElse(null);
        if(field == null){
            return findField(clazz.getSuperclass(),name);
        }
        return field;
    }
}

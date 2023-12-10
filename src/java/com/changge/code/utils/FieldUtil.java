package com.changge.code.utils;

import com.changge.code.core.annotation.Alias;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldUtil {

    public static List<Field> findFields(Class<?> clazz, String name){
        if(clazz == null) return null;
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).filter(f -> {
            Alias alias = f.getDeclaredAnnotation(Alias.class);
            return f.getName().equals(name) || (alias != null && name.equals(alias.value()));
        }).collect(Collectors.toList());
        if(fields == null){
            return findFields(clazz.getSuperclass(),name);
        }
        return fields;
    }

    public static Field findField(Class<?> clazz, String name){
        if(clazz == null) return null;
        List<Field> fields = findFields(clazz,name);
        return CollUtils.isNotEmpty(fields) ? fields.get(0) : null;
    }
}

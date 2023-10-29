package com.changge.code.core.parser;

import com.changge.code.core.exception.SystemException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

public class CollectionParser implements Parser {
    @Override
    public boolean support(Class<?> type) {
        return type.isAssignableFrom(Collection.class)
                || type.isArray();
    }

    @Override
    public boolean parse(String val, Object target, Field field, Method setter) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Class<?> type = field.getType();
        String[] splitses = val.split("[,，]");
        try {
            if (Collection.class.isAssignableFrom(type)) {
                java.util.List<String> splits = Arrays.asList(val.split("[,，]"));
                Constructor<?> constructor = type.getConstructor(Collection.class);
                setter.invoke(target,constructor.newInstance(splits));
            }else if(type.isArray()){
                setter.invoke(target,splitses);
            }
        } catch (Exception e) {
            throw new SystemException("目前只支持单层字符串的数组初始化：" + val,e);
        }
        return true;
    }
}

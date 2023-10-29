package com.changge.code.core.parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CharSequenceParser implements Parser {
    @Override
    public boolean support(Class<?> type) {
        return CharSequence.class.isAssignableFrom(type);
    }

    @Override
    public boolean parse(String val, Object target, Field field, Method setter)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Class<?> type = field.getType();
        Constructor<?> constructor = type.getConstructor(String.class);
        setter.invoke(target,constructor.newInstance(val));
        return true;
    }
}

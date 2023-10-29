package com.changge.code.core.parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;

public interface Parser {

    boolean support(Class<?> type);

    boolean parse(String val, Object target, Field field, Method setter)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException;
}

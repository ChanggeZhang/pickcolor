package com.changge.code.core.parser;

import java.lang.reflect.Type;

public interface Parser {

    boolean support(Class<?> type);

    <T>T parse(String s);
}

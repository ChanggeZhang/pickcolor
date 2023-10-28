package com.changge.code.core.factory;

public interface ConfigFactory<T> {


    void build(Class<T> startClass, String[] args);
}

package com.changge.code;

import com.changge.code.core.Application;
import com.changge.code.core.annotation.AppConfig;

@AppConfig(basePackage = "com.changge.code")
public class Starter {
    public static void main(String[] args) {
        Application.start(Starter.class,args);
    }
}

package com.changge.code.core.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AppConfig {

    String baseConfigFile() default "classpath:application.conf";

    String[] basePackage() default "";
}

package com.changge.code.core.enums;

public enum LocationPrefix {
    CLASSPATH("classpath:");

    private String prefix;

    LocationPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}

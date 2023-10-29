package com.changge.code.core.enums;

public enum LocationPrefix {
    CLASSPATH("classpath:"),
    HTTP("http:"),
    HTTPS("https:");

    private String prefix;

    LocationPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}

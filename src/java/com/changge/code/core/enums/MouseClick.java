package com.changge.code.core.enums;

import java.awt.event.MouseEvent;

public enum MouseClick {

    LEFT_CLICK(MouseEvent.BUTTON1),
    RIGHT_CLICK(MouseEvent.BUTTON3),
    MIDDLE_CLICK(MouseEvent.BUTTON2),
    ;
    private int code;

    MouseClick(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

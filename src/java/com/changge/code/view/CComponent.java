package com.changge.code.view;

import java.awt.*;

public interface CComponent {
    default void resetColor(Color color){}

    String getID();
}

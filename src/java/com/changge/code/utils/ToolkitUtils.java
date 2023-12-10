package com.changge.code.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class ToolkitUtils {


    public static void copy(String text) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text),null);
        JOptionPane.showMessageDialog(null,"颜色值已复制到粘贴板，直接粘贴即可使用","提示", JOptionPane.INFORMATION_MESSAGE);
    }
}

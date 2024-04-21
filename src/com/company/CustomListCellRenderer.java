package com.company;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

class CustomListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (renderer instanceof JLabel) {
            JLabel label = (JLabel) renderer;
            label.setFont(new Font("Arial", Font.PLAIN, 13)); // Set the desired font
            label.setForeground(Color.BLACK); // Set the text color
            label.setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE); // Set the background color based on selection
            label.setOpaque(true); // Make the label opaque to show the background color
        }
        return renderer;
    }
}

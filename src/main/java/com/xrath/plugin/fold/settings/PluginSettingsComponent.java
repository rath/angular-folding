package com.xrath.plugin.fold.settings;

import javax.swing.*;

public class PluginSettingsComponent {
    private final JPanel mainPanel;
    private final JTextField namePatternField;

    public PluginSettingsComponent() {
        mainPanel = new JPanel();
        namePatternField = new JTextField(40);
        mainPanel.add(new JLabel("Name Pattern:"));
        mainPanel.add(namePatternField);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public String getNamePattern() {
        return namePatternField.getText();
    }

    public void setNamePattern(String pattern) {
        namePatternField.setText(pattern);
    }
}

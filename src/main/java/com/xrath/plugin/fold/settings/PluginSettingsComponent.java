package com.xrath.plugin.fold.settings;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import java.awt.*;

public class PluginSettingsComponent {
    private final JPanel mainPanel;
    private final JTextArea namePatternField;

    public PluginSettingsComponent() {
        namePatternField = new JBTextArea(5, 20);
        namePatternField.setLineWrap(true);
        namePatternField.setWrapStyleWord(false);

        JPanel panel = FormBuilder.createFormBuilder().addLabeledComponent(
                "Regex for grouping files", new JBScrollPane(namePatternField)).getPanel();
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.NORTH);
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

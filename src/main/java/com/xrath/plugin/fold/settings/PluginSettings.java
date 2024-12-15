package com.xrath.plugin.fold.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PluginSettings implements Configurable {
    private PluginSettingsComponent settingsComponent;

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "Angular Folding Plugin";
    }

    @Override
    public @Nullable JComponent createComponent() {
        settingsComponent = new PluginSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        return !settingsComponent.getNamePattern().equals(PluginSettingsState.getInstance().getNamePattern());
    }

    @Override
    public void apply() {
        PluginSettingsState.getInstance().setNamePattern(settingsComponent.getNamePattern());
    }

    @Override
    public void reset() {
        settingsComponent.setNamePattern(PluginSettingsState.getInstance().getNamePattern());
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}

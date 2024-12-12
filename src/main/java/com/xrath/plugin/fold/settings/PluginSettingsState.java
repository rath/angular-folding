package com.xrath.plugin.fold.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "PluginSettingsState",
        storages = @Storage("pluginSettings.xml")
)
public class PluginSettingsState implements PersistentStateComponent<PluginSettingsState> {
    private String namePattern =
            "(.*)\\.(component|service|pipe|guard|directive|actions|effects|reducer|selectors|state|resolver|builder)\\.(css|sass|scss|stylus|styl|less|html|svg|haml|pug|spec|stories\\.ts|ts)";

    public static PluginSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(PluginSettingsState.class);
    }

    @Nullable
    @Override
    public PluginSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PluginSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public String getNamePattern() {
        return namePattern;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }
}

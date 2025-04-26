package com.nort721.replay;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class ConfigWrapper {

    private final FileConfiguration config;
    private int godseyeAlertRecordingLength;

    public ConfigWrapper(FileConfiguration config) {
        this.config = config;
        loadConfigValues();
    }

    private void loadConfigValues() {
        godseyeAlertRecordingLength = config.getInt("godseye.on-alert.recording_length_seconds");
    }
}

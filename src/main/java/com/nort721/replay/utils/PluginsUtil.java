package com.nort721.replay.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.Arrays;

@UtilityClass
public class PluginsUtil {

    public static final String ADVANCED_REPLAY = "AdvancedReplay";
    public static final String SHADOW_SNAP = "ShadowSnap";

    public static boolean exists(String name) {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .anyMatch(plugin -> plugin.isEnabled() && plugin.getName().equalsIgnoreCase(name));
    }

    public static String getPluginVersion(String name) {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .filter(plugin -> plugin.isEnabled() && plugin.getName().equalsIgnoreCase(name))
                .map(plugin -> plugin.getDescription().getVersion())
                .findFirst()
                .orElse("");
    }
}

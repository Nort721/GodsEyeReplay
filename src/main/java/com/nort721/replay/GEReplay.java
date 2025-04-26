package com.nort721.replay;

import com.nort721.replay.utils.RecordingUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class GEReplay extends JavaPlugin {

    @Getter
    private static GEReplay instance;
    private static final long TIMER_DELAY = 20L;
    private static final long TIMER_PERIOD = 20L;

    private ConfigWrapper configWrapper;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        configWrapper = new ConfigWrapper(getConfig());
        registerMainTimer();
        getServer().getPluginManager().registerEvents(new MainListener(), this);
        sendConsoleMessage(" has been enabled");
    }

    @Override
    public void onDisable() {
        sendConsoleMessage("has been disabled");
    }

    public void sendConsoleMessage(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[" + getDescription().getName() + "] " + ChatColor.RESET + msg);
    }

    private void registerMainTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                RecordingUtil.forEachTracker(RecordingTracker::onSecondPassed);
            }
        }.runTaskTimer(this, TIMER_DELAY, TIMER_PERIOD);
    }
}

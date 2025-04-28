package com.nort721.replay.utils;

import com.nort721.replay.GEReplay;
import com.nort721.replay.RecordingTracker;
import lombok.experimental.UtilityClass;
import me.jumper251.replay.api.ReplayAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class RecordingUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm");
    private static final Map<UUID, RecordingTracker> beingRecorded = new ConcurrentHashMap<>();

    public static void forEachTracker(java.util.function.Consumer<RecordingTracker> action) {
        beingRecorded.values().forEach(action);
    }

    public static RecordingTracker getRecordingTracker(UUID playerUUID) {
        return beingRecorded.get(playerUUID);
    }

    public static boolean isCurrentlyBeingRecorded(UUID playerUUID) {
        return beingRecorded.containsKey(playerUUID);
    }

    public static void startRecording(Player player) {
        String replayId = newReplayId(player);
        beingRecorded.put(player.getUniqueId(), new RecordingTracker(player, replayId));

        if (PluginsUtil.exists(PluginsUtil.ADVANCED_REPLAY)) {
            ReplayAPI.getInstance().recordReplay(replayId, Bukkit.getConsoleSender(), player);
            
        } else if (PluginsUtil.exists(PluginsUtil.SHADOW_SNAP)) {
            // ToDo implement shadowsnap support
        } else {
            GEReplay.getInstance().sendConsoleMessage("No replay plugin found, disabling GodsEyeReplay.");
            Bukkit.getPluginManager().disablePlugin(GEReplay.getInstance());
        }
    }

    public static void endRecording(Player player) {
        RecordingTracker tracker = beingRecorded.get(player.getUniqueId());
        if (tracker == null) {
            GEReplay.getInstance().sendConsoleMessage(String.format("Attempted to end recording of player - %s, but no tracker was found. aborting.%n", player.getName()));
            return;
        }

        if (PluginsUtil.exists(PluginsUtil.ADVANCED_REPLAY)) {
            ReplayAPI.getInstance().stopReplay(tracker.getReplayId(), true);
        } else {
            GEReplay.getInstance().sendConsoleMessage("No replay plugin found, disabling GodsEyeReplay.");
            Bukkit.getPluginManager().disablePlugin(GEReplay.getInstance());
        }

        beingRecorded.remove(player.getUniqueId());
    }

    public static String newReplayId(Player player) {
        return String.format("%s__%s", player.getUniqueId(), LocalDateTime.now().format(FORMATTER));
    }
}

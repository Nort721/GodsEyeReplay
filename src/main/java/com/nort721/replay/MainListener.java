package com.nort721.replay;

import com.nort721.replay.utils.RecordingUtil;
import godseye.GodsEyeAlertEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class MainListener implements Listener {

    @EventHandler
    public void onGodsEyeAlert(GodsEyeAlertEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!RecordingUtil.isCurrentlyBeingRecorded(playerUUID)) {
            RecordingUtil.startRecording(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (RecordingUtil.isCurrentlyBeingRecorded(playerUUID)) {
            RecordingUtil.endRecording(player);
        }
    }
}
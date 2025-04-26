package com.nort721.replay;

import com.nort721.replay.utils.RecordingUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public class RecordingTracker {

    private final Player player;
    private final String replayId;
    private int recordingLengthSeconds = 0;

    public void onSecondPassed() {
        recordingLengthSeconds++;
        if (recordingLengthSeconds >= GEReplay.getInstance().getConfigWrapper().getGodseyeAlertRecordingLength()) {
            RecordingUtil.endRecording(player);
        }
    }
}

package com.kgc.sauw.core.sound;

import com.kgc.sauw.core.resource.Resource;

public class Music {
    private static com.badlogic.gdx.audio.Music currentTrack;
    private static String currentTrackPath = "";

    public static void play(String path) {
        if (!path.equals(currentTrackPath)) {
            if (currentTrack != null) currentTrack.stop();
            currentTrack = Resource.getMusic(path);
            currentTrackPath = path;
            currentTrack.play();
        }
    }

    public static void setVolume(int volume) {
        if (currentTrack != null) currentTrack.setVolume(volume / 100f);
    }

    public static void setLooping(boolean looping) {
        if (currentTrack != null) currentTrack.setLooping(looping);
    }
}

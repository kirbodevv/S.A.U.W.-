package com.kgc.sauw.resource;

import com.kgc.sauw.core.resource.Resource;

import static com.kgc.sauw.core.environment.Environment.getWorld;

public class Music {

    com.badlogic.gdx.audio.Music main;
    com.badlogic.gdx.audio.Music nightSoundtrack;

    float musicVolume;

    private static Music music;

    public static Music getMusic() {
        if (music == null) music = new Music();
        return music;
    }

    public Music() {
        main = Resource.getMusic("music/main.mp3");
        nightSoundtrack = Resource.getMusic("music/Sunset.mp3");

        main.setLooping(true);
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = musicVolume / 100f;
    }

    public void update(boolean isMenu) {
        main.setVolume(musicVolume);
        nightSoundtrack.setVolume(musicVolume);
        if (isMenu) {
            if (!main.isPlaying())
                main.play();
        } else {
            if (main.isPlaying())
                main.stop();
            if (getWorld().worldTime.getHours() > 19 || getWorld().worldTime.getHours() < 7) {
                if (!nightSoundtrack.isPlaying())
                    nightSoundtrack.play();
            } else {
                nightSoundtrack.setLooping(false);
            }
        }
    }

    public void dispose() {
        main.dispose();
        nightSoundtrack.dispose();
    }
}

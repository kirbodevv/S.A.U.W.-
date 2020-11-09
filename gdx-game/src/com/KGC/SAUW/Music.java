package com.KGC.SAUW;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class Music {

	Music main;
	Music nightSoundtrack;
	public World w;
	float musicVolume;
	settings settings;
	public Music(settings settings, World w) {
		this.settings = settings;
		this.w = w;
		main = Gdx.audio.newMusic(Gdx.files.internal("music/main.mp3"));
		nightSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("music/Sunset.mp3"));

		main.setLooping(true);
	}
	public void update(boolean isMenu) {
		musicVolume = settings.musicVolume / 100f;
		main.setVolume(musicVolume);
		nightSoundtrack.setVolume(musicVolume);
		if (isMenu) {
			if (!main.isPlaying())
				main.play();
		} else {
			if (main.isPlaying())
				main.stop();
			if (w.WorldTime.getHours() > 19 || w.WorldTime.getHours() < 7) {
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

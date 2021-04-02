package com.kgc.sauw.resource;
import com.badlogic.gdx.Gdx;
import com.kgc.sauw.map.World;

public class Music {

	com.badlogic.gdx.audio.Music main;
	com.badlogic.gdx.audio.Music nightSoundtrack;

	public World w;
	float musicVolume;
	public Music(World w) {
		this.w = w;
		main = Gdx.audio.newMusic(Gdx.files.internal("music/main.mp3"));
		nightSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("music/Sunset.mp3"));

		main.setLooping(true);
	}
	public void setMusicVolume(int musicVolume){
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

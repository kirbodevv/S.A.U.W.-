package com.kgc.sauw.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kgc.sauw.MainGame;
import com.kgc.sauw.MyGdxGame;
import com.kgc.sauw.screen.MenuScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1366;
		config.height = 768;
		final MainGame game = new MainGame();
		new LwjglApplication(game, config);
	}
}

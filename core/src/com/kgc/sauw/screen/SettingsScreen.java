package com.kgc.sauw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Elements.Checkbox;
import com.kgc.sauw.UI.Elements.Notification;
import com.kgc.sauw.UI.Elements.Slider;
import com.kgc.sauw.game.MainGame;
import com.kgc.sauw.resource.Textures;
import org.json.JSONObject;

import java.util.Iterator;

import static com.kgc.sauw.graphic.Graphic.BATCH;
import static com.kgc.sauw.graphic.Graphic.MENU_CAMERA;

public class SettingsScreen implements Screen {
	private int width = Gdx.graphics.getWidth();
	private int height = Gdx.graphics.getHeight();
	MainGame game;
	Textures t;
	MenuScreen MenuScreen;

	Button closeButton;

	Button general;
	Button Interface;
	Button gameSettings;
	Button sound;

	private Button nextLang;
	private Button prevLang;
	private Checkbox debug;
	private Checkbox debugRenderer;
	private Checkbox AIPU;
    private Checkbox useConsole;

	private Slider musicVolume;

	public int currentSettingCot = 0;

	Texture background0 = Textures.generateTexture(15, (height - width / 16 * 2) / (width / 16), false);
	Texture background1 = Textures.generateTexture(3, 1, true);

	BitmapFont bf = new BitmapFont(Gdx.files.internal("ttf.fnt"));

	JSONObject availableLangs;
	private Notification Notification;
	public SettingsScreen(final MainGame game, Textures t, MenuScreen ms) {
		this.game = game;
		this.t = t;
		this.MenuScreen = ms;
		try {
			availableLangs = new JSONObject(Gdx.files.internal("json/availableLanguages.json").readString());
		} catch (Exception e) {

		}
		Notification = new Notification(Textures.generateBackground(8, 4));
		debug = new Checkbox(t.switch_0, t.switch_1);
		debug.setSize(width / 16, width / 16);
		debug.setPosition(MENU_CAMERA.X + width / 16 * 6 + width / 64 , MENU_CAMERA.Y + height - width / 16 * 6);
		debug.setChecked(ms.settings.debugMode);
		debug.setEventListener(new Checkbox.EventListener(){
				@Override
				public void onClick(boolean isChecked) {
					MenuScreen.settings.debugMode = isChecked;
					MenuScreen.settings.saveSettings();
				}
			});
		debugRenderer = new Checkbox(t.switch_0, t.switch_1);
		debugRenderer.setSize(width / 16, width / 16);
		debugRenderer.setPosition(MENU_CAMERA.X + width / 16 * 6 + width / 64 , MENU_CAMERA.Y + height - width / 16 * 7);
		debugRenderer.setChecked(ms.settings.debugRenderer);
		debugRenderer.setEventListener(new Checkbox.EventListener(){
				@Override
				public void onClick(boolean isChecked) {
					MenuScreen.settings.debugRenderer = isChecked;
					MenuScreen.settings.saveSettings();
				}
			});
		AIPU = new Checkbox(t.switch_0, t.switch_1);
		AIPU.setSize(width / 16, width / 16);
		AIPU.setPosition(MENU_CAMERA.X + width / 16 * 9, MENU_CAMERA.Y + height - width / 16 * 3);
		AIPU.setChecked(ms.settings.autopickup);
		AIPU.setEventListener(new Checkbox.EventListener(){
				@Override
				public void onClick(boolean isChecked) {
					MenuScreen.settings.autopickup = isChecked;
					MenuScreen.settings.saveSettings();
				}
			});
		useConsole = new Checkbox(t.switch_0, t.switch_1);
		useConsole.setSize(width / 16, width / 16);
		useConsole.setPosition(MENU_CAMERA.X + width / 16 * 9, MENU_CAMERA.Y + height - width / 16 * 4);
		useConsole.setChecked(ms.settings.useConsole);
		useConsole.setEventListener(new Checkbox.EventListener(){
				@Override
				public void onClick(boolean isChecked) {
					MenuScreen.settings.useConsole = isChecked;
					MenuScreen.settings.saveSettings();
					if (isChecked) {
						Notification.hideOnClick(true);
						Notification.show(width / 16 * 4, (height - width / 16 * 4) / 2, width / 16 * 8, width / 16 * 4, MenuScreen.languages.getString("useConsole"), MenuScreen.languages.getString("useConsoleNotification"), 10);
					}
				}
			});
		closeButton = new Button("closeButton", width - width / 16, height - width / 16, width / 32, width / 32, t.closeButton, t.closeButton);
		closeButton.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					game.setScreen(MenuScreen);
				}
			});
		prevLang = new Button("", MENU_CAMERA.X + width / 16 * 4 + width / 64, MENU_CAMERA.Y + height - width / 32 * 7, width / 16, width / 16, t.button_up_0, t.button_up_1);
		nextLang = new Button("", MENU_CAMERA.X + width / 16 * 4 + width / 64, MENU_CAMERA.Y + height - width / 32 * 9, width / 16, width / 16, t.button_down_0, t.button_down_1);
		nextLang.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					Iterator i = availableLangs.keys();
					int ii = 0;
					while (i.hasNext()) {
						ii++;
						String l = (String)i.next();
						if (l.equals(MenuScreen.settings.lang)) {
			                if (ii + 1 <= availableLangs.length()) {
								MenuScreen.settings.lang = (String)i.next();
							} else {
								MenuScreen.settings.lang = (String)availableLangs.keys().next();
							}
							MenuScreen.settings.saveSettings();
							break;
						}
					}
				}
			});
		prevLang.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					Iterator i = availableLangs.keys();
					int prevLang = 0;
					int ii = 0;
					while (i.hasNext()) {
						String l = (String)i.next();
						if (l.equals(MenuScreen.settings.lang)) {
			                prevLang = ii - 1;
							if (prevLang < 0) prevLang = availableLangs.length() - 1;
							ii = 0;
						}
						ii++;
					}
					i = availableLangs.keys();
					while (i.hasNext()) {
						String langg = (String)i.next();
						if (ii == prevLang) {
							MenuScreen.settings.lang = langg;
							MenuScreen.settings.saveSettings();
							break;
						}
						ii++;
					}
				}
			});
		bf.setScale(width / 16 / 2 / bf.getCapHeight());
		bf.setColor(Color.BLACK);
		general = new Button("", width / 16, height - width / 32 * 3, width / 32 * 5, width / 16);
		//general.setTextColor(Color.BLACK);
		general.setText(MenuScreen.languages.getString("general"));
		general.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					currentSettingCot = 0;
					general.lock(true);
				}
			});
		Interface = new Button("", general.X + general.width + width / 128, height - width / 32 * 3, width / 16 * 4, width / 16);
		// Interface.setTextColor(Color.BLACK);
		Interface.setText(MenuScreen.languages.getString("interface"));
		Interface.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					currentSettingCot = 1;
					Interface.lock(true);
				}
			});
		gameSettings = new Button("", Interface.X + Interface.width + width / 128, height - width / 32 * 3, width / 16 * 3, width / 16);
		// gameSettings.setTextColor(Color.BLACK);
		gameSettings.setText(MenuScreen.languages.getString("game"));
		gameSettings.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					currentSettingCot = 2;
					gameSettings.lock(true);
				}
			});
		sound = new Button("", gameSettings.X + gameSettings.width + width / 128, height - width / 32 * 3, width / 16 * 2, width / 16);
		//sound.setTextColor(Color.BLACK);
		sound.setText(MenuScreen.languages.getString("sound"));
		sound.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					currentSettingCot = 3;
					sound.lock(true);
				}
			});
		musicVolume = new Slider(width / 16 * 4, height - width / 16 * 2 - width / 64 * 3, width / 16 * 4, width / 32);
	    musicVolume.setEventListener(new Slider.EventListener(){
				@Override
				public void onValueChange(int v) {
					MenuScreen.settings.musicVolume = v;
					MenuScreen.settings.saveSettings();
				}
			});
		musicVolume.setValue(MenuScreen.settings.musicVolume);
	}
	@Override
	public void render(float p1) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		general.lock(false);
		Interface.lock(false);
		gameSettings.lock(false);
		sound.lock(false);

		if (currentSettingCot == 0) {
			general.lock(true);
			nextLang.update(MENU_CAMERA);
			prevLang.update(MENU_CAMERA);
			debug.update(MENU_CAMERA);
			debugRenderer.update(MENU_CAMERA);
		} else if (currentSettingCot == 1) {
			Interface.lock(true);
		} else if (currentSettingCot == 2) {
			gameSettings.lock(true);
			AIPU.update(MENU_CAMERA);
			useConsole.update(MENU_CAMERA);
		} else if (currentSettingCot == 3) {
			sound.lock(true);
		    musicVolume.update(MENU_CAMERA);
		}
		Notification.update(MENU_CAMERA);
		closeButton.update(MENU_CAMERA);
		general.update(MENU_CAMERA);
		Interface.update(MENU_CAMERA);
		gameSettings.update(MENU_CAMERA);
		sound.update(MENU_CAMERA);
		BATCH.begin();
		BATCH.draw(t.standartBackground_full, MENU_CAMERA.X, MENU_CAMERA.Y, width, height);
		BATCH.draw(background0, MENU_CAMERA.X + width / 32, MENU_CAMERA.Y + width / 32, width / 16 * 15, height - width / 16 * 2);
		closeButton.render(BATCH, MENU_CAMERA);
		general.render(BATCH, MENU_CAMERA);
		Interface.render(BATCH, MENU_CAMERA);
		gameSettings.render(BATCH, MENU_CAMERA);
		sound.render(BATCH, MENU_CAMERA);
		if (currentSettingCot == 0) {
			try {
				BATCH.draw(background1, MENU_CAMERA.X + width / 16, MENU_CAMERA.Y + height - width / 16 * 4, width / 16 * 3, width / 16);
				bf.drawMultiLine(BATCH, MenuScreen.languages.getString("language"), MENU_CAMERA.X + width / 16, MENU_CAMERA.Y + height - width / 16 * 2 - (width / 16 / 4), width / 16 * 3, BitmapFont.HAlignment.CENTER);
				bf.drawMultiLine(BATCH, availableLangs.getString(MenuScreen.settings.lang), MENU_CAMERA.X + width / 16, MENU_CAMERA.Y + height - width / 16 * 3 - (width / 16 / 4), width / 16 * 3, BitmapFont.HAlignment.CENTER);
			    bf.drawMultiLine(BATCH, MenuScreen.languages.getString("debug"), MENU_CAMERA.X + width / 16, MENU_CAMERA.Y + height - width / 16 * 5 - (width / 16 / 4), width / 16 * 3, BitmapFont.HAlignment.LEFT);
				bf.drawMultiLine(BATCH, MenuScreen.languages.getString("debugRenderer"), MENU_CAMERA.X + width / 16, MENU_CAMERA.Y + height - width / 16 * 6 - (width / 16 / 4), width / 16 * 3, BitmapFont.HAlignment.LEFT);
				nextLang.render(BATCH, MENU_CAMERA);
				prevLang.render(BATCH, MENU_CAMERA);
				debug.render(BATCH, MENU_CAMERA);
				debugRenderer.render(BATCH, MENU_CAMERA);
			} catch (Exception e) {

			}
		} else if (currentSettingCot == 1) {

		} else if (currentSettingCot == 2) {
			bf.drawMultiLine(BATCH, MenuScreen.languages.getString("autoitemspickup"), MENU_CAMERA.X + width / 16, MENU_CAMERA.Y + height - width / 16 * 2 - (width / 16 / 4), width / 16 * 7, BitmapFont.HAlignment.LEFT);
			AIPU.render(BATCH, MENU_CAMERA);
			bf.drawMultiLine(BATCH, MenuScreen.languages.getString("useConsole"), MENU_CAMERA.X + width / 16, MENU_CAMERA.Y + height - width / 16 * 3 - (width / 16 / 4), width / 16 * 7, BitmapFont.HAlignment.LEFT);
			useConsole.render(BATCH, MENU_CAMERA);
		} else if (currentSettingCot == 3) {
			bf.drawMultiLine(BATCH, MenuScreen.languages.getString("music"), MENU_CAMERA.X + width / 16, MENU_CAMERA.Y + height - width / 16 * 2 - (width / 16 / 4), width / 16 * 2, BitmapFont.HAlignment.LEFT);
		    musicVolume.render(BATCH, MENU_CAMERA);
			bf.drawMultiLine(BATCH, musicVolume.getValue() + "", MENU_CAMERA.X + musicVolume.X + musicVolume.width + width / 32, MENU_CAMERA.Y + musicVolume.Y + musicVolume.height, width / 16 * 2, BitmapFont.HAlignment.LEFT);
		}
		Notification.render(BATCH, MENU_CAMERA);
		BATCH.end();
	}

	@Override
	public void resize(int p1, int p2) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {

	}
}

package com.KGC.SAUW.screen;
import com.KGC.SAUW.InterfaceAPI.button;
import com.KGC.SAUW.MainGame;
import com.KGC.SAUW.Textures;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.json.JSONObject;
import java.util.Iterator;
import com.KGC.SAUW.InterfaceAPI.checkbox;
import com.KGC.SAUW.Vector2i;
import com.KGC.SAUW.InterfaceAPI.Slider;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.KGC.SAUW.InterfaceAPI.Notification;

public class SettingsScreen implements Screen {
	SpriteBatch batch;
	private int width = Gdx.graphics.getWidth();
	private int height = Gdx.graphics.getHeight();
	MainGame game;
	Textures t;
	MenuScreen MenuScreen;

	button closeButton;
	
	button general;
	button Interface;
	button gameSettings;
	button sound;
	
	private button nextLang;
	private button prevLang;
	private checkbox debug;
	private checkbox AIPU;
    private checkbox useConsole;
	
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
		batch = ms.b;
		this.MenuScreen = ms;
		try {
			availableLangs = new JSONObject(Gdx.files.internal("json/availableLanguages.json").readString());
		} catch (Exception e) {

		}
		Notification = new Notification(Textures.generateBackground(8, 4));
		debug = new checkbox(t.switch_0, t.switch_1);
		debug.setSize(width / 16, width / 16);
		debug.setPosition(ms.camera.X + width / 16 * 4 + width / 64 , ms.camera.Y + height - width / 16 * 6);
		debug.setChecked(ms.settings.debugMode);
		debug.setEventListener(new checkbox.Eventlistener(){
				@Override
				public void onClick(boolean isChecked) {
					MenuScreen.settings.debugMode = isChecked;
					MenuScreen.settings.saveSettings();
				}
			});
		AIPU = new checkbox(t.switch_0, t.switch_1);
		AIPU.setSize(width / 16, width / 16);
		AIPU.setPosition(MenuScreen.camera.X + width / 16 * 9, MenuScreen.camera.Y + height - width / 16 * 3);
		AIPU.setChecked(ms.settings.autopickup);
		AIPU.setEventListener(new checkbox.Eventlistener(){
				@Override
				public void onClick(boolean isChecked) {
					MenuScreen.settings.autopickup = isChecked;
					MenuScreen.settings.saveSettings();
				}
			});
		useConsole = new checkbox(t.switch_0, t.switch_1);
		useConsole.setSize(width / 16, width / 16);
		useConsole.setPosition(MenuScreen.camera.X + width / 16 * 9, MenuScreen.camera.Y + height - width / 16 * 4);
		useConsole.setChecked(ms.settings.useConsole);
		useConsole.setEventListener(new checkbox.Eventlistener(){
				@Override
				public void onClick(boolean isChecked) {
					MenuScreen.settings.useConsole = isChecked;
					MenuScreen.settings.saveSettings();
					if(isChecked){
						Notification.hideOnClick(true);
						Notification.show(width / 16 * 4, (height - width / 16 * 4) / 2, width / 16 * 8, width / 16 * 4, MenuScreen.langs.getString("useConsole"), MenuScreen.langs.getString("useConsoleNotification"), 10);
					}
				}
		});
		closeButton = new button("closeButton", width - width / 16, height - width / 16, width / 32, width / 32, t.closeButton, t.closeButton);
		closeButton.setEventListener(new button.EventListener(){
				@Override
				public void onClick() {
					game.setScreen(MenuScreen);
				}
			});
		prevLang = new button("", ms.camera.X + width / 16 * 4 + width / 64, ms.camera.Y + height - width / 32 * 7, width / 16, width / 16, t.button_up_0, t.button_up_1);
		nextLang = new button("", ms.camera.X + width / 16 * 4 + width / 64, ms.camera.Y + height - width / 32 * 9, width / 16, width / 16, t.button_down_0, t.button_down_1);
		nextLang.setEventListener(new button.EventListener(){
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
		prevLang.setEventListener(new button.EventListener(){
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
		general = new button("", width / 16, height - width / 32 * 3, width / 32 * 5, width / 16);
		general.setTextColor(Color.BLACK);
		general.setText(MenuScreen.langs.getString("general"));
		general.setEventListener(new button.EventListener(){
				@Override
				public void onClick() {
					currentSettingCot = 0;
					general.lock(true);
				}
			});
		Interface = new button("", general.X + general.width + width / 128, height - width / 32 * 3, width / 16 * 4, width / 16);
	    Interface.setTextColor(Color.BLACK);
		Interface.setText(MenuScreen.langs.getString("interface"));
		Interface.setEventListener(new button.EventListener(){
				@Override
				public void onClick() {
					currentSettingCot = 1;
					Interface.lock(true);
				}
			});
		gameSettings = new button("", Interface.X + Interface.width + width / 128, height - width / 32 * 3, width / 16 * 3, width / 16);
	    gameSettings.setTextColor(Color.BLACK);
		gameSettings.setText(MenuScreen.langs.getString("game"));
		gameSettings.setEventListener(new button.EventListener(){
				@Override
				public void onClick() {
					currentSettingCot = 2;
					gameSettings.lock(true);
				}
			});
		sound = new button("", gameSettings.X + gameSettings.width + width / 128, height - width / 32 * 3, width / 16 * 2, width / 16);
		sound.setTextColor(Color.BLACK);
		sound.setText(MenuScreen.langs.getString("sound"));
		sound.setEventListener(new button.EventListener(){
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
			nextLang.update(MenuScreen.camera);
			prevLang.update(MenuScreen.camera);
			debug.update(MenuScreen.camera);
		} else if (currentSettingCot == 1) {
			Interface.lock(true);
		} else if (currentSettingCot == 2) {
			gameSettings.lock(true);
			AIPU.update(MenuScreen.camera);
			useConsole.update(MenuScreen.camera);
		} else if (currentSettingCot == 3){
			sound.lock(true);
		    musicVolume.update(MenuScreen.camera);
		}
		Notification.update(MenuScreen.camera);
		closeButton.update(MenuScreen.camera);
		general.update(MenuScreen.camera);
		Interface.update(MenuScreen.camera);
		gameSettings.update(MenuScreen.camera);
		sound.update(MenuScreen.camera);
		batch.begin();
		batch.draw(t.standartBackground_full, MenuScreen.camera.X, MenuScreen.camera.Y, width, height);
		batch.draw(background0, MenuScreen.camera.X + width / 32, MenuScreen.camera.Y + width / 32, width / 16 * 15, height - width / 16 * 2);
		closeButton.render(batch, MenuScreen.camera);
		general.render(batch, MenuScreen.camera);
		Interface.render(batch, MenuScreen.camera);
		gameSettings.render(batch, MenuScreen.camera);
		sound.render(batch, MenuScreen.camera);
		if (currentSettingCot == 0) {
			try {
				batch.draw(background1, MenuScreen.camera.X + width / 16, MenuScreen.camera.Y + height - width / 16 * 4, width / 16 * 3, width / 16);
				bf.drawMultiLine(batch, MenuScreen.langs.getString("language"), MenuScreen.camera.X + width / 16, MenuScreen.camera.Y + height - width / 16 * 2 - (width / 16 / 4), width / 16 * 3, BitmapFont.HAlignment.CENTER);
				bf.drawMultiLine(batch, availableLangs.getString(MenuScreen.settings.lang), MenuScreen.camera.X + width / 16, MenuScreen.camera.Y + height - width / 16 * 3 - (width / 16 / 4), width / 16 * 3, BitmapFont.HAlignment.CENTER);
			    bf.drawMultiLine(batch, MenuScreen.langs.getString("debug"), MenuScreen.camera.X + width / 16, MenuScreen.camera.Y + height - width / 16 * 5 - (width / 16 / 4), width / 16 * 3, BitmapFont.HAlignment.CENTER);
				nextLang.render(batch, MenuScreen.camera);
				prevLang.render(batch, MenuScreen.camera);
				debug.render(batch, MenuScreen.camera);
			} catch (Exception e) {

			}
		} else if (currentSettingCot == 1) {
			
		} else if (currentSettingCot == 2) {
			bf.drawMultiLine(batch, MenuScreen.langs.getString("autoitemspickup"), MenuScreen.camera.X + width / 16, MenuScreen.camera.Y + height - width / 16 * 2 - (width / 16 / 4), width / 16 * 7, BitmapFont.HAlignment.LEFT);
			AIPU.render(batch, MenuScreen.camera);
			bf.drawMultiLine(batch, MenuScreen.langs.getString("useConsole"), MenuScreen.camera.X + width / 16, MenuScreen.camera.Y + height - width / 16 * 3 - (width / 16 / 4), width / 16 * 7, BitmapFont.HAlignment.LEFT);
			useConsole.render(batch, MenuScreen.camera);
		} else if(currentSettingCot == 3){
			bf.drawMultiLine(batch, MenuScreen.langs.getString("music"), MenuScreen.camera.X + width / 16, MenuScreen.camera.Y + height - width / 16 * 2 - (width / 16 / 4), width / 16 * 2, BitmapFont.HAlignment.LEFT);
		    musicVolume.render(MenuScreen.camera, batch);
			bf.drawMultiLine(batch, musicVolume.getValue() + "", MenuScreen.camera.X + musicVolume.X + musicVolume.width + width / 32, MenuScreen.camera.Y + musicVolume.Y + musicVolume.height, width / 16 * 2, BitmapFont.HAlignment.LEFT);
		}
		Notification.render(batch, MenuScreen.camera);
		batch.end();
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

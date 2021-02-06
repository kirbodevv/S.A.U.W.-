package com.KGC.SAUW.screen;

import com.KGC.SAUW.Camera2D;
import com.KGC.SAUW.InterfaceAPI.Button;
import com.KGC.SAUW.InterfaceAPI.Checkbox;
import com.KGC.SAUW.InterfaceAPI.InterfaceElement;
import com.KGC.SAUW.InterfaceAPI.Slider;
import com.KGC.SAUW.MainGame;
import com.KGC.SAUW.Textures;
import com.KGC.SAUW.screen.MenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONException;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ModsScreen implements Screen {
	private class Mod {
		public Texture modIcon;
		public String modName;
		public Mod(String modPath) {
			FileHandle manifestFile = Gdx.files.external(modPath + "/manifest.json");
			try {
				JSONObject manifeat = new JSONObject(manifestFile.readString());

				modName = Gdx.files.external(modPath).name();
				modIcon = new Texture(Gdx.files.external(modPath + "/" + manifeat.getString("icon")));
			}
			catch (Exception e) {
				Gdx.app.log("ModInfoLoadError", e.toString());
			}
		}
	}
	private ArrayList<Mod> Mods = new ArrayList<Mod>();
	private SpriteBatch batch;
	private Camera2D cam;
	private Textures t;
	private int width = Gdx.graphics.getWidth();
	private int height = Gdx.graphics.getHeight();

	private Button closeButton;
	private Button upButton, downButton;
	private Slider slider;
	private ModInfo modInfo0;
	private ModInfo modInfo1;
	private ModInfo modInfo2;

	private Texture background1;

	public ModsScreen(final MainGame game, Textures t, final MenuScreen ms) {
		this.batch = ms.b;
		this.cam = ms.camera;
		this.t = t;
		background1 = Textures.generateTexture(13, height / (width / 16) - 1, false);
		closeButton = new Button("closeButton", width - width / 16, height - width / 16, width / 32, width / 32, t.closeButton, t.closeButton);
		closeButton.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					game.setScreen(ms);
				}
			});
		Texture ubt = Textures.generateTexture(1, 1, true, "Interface/button_up_0.png");
		Texture ubt0 = Textures.generateTexture(1, 1, false, "Interface/button_up_0.png");
		Texture udt = Textures.generateTexture(1, 1, true, "Interface/button_down_0.png");
		Texture udt0 = Textures.generateTexture(1, 1, false, "Interface/button_down_0.png");
		upButton = new Button("upButton", width / 32 * 27, height - width / 32 * 3, width / 16, width / 16, ubt, ubt0);
		downButton = new Button("downButton", width / 32 * 27, width / 32, width / 16, width / 16, udt, udt0);
		slider = new Slider(width / 16 * 13 + width / 64 * 3, width / 32, width / 32, height - width / 16);
		//slider.setMaxValue(5);
		modInfo2 = new ModInfo(width / 16, width / 32 * 2);
		modInfo1 = new ModInfo(width / 16, width / 32 * 7);
		modInfo0 = new ModInfo(width / 16, width / 32 * 12);
		FileHandle modsFolder = Gdx.files.external("S.A.U.W./Mods");
		FileHandle[] mods = modsFolder.list();
		for (FileHandle mod : mods) {
			if (mod.isDirectory()) {
				if (mod.child("manifest.json").exists()) this.Mods.add(new Mod(mod.path()));
			}
		}
		slider.setMaxValue((Mods.size() - 1) * 40);
		slider.setEventListener(new Slider.EventListener(){
				@Override
				public void onValueChange(int v1) {
					int v = (int)Math.floor(v1 / 40);
					modInfo0.hide(false);
					modInfo1.hide(false);
					modInfo2.hide(false);

					modInfo0.setMod(Mods.get(v));
					if (v + 1 < Mods.size()) modInfo1.setMod(Mods.get(v + 1));
					else modInfo1.hide(true);
					if (v + 2 < Mods.size()) modInfo2.setMod(Mods.get(v + 2));
					else modInfo2.hide(true);
				}
			});
		if (Mods.size() <= 3) slider.hide(true);
	}

	@Override
	public void render(float p1) {
		closeButton.update(cam);
		slider.update(cam);
		batch.begin();
		batch.draw(t.standartBackground_full, cam.X, cam.Y, width, height);
		batch.draw(background1, cam.X + width / 32, cam.Y + width / 32, width / 16 * 13, height - (width / 16));
		closeButton.render(batch, cam);
		modInfo0.render(batch, cam);
		modInfo1.render(batch, cam);
		modInfo2.render(batch, cam);
		slider.render(batch, cam);

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
	public class ModInfo extends InterfaceElement {
		private int WIDTH = Gdx.graphics.getWidth();
		private Texture background;
		private Checkbox modActiv;
		private Texture modIcon;
		private String modName;
		private BitmapFont text = new BitmapFont(Gdx.files.internal("ttf.fnt"));

		public ModInfo(int X, int Y) {
			setPosition(X, Y);
			setSize(WIDTH / 16 * 12, WIDTH / 16 * 2);
			background = Textures.generateTexture(13, 2, true);
			modActiv = new Checkbox(t.switch_0, t.switch_1);
			modActiv.setSize(WIDTH / 16, WIDTH / 16);
			modActiv.setPosition(X + width - modActiv.width - WIDTH / 32, Y + (height - modActiv.height) / 2);
		}
		public void setMod(Mod mod) {
			this.modIcon = mod.modIcon;
			this.modName = mod.modName;
		}
		@Override
		public void update(Camera2D cam) {
			if (!isHided()) {
				super.update(cam);
				modActiv.update(cam);
			}
		}

		@Override
		public void render(SpriteBatch batch, Camera2D cam) {
			if (!isHided()) {
				super.render(batch, cam);
				batch.draw(background, cam.X + X, cam.Y + Y, width, height);
				batch.draw((modIcon == null) ? t.SAUWIcon : modIcon, cam.X + X + height / 8, cam.Y + Y + height / 8, height - height / 8 * 2, height - height / 8 * 2);
				text.draw(batch, modName, cam.X + X, cam.Y + Y + height);
				modActiv.render(batch, cam);
			}
		}
	}
}

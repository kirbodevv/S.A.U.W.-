package com.KGC.SAUW.screen;

import com.KGC.SAUW.Camera2D;
import com.KGC.SAUW.InterfaceAPI.Button;
import com.KGC.SAUW.MainGame;
import com.KGC.SAUW.Textures;
import com.KGC.SAUW.screen.MenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.KGC.SAUW.InterfaceAPI.InterfaceElement;
import com.KGC.SAUW.InterfaceAPI.Image;
import com.KGC.SAUW.InterfaceAPI.Checkbox;

public class ModsScreen implements Screen {
	private SpriteBatch batch;
	private Camera2D cam;
	private Textures t;
	private int width = Gdx.graphics.getWidth();
	private int height = Gdx.graphics.getHeight();

	private Button closeButton;
	private Button upButton, downButton;

	private ModInfo modInfo0;
	private ModInfo modInfo1;
	private ModInfo modInfo2;
	
	private Texture background1;
	
	public ModsScreen(final MainGame game, Textures t, final MenuScreen ms){
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
		modInfo0 = new ModInfo(width / 16, width / 32 * 2);
		modInfo1 = new ModInfo(width / 16, width / 32 * 7);
		modInfo2 = new ModInfo(width / 16, width / 32 * 12);
	}
	@Override
	public void render(float p1) {
		closeButton.update(cam);
		upButton.update(cam);
		downButton.update(cam);
		batch.begin();
		batch.draw(t.standartBackground_full, cam.X, cam.Y, width, height);
		batch.draw(background1, cam.X + width / 32, cam.Y + width / 32, width / 16 * 13, height - (width / 16));
		closeButton.render(batch, cam);
		upButton.render(batch, cam);
		downButton.render(batch, cam);
		modInfo0.render(batch, cam);
		modInfo1.render(batch, cam);
		modInfo2.render(batch, cam);
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
		public ModInfo(int X, int Y){
			setPosition(X, Y);
			setSize(WIDTH / 16 * 12, WIDTH / 16 * 2);
			background = Textures.generateTexture(13, 2, true);
			modActiv = new Checkbox(t.switch_0, t.switch_1);
			modActiv.setSize(WIDTH / 16, WIDTH / 16);
			modActiv.setPosition(X + width - modActiv.width - WIDTH / 32, Y + (height - modActiv.height) / 2);
		}

		@Override
		public void update(Camera2D cam) {
			super.update(cam);
			modActiv.update(cam);
		}
		
		@Override
		public void render(SpriteBatch batch, Camera2D cam) {
			super.render(batch, cam);
			batch.draw(background, cam.X + X, cam.Y + Y, width, height);
			batch.draw(t.SAUWIcon, cam.X + X + height / 8, cam.Y + Y + height / 8, height - height / 8 * 2, height - height / 8 * 2);
			modActiv.render(batch, cam);
		}
	}
}

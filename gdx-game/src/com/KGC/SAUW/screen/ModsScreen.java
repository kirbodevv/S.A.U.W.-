package com.KGC.SAUW.screen;

import com.KGC.SAUW.Camera2D;
import com.KGC.SAUW.InterfaceAPI.Button;
import com.KGC.SAUW.MainGame;
import com.KGC.SAUW.Textures;
import com.KGC.SAUW.screen.MenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ModsScreen implements Screen {
	private SpriteBatch batch;
	private Camera2D cam;
	private Textures t;
	private int width = Gdx.graphics.getWidth();
	private int height = Gdx.graphics.getHeight();

	private Button closeButton;
	
	public ModsScreen(final MainGame game, Textures t, final MenuScreen ms){
		this.batch = ms.b;
		this.cam = ms.camera;
		this.t = t;
		closeButton = new Button("closeButton", width - width / 16, height - width / 16, width / 32, width / 32, t.closeButton, t.closeButton);
		closeButton.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					game.setScreen(ms);
				}
			});
	}
	@Override
	public void render(float p1) {
		closeButton.update(cam);
		batch.begin();
		batch.draw(t.standartBackground_full, cam.X, cam.Y, width, height);
		closeButton.render(batch, cam);
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

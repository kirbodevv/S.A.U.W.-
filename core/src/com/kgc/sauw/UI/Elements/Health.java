package com.kgc.sauw.UI.Elements;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.utils.Camera2D;

public class Health {
	SpriteBatch b;
	Camera2D cam;
	Texture t0;
	Texture t1;
	int w;
	int h;
	int x = 0;
	boolean hided;
	public void hide(boolean h) {
		this.hided = h;
	}
    public Health(SpriteBatch b, Camera2D cam, Texture t0, Texture t1) {
		this.b = b;
		this.cam = cam;
		this.t0 = t0;
		this.t1 = t1;
		w = cam.W;
		h = cam.H;
		if(Gdx.app.getType() == Application.ApplicationType.Desktop){
			x = 0;
		} else if (Gdx.app.getType() == Application.ApplicationType.Android){
			x = w / 16;
		}
	}
	public void render(int currentHealth, int maxHealth) {
		if (!hided) {
			int c1 = (maxHealth % 10 == 0) ? maxHealth / 10 : (maxHealth - (maxHealth % 10)) / 10 + 1;
			int c2 = (currentHealth % 10 == 0) ? currentHealth / 10 : (currentHealth - (currentHealth % 10)) / 10 + 1;
			for (int i = 0; i < c1; i++) {
				int num = (maxHealth % 10 != 0 && i == c1 - 1) ? maxHealth % 10 : 10;
				for (int j = 0; j < num; j++) {
					b.draw(t1, x + j * (w / 32) + cam.X, (h - (w / 32)) - i * (w / 32) + cam.Y, w / 32, w / 32);
				}
			}
			for (int i = 0; i < c2; i++) {
				int num = (currentHealth % 10 != 0 && i == c2 - 1) ? currentHealth % 10 : 10;
				for (int j = 0; j < num; j++) {
					b.draw(t0, x + j * (w / 32) + cam.X, (h - (w / 32)) - i * (w / 32) + cam.Y, w / 32, w / 32);
				}
			}
		}
	}
}
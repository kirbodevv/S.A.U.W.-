package com.kgc.sauw;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Health {
	SpriteBatch b;
	Camera2D cam;
	Texture t0;
	Texture t1;
	int w;
	int h;
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
	}
	public void render(int currentHealth, int maxHealth) {
		if (!hided) {
			int c1 = (maxHealth % 10 == 0) ? maxHealth / 10 : (maxHealth - (maxHealth % 10)) / 10 + 1;
			int c2 = (currentHealth % 10 == 0) ? currentHealth / 10 : (currentHealth - (currentHealth % 10)) / 10 + 1;
			for (int i = 0; i < c1; i++) {
				int num = (maxHealth % 10 != 0 && i == c1 - 1) ? maxHealth % 10 : 10;
				for (int j = 0; j < num; j++) {
					b.draw(t1, w / 16 + j * (w / 32) + cam.X, (h - (w / 32)) - i * (w / 32) + cam.Y, w / 32, w / 32);
				}
			}
			for (int i = 0; i < c2; i++) {
				int num = (currentHealth % 10 != 0 && i == c2 - 1) ? currentHealth % 10 : 10;
				for (int j = 0; j < num; j++) {
					b.draw(t0, w / 16 + j * (w / 32) + cam.X, (h - (w / 32)) - i * (w / 32) + cam.Y, w / 32, w / 32);
				}
			}
		}
	}
}

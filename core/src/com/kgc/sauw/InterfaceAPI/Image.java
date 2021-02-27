package com.kgc.sauw.InterfaceAPI;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.Camera2D;
import com.badlogic.gdx.math.Vector2;

public class Image extends InterfaceElement {
	private float x, y, w, h;
	private Texture t;
    public Image(int x, int y, int w, int h){
		setPosition(x, y);
		setSize(w, h);
	}
	public void setImg(Texture t){
		this.t = t;
	}

	@Override
	public void render(SpriteBatch batch, Camera2D cam) {
		super.render(batch, cam);
		batch.draw(t, x + cam.X, y + cam.Y, w, h);
	}
}

package com.KGC.SAUW.InterfaceAPI;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.KGC.SAUW.Camera2D;
import com.badlogic.gdx.math.Vector2;

public class Image {
	private float x, y, w, h;
	private Texture t;
    public Image(Texture t, int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.t = t;
	}
	public void render(SpriteBatch b, Camera2D cam){
		b.draw(t, x + cam.X, y + cam.Y, w, h);
	} 
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}
	public void setPosition(Vector2 pos){
		this.x = pos.x;
		this.y = pos.y;
	}
	public Vector2 getPosition(){
		return new Vector2(x, y);
	}
	public void setSize(float w, float h){
		this.w = w;
		this.h = h;
	}
	public Vector2 getSize(){
		return new Vector2(w, h);
	}
}

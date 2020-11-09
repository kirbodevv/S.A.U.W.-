package com.KGC.SAUW.InterfaceAPI;
import com.KGC.SAUW.Textures;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.KGC.SAUW.Camera2D;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Slider extends InterfaceElement {
	private Texture background, slider;
	private int sliderW;
	private int maxValue = 100;
	private int value = 0;
	private double sc;
	private EventListener EL;
	public Slider(int x, int y, int w, int h) {
		int ww = Gdx.graphics.getWidth();
		this.X = x;
		this.Y = y;
		this.width = w;
		this.height = h;
		sliderW = h / 2;
		slider = Textures.generateTexture(sliderW / (ww / 16.0f), h / (ww / 16.0f), true/*, new Color(0xCCCCCCFF), new Color(0xE0E0E0FF), new Color(0xF8F8F8FF), new Color(0xA0A0A0FF)*/);
	    background = Textures.generateTexture(w / (ww / 16.0f), h / 2 / (ww / 16.0f), new Color(0x383838FF), new Color(0x000000FF), new Color(0x000000FF), new Color(0x000000FF));
	    setMaxValue(100);
	}
	public void setValue(int v){
		this.value = v;
	}
	public int getValue(){
		return value;
	}
	public void setMaxValue(int v){
		this.maxValue = v;
		sc = (width + 0.0) / maxValue;
	}
	@Override
	public void update(Camera2D cam) {
		super.update(cam);
		if(isTouched()){
			value = (int)((Gdx.input.getX() - X) / sc);
			if(value < 0) value = 0;
			if(value > maxValue) value = maxValue;
			}
			if(EL != null) EL.onValueChange(value);
	}
	public void render(Camera2D cam, SpriteBatch b) {
		b.draw(background, cam.X + X, cam.Y + Y + height / 4, width, height / 2);
		b.draw(slider, cam.X + X + (int)(sc * value) - sliderW / 2, cam.Y + Y, sliderW, height);
	}
	public void setEventListener(EventListener EL){
		this.EL = EL;
	}
	public static abstract class EventListener{
		public abstract void onValueChange(int v);
	}
}

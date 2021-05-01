package com.kgc.sauw.ui.elements;
import com.kgc.sauw.resource.Textures;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.kgc.sauw.utils.Camera2D;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.ui.InterfaceElement;

public class Slider extends InterfaceElement {
	private Texture background, slider;
	private int sliderW;
	private int maxValue = 100;
	private int value = 0;
	private double sc;
	private EventListener EL;
	private boolean verticalSlider;
	public Slider(int x, int y, int w, int h) {
		if (w > h) verticalSlider = false; 
		else verticalSlider = true;
		int ww = Gdx.graphics.getWidth();
		this.X = x;
		this.Y = y;
		this.width = w;
		this.height = h;
		if (!verticalSlider) sliderW = h / 2;
		else sliderW = w / 2;
		if (!verticalSlider) {
			slider = Textures.generateTexture(sliderW / (ww / 16.0f), h / (ww / 16.0f), true);
			background = Textures.generateTexture(w / (ww / 16.0f), h / 2 / (ww / 16.0f), new Color(0x383838FF), new Color(0x000000FF), new Color(0x000000FF), new Color(0x000000FF));
		} else {
			slider = Textures.generateTexture(w / (ww / 16.0f), sliderW / (ww / 16.0f), true);
			background = Textures.generateTexture(w / 2 / (ww / 16.0f), h / (ww / 16.0f), new Color(0x383838FF), new Color(0x000000FF), new Color(0x000000FF), new Color(0x000000FF));
		}
	    setMaxValue(100);
	}
	public void setValue(int v) {
		this.value = v;
	}
	public int getValue() {
		return value;
	}
	public void setMaxValue(int v) {
		this.maxValue = v;
		if (!verticalSlider)
			sc = (width + 0.0) / maxValue;
		else sc = (height + 0.0) / maxValue;
	}
	@Override
	public void update(Camera2D cam) {
		if (!isHidden()) {
			super.update(cam);
			if (isTouched()) {
				if (!verticalSlider)
					value = (int)((Gdx.input.getX() - X) / sc);
				else 
					value = (int)((Gdx.input.getY() - Y) / sc);

				if (value < 0) value = 0;
				if (value > maxValue) value = maxValue;
			}
			if (EL != null) EL.onValueChange(value);
		}
	}
	@Override
	public void render(SpriteBatch b, Camera2D cam) {
		if (!isHidden()) {
			if (!verticalSlider) {
				b.draw(background, cam.X + X, cam.Y + Y + height / 4, width, height / 2);
				b.draw(slider, cam.X + X + (int)(sc * value) - sliderW / 2, cam.Y + Y, sliderW, height);
			} else {
				b.draw(background, cam.X + X + width / 4, cam.Y + Y, width / 2, height);
				b.draw(slider, cam.X + X, cam.Y + Y + (height - (int)(sc * value)) - sliderW / 2, width, sliderW);
			}
		}
	}
	public void setEventListener(EventListener EL) {
		this.EL = EL;
	}
	public static abstract class EventListener {
		public abstract void onValueChange(int v);
	}
}

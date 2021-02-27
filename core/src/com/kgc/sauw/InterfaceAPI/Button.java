package com.kgc.sauw.InterfaceAPI;
import com.kgc.sauw.Camera2D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kgc.sauw.Textures;

public class Button extends InterfaceElement {
	private Texture buttonTexture;
	private Texture buttonPressedTexture;
	private String txt;
	private BitmapFont buttonText;
	public Button.EventListener EventListener = null;
	public boolean generatedTextures = false;
    private boolean locked = false;
	private float capHeight;
	public Button(String ID, int X, int Y, int w, int h, Texture BT, Texture BP) {
		generatedTextures = false;
		createButton(ID, X, Y, w, h, BT, BP);
	}
	public Button(String ID, int X, int Y, int w, int h) {
		generatedTextures = true;
		createButton(ID, X, Y, w, h);
	}
	private void createButton(String ID, int X, int Y, int w, int h, Texture BT, Texture BP) {
		setPosition(X, Y);
		setSize(w, h);
		setTextures(BT, BP);
		setID(ID);
	}
	private void createButton(String ID, int X, int Y, int w, int h) {
		setPosition(X, Y);
		setSize(w, h);
		setID(ID);
	}
	public Texture generateBTexture() {
		int W = Gdx.graphics.getWidth();
		return Textures.generateTexture(width / (W / 16), height / (W / 16), true);
	}
	public Texture generateBPTexture() {
		int W = Gdx.graphics.getWidth();
		return Textures.generateTexture(width / (W / 16), height / (W / 16), false);
	}
	public void setTextColor(Color c) {
		createBitmapFont();
		buttonText.setColor(c);
	}
	public void setText(String text) {
		txt = text;
		createBitmapFont();
		if (buttonText.getBounds(text).width > this.width) {
			setSize(height / 2 + (int)buttonText.getBounds(text).width, this.height);
		}
	}
	public void createBitmapFont() {
		if (buttonText == null) {
			buttonText = new BitmapFont(Gdx.files.internal("ttf.fnt"));
			capHeight = buttonText.getCapHeight();
			setTextScale();
			buttonText.setColor(Color.BLACK);
		}
	}
	public void setTextScale() {
		if (buttonText != null) {
			buttonText.setScale(height / 2 / capHeight);
		}
	}
	public void setTextures(Texture t0, Texture t1) {
		this.buttonTexture = t0;
		this.buttonPressedTexture = t1;
	}
	public void setEventListener(Button.EventListener el) {
		EventListener = el;
	}
	@Override
	public void update(Camera2D cam) {
		super.update(cam);
		setTextScale();
	}
	public void lock(boolean b) {
		locked = b;
	}
	@Override
	public void render(SpriteBatch b, Camera2D cam) {
		if (!isHided()) {
			if (!locked)
				b.draw(isTouched() ? buttonPressedTexture : buttonTexture, cam.X + X, cam.Y + Y, width, height);
			else b.draw(buttonPressedTexture, cam.X + X, cam.Y + Y, width, height);

			if (buttonText != null)
				buttonText.drawMultiLine(b, txt, cam.X + X, cam.Y + Y + (height / 4 * 3), width, BitmapFont.HAlignment.CENTER);
		}
	}

	@Override
	public void setSize(int w, int h) {
		super.setSize(w, h);
		if (generatedTextures) {
			if (buttonTexture != null) buttonTexture.dispose();
			if (buttonPressedTexture != null) buttonPressedTexture.dispose();
			setTextures(generateBTexture(), generateBPTexture());
		}
	}
	@Override
	public void onClick(boolean onButton) {
		super.onClick(onButton);
		if (EventListener != null && onButton) {
			EventListener.onClick();
		}
	}
    public void dispose() {
		buttonTexture.dispose();
		buttonPressedTexture.dispose();
		buttonText.dispose();
	}
	public static abstract class EventListener {
		public abstract void onClick();
	}
}

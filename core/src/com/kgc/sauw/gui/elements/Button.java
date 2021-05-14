package com.kgc.sauw.gui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.kgc.sauw.gui.InterfaceElement;
import com.kgc.sauw.resource.Textures;
import com.kgc.sauw.utils.Camera2D;

import java.util.ArrayList;

public class Button extends InterfaceElement {
    private Texture buttonTexture;
    private Texture buttonPressedTexture;
    private Texture icon;

    private String txt;
    private BitmapFont buttonText;
    public ArrayList<EventListener> eventListeners = new ArrayList<>();
    public boolean generatedTextures;
    private boolean locked = false;
    private float capHeight;
    private GlyphLayout glyphLayout;

    public Button(String ID, float X, float Y, float w, float h, Texture BT, Texture BP) {
        generatedTextures = false;
        createButton(ID, X, Y, w, h, BT, BP);
    }

    public Button(String ID, float X, float Y, float w, float h) {
        generatedTextures = true;
        createButton(ID, X, Y, w, h);
    }

    private void createButton(String ID, float X, float Y, float w, float h, Texture BT, Texture BP) {
        setPosition(X, Y);
        setSize(w, h);
        setTextures(BT, BP);
        setID(ID);
        create();
    }

    private void createButton(String ID, float X, float Y, float w, float h) {
        setPosition(X, Y);
        setSize(w, h);
        setID(ID);
        create();
    }

    public Texture generateBTexture() {
        int W = Gdx.graphics.getWidth();
        return Textures.generateTexture(width / (W / 16.0f), height / (W / 16.0f), true);
    }

    public Texture generateBPTexture() {
        int W = Gdx.graphics.getWidth();
        return Textures.generateTexture(width / (W / 16.0f), height / (W / 16.0f), false);
    }

    public void setTextColor(Color c) {
        createBitmapFont();
        buttonText.setColor(c);
    }

    public void setText(String text) {
        txt = text;
        createBitmapFont();
        glyphLayout.setText(buttonText, text);
        if (glyphLayout.width > this.width) {
            setSize(height / 2 + (int) glyphLayout.width, this.height);
        }
    }

    public void createBitmapFont() {
        if (buttonText == null) {
            buttonText = new BitmapFont(Gdx.files.internal("ttf.fnt"));
            capHeight = buttonText.getCapHeight();
            setTextScale();
            buttonText.setColor(Color.BLACK);
            glyphLayout = new GlyphLayout();
        }
    }

    public void setTextScale() {
        if (buttonText != null) {
            buttonText.getData().setScale(height / 2 / capHeight);
        }
    }

    public void setTextures(Texture t0, Texture t1) {
        this.buttonTexture = t0;
        this.buttonPressedTexture = t1;
    }

    public void setIcon(Texture icon) {
        this.icon = icon;
    }

    public void addEventListener(Button.EventListener eventListener) {
        eventListeners.add(eventListener);
    }

    @Override
    public void tick(Camera2D cam) {
        setTextScale();
    }

    public void lock(boolean b) {
        locked = b;
    }

    @Override
    public void renderTick(SpriteBatch b, Camera2D cam) {
        if (!locked)
            b.draw(isTouched() ? buttonPressedTexture : buttonTexture, cam.X + X, cam.Y + Y, width, height);
        else b.draw(buttonPressedTexture, cam.X + X, cam.Y + Y, width, height);

        if (icon != null) {
            b.draw(icon, cam.X + X, cam.Y + Y, width, height);
        }

        if (buttonText != null)
            buttonText.draw(b, txt, cam.X + X, cam.Y + Y + (height / 4 * 3), width, Align.center, false);
    }

    @Override
    public void setSize(float w, float h) {
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
        for (EventListener e : eventListeners) {
            e.onClick();
        }
    }

    @Override
    public void dispose() {
        buttonTexture.dispose();
        buttonPressedTexture.dispose();
        buttonText.dispose();
    }

    public static abstract class EventListener {
        public abstract void onClick();
    }
}

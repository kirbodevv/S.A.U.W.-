package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.utils.Camera2D;
import com.kgc.sauw.skins.Skins;

import java.util.ArrayList;

public class Button extends InterfaceElement {
    private Texture icon;

    ElementSkin buttonUpSkin;
    ElementSkin buttonDownSkin;

    private String txt;
    private BitmapFont buttonText;
    public ArrayList<EventListener> eventListeners = new ArrayList<>();
    public boolean generatedTextures;
    private boolean locked = false;
    private float capHeight;
    private GlyphLayout glyphLayout;

    public Button(String ID, float X, float Y, float w, float h, ElementSkin BT, ElementSkin BP) {
        generatedTextures = false;
        createButton(ID, X, Y, w, h, BT, BP);
    }

    public Button(String ID, float X, float Y, float w, float h) {
        generatedTextures = true;
        createButton(ID, X, Y, w, h);
    }

    private void createButton(String ID, float X, float Y, float w, float h, ElementSkin BT, ElementSkin BP) {
        setPosition(X, Y);
        setSize(w, h);
        setSkin(BT, BP);
        setID(ID);
        create();
    }

    private void createButton(String ID, float X, float Y, float w, float h) {
        createButton(ID, X, Y, w, h, Skins.round_up, Skins.round_down);
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

    public void setSkin(ElementSkin elementSkin0, ElementSkin elementSkin1) {
        this.buttonUpSkin = elementSkin0;
        this.buttonDownSkin = elementSkin1;
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
            (isTouched() ? buttonDownSkin : buttonUpSkin).draw(cam.X + x, cam.Y + y, width, height);
        else buttonDownSkin.draw(cam.X + x, cam.Y + y, width, height);

        if (icon != null) {
            b.draw(icon, cam.X + x, cam.Y + y, width, height);
        }

        if (buttonText != null)
            buttonText.draw(b, txt, cam.X + x, cam.Y + y + (height / 4 * 3), width, Align.center, false);
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
        buttonText.dispose();
    }

    public static abstract class EventListener {
        public abstract void onClick();
    }
}

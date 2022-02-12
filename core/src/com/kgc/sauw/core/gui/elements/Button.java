package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.utils.Camera2D;

public class Button extends AbstractTextView {
    private Texture icon;

    ElementSkin buttonUpSkin;
    ElementSkin buttonDownSkin;

    public boolean generatedTextures;
    private boolean locked = false;

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
        setId(ID);
        setTextColor(Color.BLACK);
    }

    private void createButton(String ID, float X, float Y, float w, float h) {
        createButton(ID, X, Y, w, h, Skins.round_up, Skins.round_down);
    }

    public void setSkin(ElementSkin elementSkin0, ElementSkin elementSkin1) {
        this.buttonUpSkin = elementSkin0;
        this.buttonDownSkin = elementSkin1;
        defaultColor = new Color(buttonUpSkin.getColor());
        buttonColor = new Color(defaultColor);
    }

    public void setIcon(Texture icon) {
        this.icon = icon;
    }

    public void lock(boolean b) {
        locked = b;
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void preRender(SpriteBatch batch, Camera2D cam) {
    }

    @Override
    protected void tick(Camera2D cam) {
    }

    private Color buttonColor;
    private Color defaultColor;
    private final Color onHoverColor = new Color(0xAC9262FF);

    @Override
    public void renderTick(SpriteBatch b, Camera2D cam) {
        (isTouched() || locked ? buttonDownSkin : buttonUpSkin).draw(cam.X + x, cam.Y + y, width, height, buttonColor, hover ? onHoverColor : defaultColor);
        if (icon != null) {
            b.draw(icon, cam.X + x, cam.Y + y, width, height);
        }
        if (!text.equals("")) super.renderTick(b, cam);
    }

    @Override
    protected void onClick() {
        if (!locked)
            super.onClick();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onClick(boolean onElement) {
    }
}

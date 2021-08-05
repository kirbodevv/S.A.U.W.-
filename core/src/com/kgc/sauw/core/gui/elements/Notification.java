package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.utils.Camera2D;
import com.kgc.sauw.game.skins.Skins;

public class Notification extends InterfaceElement {
    private final Text title;
    private final Text text;

    public Notification() {
        title = new Text();
        text = new Text();
        setTitle("fdfd");
        setText("ffddfdfd");
    }

    private void setTextPosition() {
        title.attachTo(this, Sides.TOP, Sides.TOP);
        text.attachTo(title, Sides.TOP, Sides.TOP);
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        setTextPosition();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setTextPosition();
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    @Override
    protected void renderTick(SpriteBatch batch, Camera2D cam) {
        Skins.round_down.draw(x, y, width, height);
        title.render(batch, cam);
        text.render(batch, cam);
    }
}

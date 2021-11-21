package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.utils.Camera2D;
import com.kgc.sauw.core.skins.Skins;

public class Notification extends InterfaceElement {
    private final TextView title;
    private final TextView textView;

    public Notification() {
        title = new TextView();
        textView = new TextView();
        setTitle("fdfd");
        setText("ffddfdfd");
    }

    private void setTextPosition() {
        title.attachTo(this, Sides.TOP, Sides.TOP);
        textView.attachTo(title, Sides.TOP, Sides.TOP);
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        setTextPosition();
    }

    @Override
    protected void tick(Camera2D cam) {
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
        this.textView.setText(text);
    }

    @Override
    protected void renderTick(SpriteBatch batch, Camera2D cam) {
        Skins.round_down.draw(x, y, width, height);
        title.render(batch, cam);
        textView.render(batch, cam);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onClick(boolean onElement) {
    }
}

package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.utils.Camera2D;

public class MultilineTextView extends InterfaceElement {
    private String text = "";
    private TextView[] textViews;
    private ElementSkin background = Skins.round_up;

    public MultilineTextView(int linesCount) {
        textViews = new TextView[linesCount];
        for (int i = 0; i < linesCount; i++) {
            textViews[i] = new TextView();
            textViews[i].setBackground(Skins.transparent);
        }
    }

    public void setBackground(ElementSkin background) {
        this.background = background;
    }

    public void setText(String text) {
        this.text = text;
        String[] lines = text.split("\n");
        for (int i = 0; i < textViews.length; i++) {
            if (i < lines.length) textViews[i].setText(lines[i]);
            else textViews[i].setText("");
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        textViews[0].setPosition(x, y + height - height / textViews.length);
        for (int i = 1; i < textViews.length; i++) {
            textViews[i].attachTo(textViews[i - 1], Sides.TOP, Sides.BOTTOM);
        }
    }

    public void setTextAlign(int align) {
        for (TextView textView : textViews) {
            textView.setTextAlign(align);
        }
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        float height = h / textViews.length;
        for (TextView textView : textViews) {
            textView.setSize(w, height);
        }
    }

    @Override
    protected void tick(Camera2D cam) {
        for (TextView textView : textViews) {
            textView.update(cam);
        }
    }

    @Override
    protected void renderTick(SpriteBatch batch, Camera2D cam) {
        if (background != null) background.draw(cam.X + x, cam.Y + y, width, height);
        for (TextView textView : textViews) {
            textView.render(batch, cam);
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onClick(boolean onElement) {
    }

}

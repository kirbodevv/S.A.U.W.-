package com.kgc.sauw.core.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kgc.sauw.core.utils.GravityAdapter;
import com.kgc.utils.Camera2D;

import java.util.ArrayList;

import static com.kgc.sauw.core.graphic.Graphic.BLOCK_SIZE;

public abstract class InterfaceElement {
    public enum Sides {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        CENTER,
        LEFT_TOP,
        LEFT_BOTTOM,
        RIGHT_TOP,
        RIGHT_BOTTOM
    }

    public float x, y, width, height;

    public float BX = 0f, BY = 0f, BWidth = 0f, BHeight = 0f;
    protected boolean hidden;
    protected boolean isTouched;
    protected boolean this_touched;
    public String id = "";
    public boolean wasClicked;
    public boolean wasUp;
    protected boolean hover;
    public InterfaceElement attachedTo;
    protected Sides attachableSide, attachedToSide;

    public float marginTop, marginBottom, marginLeft, marginRight;
    public float translationX = 0, translationY = 0;

    public ArrayList<OnClickListener> eventListeners = new ArrayList<>();

    public final void update(Camera2D cam) {
        wasClicked = false;
        wasUp = false;

        if (!hidden) {
            hover = Gdx.input.getX() > x && Gdx.input.getX() < x + width && cam.H - Gdx.input.getY() > y && cam.H - Gdx.input.getY() < y + height;
            if (Gdx.input.isTouched()) {
                if (!isTouched) {
                    if (Gdx.input.getX() > x && Gdx.input.getX() < x + width && cam.H - Gdx.input.getY() > y && cam.H - Gdx.input.getY() < y + height)
                        this_touched = true;
                    isTouched = true;

                }
            }
            if (this_touched && !Gdx.input.isTouched()) {
                if (Gdx.input.getX() > x && Gdx.input.getX() < x + width && cam.H - Gdx.input.getY() > y && cam.H - Gdx.input.getY() < y + height) {
                    onClick(true);
                    onClick();
                    wasClicked = true;
                } else {
                    onClick(false);
                    onClick();
                }
                wasUp = true;
                this_touched = false;
            }
            if (!Gdx.input.isTouched())
                isTouched = false;
            tick(cam);
        }
    }

    private void onClick() {
        for (OnClickListener e : eventListeners) {
            e.onClick();
        }
    }

    public void addEventListener(OnClickListener eventListener) {
        eventListeners.add(eventListener);
    }

    public final void render(SpriteBatch batch, Camera2D cam) {
        if (!hidden) renderTick(batch, cam);
    }

    public void setMarginLeft(float marginLeft) {
        this.marginLeft = marginLeft;
    }

    public void setMarginRight(float marginRight) {
        this.marginRight = marginRight;
    }

    public void setMarginTop(float marginTop) {
        this.marginTop = marginTop;
    }

    public void setMarginBottom(float marginBottom) {
        this.marginBottom = marginBottom;
    }

    public void setTranslationX(float translationX) {
        this.translationX = translationX;
    }

    public void setTranslationY(float translationY) {
        this.translationY = translationY;
    }

    public void setMargin(float right, float left, float top, float bottom) {
        this.marginLeft = left;
        this.marginRight = right;
        this.marginTop = top;
        this.marginBottom = bottom;
    }

    public void setMargin(float margin) {
        this.marginLeft = margin;
        this.marginRight = margin;
        this.marginTop = margin;
        this.marginBottom = margin;
    }

    public void attachTo(InterfaceElement element, Sides attachableSide, Sides attachedToSide) {
        this.attachedTo = element;
        this.attachableSide = attachableSide;
        this.attachedToSide = attachedToSide;
        Vector2 position = GravityAdapter.getPosition(this, element, attachableSide, attachedToSide);

        setPosition(position.x, position.y);
        if (attachedToSide == Sides.RIGHT || attachedToSide == Sides.RIGHT_BOTTOM || attachedToSide == Sides.RIGHT_TOP)
            x += element.marginRight * BLOCK_SIZE;
        if (attachedToSide == Sides.LEFT || attachedToSide == Sides.LEFT_BOTTOM || attachedToSide == Sides.LEFT_TOP)
            x -= element.marginLeft * BLOCK_SIZE;
        if (attachedToSide == Sides.TOP || attachedToSide == Sides.LEFT_TOP || attachedToSide == Sides.RIGHT_TOP)
            y += element.marginTop * BLOCK_SIZE;
        if (attachedToSide == Sides.BOTTOM || attachedToSide == Sides.LEFT_BOTTOM || attachedToSide == Sides.RIGHT_BOTTOM)
            y -= element.marginBottom * BLOCK_SIZE;
        setPositionInBlocks(x / BLOCK_SIZE + translationX, y / BLOCK_SIZE + translationY);
    }

    public boolean isTouched() {
        return this_touched;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void hide(boolean b) {
        this.hidden = b;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float w, float h) {
        this.width = w;
        this.height = h;
    }

    public void setPositionInBlocks(float x, float y) {
        this.BX = x;
        this.BY = y;
        setPosition(x * BLOCK_SIZE, y * BLOCK_SIZE);
    }

    public void setSizeInBlocks(float w, float h) {
        this.BWidth = w;
        this.BHeight = h;
        setSize(w * BLOCK_SIZE, h * BLOCK_SIZE);
    }

    public void resize() {
        setPositionInBlocks(BX, BY);
        setSizeInBlocks(BWidth, BHeight);
    }

    protected abstract void tick(Camera2D cam);

    protected abstract void renderTick(SpriteBatch batch, Camera2D cam);

    public abstract void dispose();

    public abstract void onClick(boolean onElement);
}
package com.kgc.sauw.core.gui;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kgc.sauw.core.utils.GravityAdapter;
import com.kgc.utils.Camera2D;

import java.util.ArrayList;

import static com.kgc.sauw.core.graphic.Graphic.BLOCK_SIZE;
import static com.kgc.sauw.core.graphic.Graphic.SCREEN_HEIGHT;
import static com.kgc.sauw.core.input.Input.INPUT_MULTIPLEXER;

public abstract class InterfaceElement implements InputProcessor {
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

    public float bx = 0f, by = 0f, bWidth = 0f, bHeight = 0f;
    protected boolean hidden;
    protected boolean isTouched;
    protected int touchPointer;
    public String id = "";
    public InterfaceElement attachedTo;
    protected Sides attachableSide, attachedToSide;

    public float marginTop, marginBottom, marginLeft, marginRight;
    public float translationX = 0, translationY = 0;

    public ArrayList<OnClickListener> eventListeners = new ArrayList<>();

    public Rectangle elementBounds = new Rectangle();

    public InterfaceElement() {
        INPUT_MULTIPLEXER.addProcessor(this);
    }

    public final void update(Camera2D cam) {
        if (!hidden) {
            /*hover = Gdx.input.getX() - cam.X > x &&
                    Gdx.input.getX() - cam.X < x + width &&
                    cam.H - Gdx.input.getY() - cam.Y > y &&
                    cam.H - Gdx.input.getY() - cam.Y < y + height;
            if (Gdx.input.isTouched()) {
                if (!isTouched) {
                    if (hover)
                        this_touched = true;
                    isTouched = true;

                }
            }
            if (this_touched && !Gdx.input.isTouched()) {
                if (hover) {
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
                isTouched = false;*/
            tick(cam);
        }
    }

    protected void onClick() {
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
        return isTouched;
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
        elementBounds.setPosition(x, y);
    }

    public void setSize(float w, float h) {
        this.width = w;
        this.height = h;
        elementBounds.setSize(w, h);
    }

    public void setPositionInBlocks(float x, float y) {
        this.bx = x;
        this.by = y;
        setPosition(x * BLOCK_SIZE, y * BLOCK_SIZE);
    }

    public void setSizeInBlocks(float w, float h) {
        this.bWidth = w;
        this.bHeight = h;
        setSize(w * BLOCK_SIZE, h * BLOCK_SIZE);
    }

    public void resize() {
        setPositionInBlocks(bx, by);
        setSizeInBlocks(bWidth, bHeight);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int clickY = (int) (SCREEN_HEIGHT - screenY);
        if (!hidden && elementBounds.contains(screenX, clickY)) {
            touchPointer = pointer;
            isTouched = true;
            return false;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int clickY = (int) (SCREEN_HEIGHT - screenY);
        if (!hidden && pointer == touchPointer) {
            touchPointer = -1;
            isTouched = false;
            onClick(!elementBounds.contains(screenX, clickY));
            onClick();
            System.out.println(id);
            return false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    protected abstract void tick(Camera2D cam);

    protected abstract void renderTick(SpriteBatch batch, Camera2D cam);

    public abstract void dispose();

    public abstract void onClick(boolean onElement);
}
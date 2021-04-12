package com.kgc.sauw.UI;

import com.kgc.sauw.utils.Camera2D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InterfaceElement {
    public int X, Y, width, height;
    protected boolean hided;
    protected boolean isTouched;
    protected boolean this_touched;
    public String ID;
    public boolean wasClicked;
    public boolean wasUp;

    public void update(Camera2D cam) {
        wasClicked = false;
        wasUp = false;
        if (!hided) {
            if (Gdx.input.isTouched()) {
                if (!isTouched) {
                    if (Gdx.input.getX() > X && Gdx.input.getX() < X + width && cam.H - Gdx.input.getY() > Y && cam.H - Gdx.input.getY() < Y + height)
                        this_touched = true;
                    isTouched = true;

                }
            }
            if (this_touched && !Gdx.input.isTouched()) {
                if (Gdx.input.getX() > X && Gdx.input.getX() < X + width && cam.H - Gdx.input.getY() > Y && cam.H - Gdx.input.getY() < Y + height) {
                    onClick(true);
                    wasClicked = true;
                } else {
                    onClick(false);
                }
                wasUp = true;
                this_touched = false;
            }
            if (!Gdx.input.isTouched())
                isTouched = false;
        }
    }

    public void render(SpriteBatch batch, Camera2D cam) {

    }

    public void onClick(boolean onButton) {

    }

    public boolean isTouched() {
        return this_touched;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void hide(boolean b) {
        this.hided = b;
    }

    public boolean isHided() {
        return hided;
    }

    public void setPosition(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public void setX(int x) {
        this.X = x;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }
}

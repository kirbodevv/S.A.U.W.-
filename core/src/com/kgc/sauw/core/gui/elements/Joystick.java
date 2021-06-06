package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.math.Maths;
import com.kgc.sauw.core.math.Vector2d;
import com.kgc.sauw.core.math.Vector2i;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.utils.Camera2D;

import static com.kgc.sauw.core.graphic.Graphic.BLOCK_SIZE;

public class Joystick extends InterfaceElement {
    private float D1;
    private float D2;
    private Texture a;
    private Texture h;
    private final Vector2d handle_pos;
    private boolean joystick_touched;
    private boolean isTouched;


    public Joystick(Texture a, Texture h) {
        setTextures(a, h);
        handle_pos = new Vector2d();
    }

    public void setDiameters(float area, float handle) {
        D1 = area;
        D2 = handle;
    }

    public void setTextures(Texture area, Texture handle) {
        a = area;
        h = handle;
    }

    @Override
    public void setSizeInBlocks(float w, float h) {
        super.setSizeInBlocks(w, h);
        setDiameters(w * BLOCK_SIZE, w / 3 * BLOCK_SIZE);
    }

    public double angleD() {
        return handle_pos.angleD();
    }

    public int angleI() {
        return handle_pos.angleI();
    }

    public Vector2d normD() {
        return handle_pos.getNorm();
    }

    public Vector2d normD(int length) {
        return handle_pos.getNorm(length);
    }

    public Vector2d normD(double length) {
        return handle_pos.getNorm(length);
    }

    public Vector2i normI() {
        Vector2d v = handle_pos.getNorm();
        return new Vector2i(v.x(), v.y());
    }

    public Vector2i normI(int length) {
        Vector2d v = handle_pos.getNorm(length);
        return new Vector2i(v.x(), v.y());
    }

    public int dist() {
        return Maths.distance(new Vector2d(), handle_pos);
    }

    public double normDist() {
        return Maths.distanceD(new Vector2d(), handle_pos) / (D1 / 2 - D2 / 2);
    }

    public int normDistI(int length) {
        return (int) Math.round(Maths.distanceD(new Vector2d(), handle_pos) / (D1 / 2 - D2 / 2) * length);
    }

    public double normDistD(int length) {
        return Maths.distanceD(new Vector2d(), handle_pos) / (D1 / 2 - D2 / 2) * length;
    }

    public double normDistD(double length) {
        return Maths.distanceD(new Vector2d(), handle_pos) / (D1 / 2 - D2 / 2) * length;
    }

    @Override
    public boolean isTouched() {
        return joystick_touched;
    }

    @Override
    public void tick(Camera2D cam) {
        setSize(D1, D1);
    }

    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        int x = 0, y = 0;
        if (Gdx.input.isTouched()) {
            x = (int) cam.touchX();
            y = (int) cam.touchY();

            if (!isTouched) {
                if (Maths.distance(this.x + D1 / 2f, this.y + D1 / 2f, x, y) <= D1 / 2f)
                    joystick_touched = true;

                isTouched = true;
            }
        }

        if (joystick_touched) {
            handle_pos.x = x - this.x - D1 / 2f;
            handle_pos.y = y - this.y - D1 / 2f;

            if (Maths.distance(0, 0, handle_pos.x(), handle_pos.y()) > D1 / 2 - D2 / 2) {
                handle_pos.norm(D1 + D2);
            }

            if (!Gdx.input.isTouched()) {
                joystick_touched = false;
                handle_pos.setZero();
            }
        }

        if (!Gdx.input.isTouched())
            isTouched = false;

        batch.draw(a, this.x, this.y, D1, D1);
        batch.draw(h, this.x + handle_pos.x() + D1 / 2f - D2 / 2f, this.y + handle_pos.y() + D1 / 2f - D2 / 2f, D2, D2);
    }
}
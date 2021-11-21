package com.kgc.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Camera2D {

    public OrthographicCamera CAMERA;
    public int SIZE, W, H;
    public float X, Y;

    public void update(SpriteBatch b) {
        CAMERA.update();
        b.setProjectionMatrix(CAMERA.combined);
    }

    private void configure(int size) {
        int w = Gdx.graphics.getWidth(),
                h = Gdx.graphics.getHeight();

        if (h < w) {
            CAMERA.setToOrtho(false, size, size * h / w);
            W = size;
            H = size * h / w;
        } else {
            CAMERA.setToOrtho(false, size * w / h, size);
            W = size * w / h;
            H = size;
        }

        CAMERA.translate(X, Y);
    }

    public Camera2D(int size) {
        CAMERA = new OrthographicCamera();
        configure(size);
        SIZE = size;
    }

    public Camera2D() {
        this(Math.max(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    public void lookAt(float x, float y, boolean smooth) {
        if (!smooth) {
            CAMERA.translate(-X + x, -Y + y);
            X = x;
            Y = y;
        } else {
            float camX = MathUtils.lerp(X, x, 0.06f);
            float camY = MathUtils.lerp(Y, y, 0.06f);
            lookAt(camX, camY, false);
            X = camX;
            Y = camY;
        }
    }

    public void resize(int size) {
        SIZE = size;
        configure(size);
    }

    public void resize() {
        configure(SIZE);
    }

    public float touchX() {
        return Gdx.input.getX() + X;
    }

    public float touchY() {
        return H - Gdx.input.getY() + Y;
    }
}

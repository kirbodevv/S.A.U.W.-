package com.kgc.sauw.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.kgc.sauw.math.Vector2d;

public class Camera2D {

    public OrthographicCamera CAMERA;
    public int SIZE, ANGLE, W, H;
    public float X, Y;


    public void setCurrentCameraZoom(float zoom) {
        resize((int) (Gdx.graphics.getWidth() * zoom));
    }

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
        CAMERA.rotate(ANGLE);
    }

    public Camera2D(int size) {
        CAMERA = new OrthographicCamera();
        configure(size);
        SIZE = size;
    }

    public Camera2D() {
        int size = Math.max(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        CAMERA = new OrthographicCamera();
        configure(size);
        SIZE = size;
    }

    public void rotate(int a) {
        CAMERA.rotate(a);
        ANGLE += a;
    }

    public void setAngle(int a) {
        CAMERA.rotate(-ANGLE + a);
        ANGLE = a;
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

    public void translatePosition(int x, int y) {
        CAMERA.translate(x, y);
        X += x;
        Y += y;
    }

    public void translatePosition(Vector2d v) {
        CAMERA.translate(v.x(), v.y());
        X += v.x();
        Y += v.y();
    }

    public void resize(int size) {
        SIZE = size;
        configure(size);
    }

    public void resize() {
        configure(SIZE);
    }

    public void translateScale(int lss) {
        resize(SIZE + lss);
    }

    public float touchX() {
        return Gdx.input.getX() + X;
    }

    public float touchY() {
        return H - Gdx.input.getY() + Y;
    }

    public float touchYI() {
        return Gdx.input.getY() + (H - Y) + H;
    }

    public float touchYI(int i) {
        return Gdx.input.getY(i) + (H - Y) + H;
    }

    public float touchX(int i) {
        return Gdx.input.getX(i) + X;
    }

    public float touchY(int i) {
        return H - Gdx.input.getY(i) + Y;
    }


}

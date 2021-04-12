package com.kgc.sauw.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.kgc.sauw.math.Vector2d;

public class Camera2D {

    public OrthographicCamera CAMERA;
    public int SIZE, X, Y, ANGLE, W, H;

    private float cameraScaleProgress = 1f;
    public float cameraZoom = 1f;
    public float currentCameraZoom = 1f;

    public void setCurrentCameraZoom(float zoom) {
        if (Gdx.graphics.getWidth() * zoom != W) resize((int) (Gdx.graphics.getWidth() * zoom));
        currentCameraZoom = zoom;
    }

    public void setCameraZoom(float zoom, float progress) {
        cameraZoom = zoom;
        cameraScaleProgress = progress;
    }

    public void update(SpriteBatch b) {
        setCurrentCameraZoom(MathUtils.lerp(currentCameraZoom, cameraZoom, cameraScaleProgress));
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

    public void lookAt(int x, int y, boolean smooth) {
        if (!smooth) {
            CAMERA.translate(-X + x, -Y + y);
            X = x;
            Y = y;
        } else {
            if (X != x && Y != y) {
                float camX = MathUtils.lerp(X, x, 0.06f);
                float camY = MathUtils.lerp(Y, y, 0.06f);
                CAMERA.position.x = camX;
                CAMERA.position.y = camY;
                X = (int) camX;
                Y = (int) camY;
            }
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

    public int touchX() {
        return Gdx.input.getX() + X;
    }

    public int touchY() {
        return H - Gdx.input.getY() + Y;
    }

    public int touchYI() {
        return Gdx.input.getY() + (H - Y) + H;
    }

    public int touchYI(int i) {
        return Gdx.input.getY(i) + (H - Y) + H;
    }

    public int touchX(int i) {
        return Gdx.input.getX(i) + X;
    }

    public int touchY(int i) {
        return H - Gdx.input.getY(i) + Y;
    }


}

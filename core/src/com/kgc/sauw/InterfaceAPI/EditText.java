package com.kgc.sauw.InterfaceAPI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.Camera2D;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kgc.sauw.Textures;

public class EditText {
    public String input = "";
    public int X, Y, w, h;
    private Texture backgroundTextutre;
    private BitmapFont BF;
    public boolean hided;
    private boolean isTouched;
    private Camera2D cam;
    public boolean isKeyboardOpen = false;
    private InputProcessor processor;
    private InputMultiplexer multiplexer;
    public EditText(int x, int y, int w, int h, Camera2D cam, InputMultiplexer multiplexer) {
        this.multiplexer = multiplexer;
        int width = Gdx.graphics.getWidth();
        this.backgroundTextutre = Textures.generateTexture(w / (width / 16), h / (width / 16), false);
        this.X = x;
        this.Y = y;
        this.cam = cam;

        setWidthAndHeight(new Vector2(w, h));

        BF = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        BF.setColor(Color.BLACK);
        BF.setScale(h / 2 / BF.getData().capHeight);
        processor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char c) {
                if (!hided) {
                    if (c == 0) {
                        return false;
                    } else if (c == '\b' && input.length() > 0) {
                        input = input.substring(0, input.length() - 1);
                    } else {
                        input += c;
                    }
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
            public boolean scrolled(int amount) {
                return false;
            }
        };
    }

    public void setWidthAndHeight(Vector2 WH) {
        this.w = (int) WH.x;
        this.h = (int) WH.y;
    }

    public void setTextColor(float r, float g, float b) {
        this.BF.setColor(r, g, b, 1);
    }

    public void clear() {
        input = "";
    }

    public void hide(boolean flag) {
        this.hided = flag;
        if(flag){
            multiplexer.removeProcessor(processor);
        } else {
            multiplexer.addProcessor(processor);
        }
    }

    public void update() {
        if (!hided) {
            if (Gdx.input.getX() > X && Gdx.input.getX() < X + w && cam.H - Gdx.input.getY() > Y && cam.H - Gdx.input.getY() < Y + h) {
                isTouched = true;
            } else {
                isTouched = false;
            }
            if (!Gdx.input.isTouched())
                isTouched = false;
        }
        if (isTouched) {
            Gdx.input.setOnscreenKeyboardVisible(true);
            isKeyboardOpen = true;
        } else if (Gdx.input.isTouched()) {
            Gdx.input.setOnscreenKeyboardVisible(false);
            isKeyboardOpen = false;
        }

    }

    public void render(SpriteBatch b) {
        b.draw(backgroundTextutre, X + cam.X, Y + cam.Y, w, h);
        BF.draw(b, input, X + cam.X + (h / 2), Y + cam.Y + (h / 4 * 3));
    }
}

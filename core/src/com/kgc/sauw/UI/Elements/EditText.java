package com.kgc.sauw.ui.elements;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.ui.InterfaceElement;
import com.kgc.sauw.utils.Camera2D;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.resource.Textures;

import static com.kgc.sauw.graphic.Graphic.BLOCK_SIZE;

public class EditText extends InterfaceElement {
    public String input = "";
    private Texture backgroundTextutre;

    private static final BitmapFont BF;
    private static final float capHeight;

    static {
        BF = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        capHeight = BF.getCapHeight();
    }

    Color textColor = new Color(64f / 255, 137f / 255, 154f / 255, 1);

    public boolean isKeyboardOpen = false;
    private InputProcessor processor;
    private InputMultiplexer multiplexer;

    private boolean possibleToEnterText = false;

    public EditText(float x, float y, float w, float h, InputMultiplexer multiplexer) {
        this.multiplexer = multiplexer;

        setPosition(x, y);
        setSize(w, h);

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
                if (!hided && possibleToEnterText) {
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
        multiplexer.addProcessor(processor);
    }

    public void setTextColor(float r, float g, float b) {
        this.BF.setColor(r, g, b, 1);
    }

    public void clear() {
        input = "";
    }

    @Override
    public void update(Camera2D cam) {
        super.update(cam);
        if (Gdx.input.isTouched()) {
            Gdx.input.setOnscreenKeyboardVisible(false);
            possibleToEnterText = false;
            isKeyboardOpen = false;
        }
    }

    @Override
    public void hide(boolean b) {
        super.hide(b);
        if (b) possibleToEnterText = false;
    }

    @Override
    public void onClick(boolean onButton) {
        super.onClick(onButton);
        Gdx.input.setOnscreenKeyboardVisible(true);
        possibleToEnterText = true;
        isKeyboardOpen = true;
    }

    @Override
    public void render(SpriteBatch b, Camera2D cam) {
        setTextScale();
        BF.setColor(textColor);
        b.draw(backgroundTextutre, X + cam.X, Y + cam.Y, width, height);
        BF.draw(b, input, X + cam.X + (height / 2), Y + cam.Y + (height / 4 * 3));
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        if(backgroundTextutre != null) backgroundTextutre.dispose();
        this.backgroundTextutre = Textures.generateTexture(w / BLOCK_SIZE, h / BLOCK_SIZE, false);
    }

    public void setTextScale() {
        BF.setScale(height / 2f / capHeight);
    }

    public void setColor(int r, int g, int b) {
        this.textColor.set(r / 255f, g / 255f, b / 255f, 1f);
    }

}

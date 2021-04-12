package com.kgc.sauw.UI.Elements;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.UI.InterfaceElement;
import com.kgc.sauw.utils.Camera2D;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kgc.sauw.resource.Textures;

import static com.kgc.sauw.graphic.Graphic.BLOCK_SIZE;

public class EditText extends InterfaceElement {
    public String input = "";
    private Texture backgroundTextutre;
    private BitmapFont BF;
    public boolean isKeyboardOpen = false;
    private InputProcessor processor;
    private InputMultiplexer multiplexer;

    private boolean possibleToEnterText = false;

    public EditText(int x, int y, int w, int h, InputMultiplexer multiplexer) {
        this.multiplexer = multiplexer;
        this.backgroundTextutre = Textures.generateTexture(w / BLOCK_SIZE, h / BLOCK_SIZE, false);

        setPosition(x, y);
        setSize(w, h);

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
        if(b) possibleToEnterText = false;
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
        b.draw(backgroundTextutre, X + cam.X, Y + cam.Y, width, height);
        BF.draw(b, input, X + cam.X + (height / 2), Y + cam.Y + (height / 4 * 3));
    }
}

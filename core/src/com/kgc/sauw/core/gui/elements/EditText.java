package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.input.InputAdapter;
import com.kgc.sauw.core.input.TextInputProcessor;
import com.kgc.sauw.core.utils.Camera2D;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.game.skins.Skins;

public class EditText extends InterfaceElement {
    public String input = "";
    private ElementSkin backgroundTexture;

    private static final BitmapFont BF;
    private static final float capHeight;

    static {
        BF = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        capHeight = BF.getCapHeight();
    }

    Color textColor = new Color(64f / 255, 137f / 255, 154f / 255, 1);

    public boolean isKeyboardOpen = false;
    private boolean possibleToEnterText = false;

    public EditText() {
        TextInputProcessor.addAdapter(new InputAdapter() {
            @Override
            public void onCharEnter(char char_) {
                if (possibleToEnterText)
                    input += char_;
            }

            @Override
            public void onBackspaceClick() {
                if (input.length() > 0 && possibleToEnterText) input = input.substring(0, input.length() - 1);
            }
        });
        backgroundTexture = Skins.round_down_1;
    }

    public void setTextColor(float r, float g, float b) {
        BF.setColor(r, g, b, 1);
    }

    public void clear() {
        input = "";
    }

    @Override
    public void tick(Camera2D cam) {
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
    public void renderTick(SpriteBatch b, Camera2D cam) {
        setTextScale();
        BF.setColor(textColor);
        backgroundTexture.draw(x + cam.X, y + cam.Y, width, height);
        BF.draw(b, input, x + cam.X + (height / 2), y + cam.Y + (height / 4 * 3));
    }

    public void setTextScale() {
        BF.getData().setScale(height / 2f / capHeight);
    }

    public void setColor(int r, int g, int b) {
        this.textColor.set(r / 255f, g / 255f, b / 255f, 1f);
    }

}

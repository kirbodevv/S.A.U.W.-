package com.kgc.bluesgui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.input.InputAdapter;
import com.kgc.sauw.core.input.TextInputProcessor;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.utils.Camera2D;

public class EditText extends AbstractTextView {
    private ElementSkin backgroundTexture;

    public boolean isKeyboardOpen = false;
    private boolean possibleToEnterText = false;

    public EditText() {
        TextInputProcessor.addAdapter(new InputAdapter() {
            @Override
            public void onCharEnter(char char_) {
                if (possibleToEnterText)
                    text += char_;
            }

            @Override
            public void onBackspaceClick() {
                if (text.length() > 0 && possibleToEnterText) text = text.substring(0, text.length() - 1);
            }
        });
        backgroundTexture = Skins.round_down_1;
    }

    public void clear() {
        text = "";
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
    public void onClick(boolean onElement) {
        Gdx.input.setOnscreenKeyboardVisible(true);
        possibleToEnterText = true;
        isKeyboardOpen = true;
    }

    @Override
    public void preRender(SpriteBatch batch, Camera2D cam) {
        backgroundTexture.draw(x + cam.X, y + cam.Y, width, height);
    }

    @Override
    public void dispose() {

    }
}

package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.input.InputAdapter;
import com.kgc.sauw.core.input.TextInputProcessor;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.utils.Camera2D;

import static com.kgc.sauw.core.graphic.Graphic.GLYPH_LAYOUT;

public class EditText extends AbstractTextView {
    private ElementSkin backgroundTexture;

    public boolean isKeyboardOpen = false;
    private boolean possibleToEnterText = false;
    private String input = "";

    public String getInput() {
        return input;
    }

    public EditText() {
        setTextAlign(Align.left);
        setScalable(false);
        TextInputProcessor.addAdapter(new InputAdapter() {
            @Override
            public void onCharEnter(char char_) {
                if (possibleToEnterText) {
                    input += char_;
                    updateText(input);
                }
            }

            @Override
            public void onBackspaceClick() {
                if (input.length() > 0 && possibleToEnterText) input = input.substring(0, input.length() - 1);
                updateText(input);
            }

            private void updateText(String input) {
                setText(input);
                while (GLYPH_LAYOUT.width > width - outline * 2) setText(text.substring(1));
            }
        });
        backgroundTexture = Skins.round_down_1;
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

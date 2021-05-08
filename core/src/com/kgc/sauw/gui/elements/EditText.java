package com.kgc.sauw.gui.elements;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.gui.InterfaceElement;
import com.kgc.sauw.input.InputAdapter;
import com.kgc.sauw.input.TextInputProcessor;
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
        b.draw(backgroundTextutre, X + cam.X, Y + cam.Y, width, height);
        BF.draw(b, input, X + cam.X + (height / 2), Y + cam.Y + (height / 4 * 3));
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        if (backgroundTextutre != null) backgroundTextutre.dispose();
        this.backgroundTextutre = Textures.generateTexture(w / BLOCK_SIZE, h / BLOCK_SIZE, false);
    }

    public void setTextScale() {
        BF.setScale(height / 2f / capHeight);
    }

    public void setColor(int r, int g, int b) {
        this.textColor.set(r / 255f, g / 255f, b / 255f, 1f);
    }

}

package com.kgc.sauw.input;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

public class TextInputProcessor implements InputProcessor {

    public static void addAdapter(InputAdapter adapter) {
        adapters.add(adapter);
    }

    private static final ArrayList<InputAdapter> adapters = new ArrayList<>();

    private static void onCharEnter(char char_) {
        for (InputAdapter adapter : adapters) adapter.onCharEnter(char_);
    }

    private static void onBackspaceClick() {
        for (InputAdapter adapter : adapters) adapter.onBackspaceClick();
    }

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
        if (c == 0) {
            return false;
        } else if (c == '\b') {
            onBackspaceClick();
        } else {
            onCharEnter(c);
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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}

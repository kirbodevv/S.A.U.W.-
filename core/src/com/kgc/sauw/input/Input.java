package com.kgc.sauw.input;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.kgc.sauw.ui.interfaces.Interfaces;
import com.kgc.sauw.game.SAUW;

import static com.kgc.sauw.ui.interfaces.Interfaces.*;
import static com.kgc.sauw.ui.interfaces.Interfaces.CRAFTING_INTERFACE;
import static com.kgc.sauw.config.Settings.SETTINGS;


public final class Input {
    public static final InputMultiplexer INPUT_MULTIPLEXER;
    private static final GameInputProcessor GAME_INPUT_PROCESSOR;

    static {
        INPUT_MULTIPLEXER = new InputMultiplexer();
        GAME_INPUT_PROCESSOR = new GameInputProcessor();
        INPUT_MULTIPLEXER.addProcessor(GAME_INPUT_PROCESSOR);
    }

    public static class GameInputProcessor implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            if (SAUW.isGameRunning) {
                if (!isAnyInterfaceOpen()) {
                    if (keycode == Keys.ESCAPE) {
                        if (!PAUSE_INTERFACE.isOpen) PAUSE_INTERFACE.open();
                    }
                    if (keycode == Keys.TAB) {
                        if (!INVENTORY_INTERFACE.isOpen) INVENTORY_INTERFACE.open();
                    }
                    if (keycode == Keys.F1) {
                        if (SETTINGS.useConsole) if (!CONSOLE_INTERFACE.isOpen) CONSOLE_INTERFACE.open();
                    }
                    if (keycode == Keys.C) {
                        if (!CRAFTING_INTERFACE.isOpen) CRAFTING_INTERFACE.open();
                    }
                } else {
                    if (keycode == Keys.ESCAPE) {
                        Interfaces.closeInterface();
                    }
                    if (keycode == Keys.TAB) {
                        if (INVENTORY_INTERFACE.isOpen) INVENTORY_INTERFACE.close();
                    }
                    if (keycode == Keys.F1) {
                        if (SETTINGS.useConsole)
                            if (CONSOLE_INTERFACE.isOpen) CONSOLE_INTERFACE.close();
                    }
                    if (keycode == Keys.C) {
                        if (CRAFTING_INTERFACE.isOpen) CRAFTING_INTERFACE.close();
                    }
                }
            }
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
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
    }
}

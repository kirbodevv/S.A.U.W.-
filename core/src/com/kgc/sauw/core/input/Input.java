package com.kgc.sauw.core.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controllers;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.game.Game;
import com.kgc.sauw.game.gui.Interfaces;

import static com.kgc.sauw.game.gui.Interfaces.*;


public final class Input {
    public static final InputMultiplexer INPUT_MULTIPLEXER;
    private static final GameInputProcessor GAME_INPUT_PROCESSOR;
    private static PlayerController PLAYER_CONTROLLER;
    private static DebugController DEBUG_CONTROLLER;
    private static final TextInputProcessor TEXT_INPUT_PROCESSOR;
    private static final ControllerListener CONTROLLER_LISTENER;

    static {
        INPUT_MULTIPLEXER = new InputMultiplexer();
        GAME_INPUT_PROCESSOR = new GameInputProcessor();
        TEXT_INPUT_PROCESSOR = new TextInputProcessor();
        CONTROLLER_LISTENER = new ControllerListener();

        INPUT_MULTIPLEXER.addProcessor(GAME_INPUT_PROCESSOR);

        INPUT_MULTIPLEXER.addProcessor(TEXT_INPUT_PROCESSOR);

        Controllers.addListener(CONTROLLER_LISTENER);
    }

    public static void init() {
        PLAYER_CONTROLLER = new PlayerController();
        DEBUG_CONTROLLER = new DebugController();
        INPUT_MULTIPLEXER.addProcessor(PLAYER_CONTROLLER);
        INPUT_MULTIPLEXER.addProcessor(DEBUG_CONTROLLER);
    }

    public static class GameInputProcessor implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            if (Game.isGameRunning) {
                if (!isAnyInterfaceOpen()) {
                    if (keycode == Keys.ESCAPE) {
                        if (!PAUSE_INTERFACE.isOpen) PAUSE_INTERFACE.open();
                    }
                    if (keycode == Keys.TAB) {
                        if (!INVENTORY_INTERFACE.isOpen) INVENTORY_INTERFACE.open();
                    }
                    if (keycode == Keys.F1) {
                        if (Settings.useConsole) if (!CONSOLE_INTERFACE.isOpen) CONSOLE_INTERFACE.open();
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
                        if (Settings.useConsole)
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
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }

    }
}

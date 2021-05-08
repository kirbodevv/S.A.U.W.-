package com.kgc.sauw.input;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.kgc.sauw.gui.interfaces.Interfaces;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.gui.interfaces.Interfaces.GAME_INTERFACE;

public class PlayerController implements InputProcessor {
    public static void update() {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            PLAYER.setVelocityX((float) GAME_INTERFACE.j.normD(3).x);
            PLAYER.setVelocityY((float) GAME_INTERFACE.j.normD(3).y);
            if (GAME_INTERFACE.j.isTouched()) {
                if (GAME_INTERFACE.j.angleI() < 315 && GAME_INTERFACE.j.angleI() > 225) {
                    PLAYER.rotation = 0;
                } else if (GAME_INTERFACE.j.angleI() < 225 && GAME_INTERFACE.j.angleI() > 135) {
                    PLAYER.rotation = 1;
                } else if (GAME_INTERFACE.j.angleI() > 45 && GAME_INTERFACE.j.angleI() < 135) {
                    PLAYER.rotation = 2;
                } else if (GAME_INTERFACE.j.angleI() < 45 || GAME_INTERFACE.j.angleI() > 315) {
                    PLAYER.rotation = 3;
                }
            }
        }
        if (!Interfaces.isAnyInterfaceOpen()) {
            if (Gdx.input.isKeyPressed(Keys.W)) {
                PLAYER.setVelocityY(1);
                PLAYER.rotation = 0;
            }
            if (Gdx.input.isKeyPressed(Keys.S)) {
                PLAYER.setVelocityY(-1);
                PLAYER.rotation = 2;
            }
            if (Gdx.input.isKeyPressed(Keys.A)) {
                PLAYER.setVelocityX(-1);
                PLAYER.rotation = 3;
            }
            if (Gdx.input.isKeyPressed(Keys.D)) {
                PLAYER.setVelocityX(1);
                PLAYER.rotation = 1;
            }
        }
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

package com.kgc.sauw.UI;

import com.badlogic.gdx.*;
import com.kgc.sauw.UI.Elements.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.utils.Version;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.map.World;
import com.badlogic.gdx.graphics.Texture;

import static com.kgc.sauw.Input.INPUT_MULTIPLEXER;
import static com.kgc.sauw.UI.Interfaces.Interfaces.*;
import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.graphic.Graphic.*;

public class GameInterface implements InputProcessor {
    Inventory inv;
    public Button interactionButton;
    public Button dropButton;
    public Button attackButton;
    public Button consoleOpenButton;
    public Button pauseButton;
    public Joystick j;
    Health health;
    BitmapFont debug;
    public boolean hided = false;

    private boolean isTouched;
    public Button craftingButton;
    public Notification notification;
    private boolean notifAnimation = false;
    private int notifW;
    private float notifAl = 1f;
    public BitmapFont Log = new BitmapFont(Gdx.files.internal("ttf.fnt"));

    public String logText = "Lol";
    public int R = 0, G = 0, B = 0;

    public void consolePrint(String txt) {
        this.logText = txt;
    }

    public void setConsoleTextColor(int r, int g, int b) {
        R = r;
        G = g;
        B = b;
        SETTINGS.consoleTextColorRed = r;
        SETTINGS.consoleTextColorGreen = g;
        SETTINGS.consoleTextColorBlue = b;
        SETTINGS.saveSettings();
    }

    public GameInterface() {
        setConsoleTextColor(SETTINGS.consoleTextColorRed, SETTINGS.consoleTextColorGreen, SETTINGS.consoleTextColorBlue);
        notification = new Notification(TEXTURES.generateBackground(4, SCREEN_WIDTH / 12.0f / (SCREEN_WIDTH / 16.0f)));
        debug = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        debug.setScale(SCREEN_WIDTH / 64 / debug.getCapHeight());
        attackButton = new Button("ATTACK_BUTTON", (int) (12 * (SCREEN_WIDTH / 16)), (int) (3 * (SCREEN_WIDTH / 16)), (int) (SCREEN_WIDTH / 32 * 3), (int) (SCREEN_WIDTH / 32 * 3), TEXTURES.button_0, TEXTURES.button_1);
        dropButton = new Button("DROP_BUTTON", (int) (13 * (SCREEN_WIDTH / 16)), (int) (3 * (SCREEN_WIDTH / 32)), (int) (SCREEN_WIDTH / 32 * 3), (int) (SCREEN_WIDTH / 32 * 3), TEXTURES.button_0, TEXTURES.button_1);
        interactionButton = new Button("INTERACTION_BUTTON", (int) (14 * (SCREEN_WIDTH / 16)), (int) (3 * (SCREEN_WIDTH / 16))/*(int)(13 * (WIDTH / 16)), (int)(3 * (WIDTH / 32))*/, (int) (SCREEN_WIDTH / 32 * 3), (int) (SCREEN_WIDTH / 32 * 3), TEXTURES.button_0, TEXTURES.button_1);
        consoleOpenButton = new Button("CONSOLE_OPEN_BUTTON", (int) (SCREEN_WIDTH - (SCREEN_WIDTH / 16)), (int) (SCREEN_HEIGHT - (SCREEN_WIDTH / 16)), (int) SCREEN_WIDTH / 16, (int) SCREEN_WIDTH / 16, TEXTURES.console_button_0, TEXTURES.console_button_1);
        craftingButton = new Button("CRAFTING_MENU_OPEN_BUTTON", (int) (SCREEN_WIDTH / 16 * 4 + SCREEN_WIDTH / 16 * 9), 0, (int) SCREEN_WIDTH / 16, (int) SCREEN_WIDTH / 16, TEXTURES.crafting_button_0, TEXTURES.crafting_button_1);
        pauseButton = new Button("PAUSE_BUTTON", 0, (int) (SCREEN_HEIGHT - SCREEN_WIDTH / 16), (int) SCREEN_WIDTH / 16, (int) SCREEN_WIDTH / 16);
        j = new Joystick(TEXTURES.j_0, TEXTURES.j_1, (int) (SCREEN_WIDTH / 32 * 3), (int) (SCREEN_WIDTH / 32 * 3), BATCH, (int) (SCREEN_WIDTH / 16 * 3), INTERFACE_CAMERA);
        j.setDiameters((int) SCREEN_WIDTH / 16 * 3, (int) SCREEN_WIDTH / 32 * 2);
        inv = new Inventory(TEXTURES.inventory, TEXTURES.selected_slot, (int) ((SCREEN_WIDTH / 16) * 4), 0);
        health = new Health(TEXTURES.health_0, TEXTURES.health_1);
        notifW = (int) SCREEN_WIDTH / 16 * 4;
        INPUT_MULTIPLEXER.addProcessor(this);

        craftingButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                CRAFTING_INTERFACE.open();
            }
        });
        consoleOpenButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                CONSOLE_INTERFACE.open();
            }
        });
        pauseButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                PAUSE_INTERFACE.open();
            }
        });
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched(boolean t) {
        isTouched = t;
    }

    public void showNotification(String title, String txt, Texture t, int time) {
        notification.show((int) SCREEN_WIDTH, (int) (SCREEN_HEIGHT - SCREEN_WIDTH / 12), (int) (SCREEN_WIDTH / 16 * 4), (int) (SCREEN_WIDTH / 12), title, txt, t, time);
        notifAnimation = true;
        notifAl = 1;
    }
    public void setSize(){

    }
    public void update(Player pl) {
        if (isAnyInterfaceOpen()) {
            interactionButton.hide(true);
            dropButton.hide(true);
            attackButton.hide(true);
            inv.hide(true);
            j.hide(true);
            health.hide(true);
            consoleOpenButton.hide(true);
            craftingButton.hide(true);
            pauseButton.hide(true);
        } else {
            interactionButton.hide(false);
            dropButton.hide(false);
            attackButton.hide(false);
            inv.hide(false);
            j.hide(false);
            health.hide(false);
            consoleOpenButton.hide(!SETTINGS.useConsole);
            craftingButton.hide(false);
            pauseButton.hide(false);
        }
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            j.hide(true);
            consoleOpenButton.hide(true);
            pauseButton.hide(true);
            craftingButton.hide(true);
            interactionButton.hide(true);
            dropButton.hide(true);
            attackButton.hide(true);
        }
        isTouched = false;
        inv.update();
        interactionButton.update(INTERFACE_CAMERA);
        dropButton.update(INTERFACE_CAMERA);
        attackButton.update(INTERFACE_CAMERA);
        consoleOpenButton.update(INTERFACE_CAMERA);
        craftingButton.update(INTERFACE_CAMERA);
        pauseButton.update(INTERFACE_CAMERA);
        if (consoleOpenButton.isTouched() || dropButton.isTouched() || attackButton.isTouched() || interactionButton.isTouched() || j.isTouched() || inv.isTouched()) {
            isTouched = true;
        }
        CONSOLE_INTERFACE.update(pl, INTERFACE_CAMERA);
        CRAFTING_INTERFACE.update(pl, INTERFACE_CAMERA);
        DEAD_INTERFACE.update(pl, INTERFACE_CAMERA);
        PAUSE_INTERFACE.update(pl, INTERFACE_CAMERA);

        if (notifAnimation) {
            if (notification.timer < 4) {
                notification.X -= notifW / 15;
                if (notification.X <= SCREEN_WIDTH - notifW) {
                    notification.X = (int) SCREEN_WIDTH - notifW;
                    notifAnimation = false;
                }
            } else {
                notifAl -= 0.01f;
            }
        }
        notification.update(INTERFACE_CAMERA);
        if (notification.timer >= 4) {
            notifAnimation = true;
        }
    }

    public void render(World World,Player pl, boolean debug) {
        INTERFACE_CAMERA.update(BATCH);
        BATCH.setColor(1f, 1f, 1f, 0.7f);
        interactionButton.render(BATCH, INTERFACE_CAMERA);
        dropButton.render(BATCH, INTERFACE_CAMERA);
        attackButton.render(BATCH, INTERFACE_CAMERA);
        consoleOpenButton.render(BATCH, INTERFACE_CAMERA);
        craftingButton.render(BATCH, INTERFACE_CAMERA);
        pauseButton.render(BATCH, INTERFACE_CAMERA);
        BATCH.setColor(1f, 1f, 1f, 0.5f);
        j.render(INTERFACE_CAMERA);
        BATCH.setColor(1f, 1f, 1f, 1f);
        health.render(pl.health, pl.maxHealth);
        CONSOLE_INTERFACE.render(pl, INTERFACE_CAMERA);
        CRAFTING_INTERFACE.render(pl, INTERFACE_CAMERA);
        DEAD_INTERFACE.render(pl, INTERFACE_CAMERA);
        PAUSE_INTERFACE.render(pl, INTERFACE_CAMERA);
        if (!isAnyInterfaceOpen() && debug)
            drawDebugString(World);
        inv.render(pl);
        BATCH.setColor(1, 1, 1, notifAl);
        notification.render(BATCH, INTERFACE_CAMERA);
        BATCH.setColor(1, 1, 1, 1);
    }

    public void drawDebugString(World World) {
        String Main = " Version : " + Version.VERSION +
                "\n FPS : " + Gdx.graphics.getFramesPerSecond() +
                "\n " + (Gdx.app.getJavaHeap() + Gdx.app.getNativeHeap()) / 1024 / 1024 + " Mb" +
                "\n Camera X : " + GAME_CAMERA.X +
                "\n Camera Y : " + GAME_CAMERA.Y +
                "\n UI_ELEMENTS Count : " + Elements.UI_ELEMENTS.size();
        String Player = "\n Hunger:" + PLAYER.hunger + "/20" +
                "\n X : " + PLAYER.currentTileX +
                "\n Y : " + PLAYER.currentTileY +
                "\n velX : " + PLAYER.velX +
                "\n velY : " + PLAYER.velY;
        this.debug.setColor(0f, 0f, 0f, 1f);
        this.debug.drawMultiLine(BATCH, Main, INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - SCREEN_WIDTH / 16 + INTERFACE_CAMERA.Y);
        this.debug.setColor(0.25f, 0.25f, 1f, 1f);
        this.debug.drawMultiLine(BATCH, "\n Player", INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - SCREEN_WIDTH / 16 + INTERFACE_CAMERA.Y - this.debug.getMultiLineBounds(Main).height);
        this.debug.setColor(0f, 0f, 0f, 1f);
        this.debug.drawMultiLine(BATCH, Player, INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - SCREEN_WIDTH / 16 + INTERFACE_CAMERA.Y - this.debug.getMultiLineBounds(Main + "\n Player").height);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!isAnyInterfaceOpen()) {
            if (keycode == Input.Keys.ESCAPE) {
                if (!PAUSE_INTERFACE.isOpen) PAUSE_INTERFACE.open();
            }
            if (keycode == Input.Keys.TAB) {
                if (!INVENTORY_INTERFACE.isOpen) INVENTORY_INTERFACE.open();
            }
            if (keycode == Input.Keys.F1) {
                if (SETTINGS.useConsole) if (!CONSOLE_INTERFACE.isOpen) CONSOLE_INTERFACE.open();
            }
            if (keycode == Input.Keys.C) {
                if (!CRAFTING_INTERFACE.isOpen) CRAFTING_INTERFACE.open();
            }
        } else {
            if (keycode == Input.Keys.ESCAPE) {
                if (PAUSE_INTERFACE.isOpen) PAUSE_INTERFACE.close();
            }
            if (keycode == Input.Keys.TAB) {
                if (INVENTORY_INTERFACE.isOpen) INVENTORY_INTERFACE.close();
            }
            if (keycode == Input.Keys.F1) {
                if (SETTINGS.useConsole)
                    if (CONSOLE_INTERFACE.isOpen) CONSOLE_INTERFACE.close();
            }
            if (keycode == Input.Keys.C) {
                if (CRAFTING_INTERFACE.isOpen) CRAFTING_INTERFACE.close();
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

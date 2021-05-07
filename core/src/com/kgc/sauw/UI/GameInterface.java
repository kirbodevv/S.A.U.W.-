package com.kgc.sauw.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.ui.elements.*;
import com.kgc.sauw.ui.interfaces.Interfaces;
import com.kgc.sauw.utils.Version;

import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.ui.interfaces.Interfaces.*;

public class GameInterface {
    public Hotbar hotbar;
    public Health health;

    public Button interactionButton;
    public Button dropButton;
    public Button attackButton;
    public Button consoleOpenButton;
    public Button pauseButton;
    public Button craftingButton;
    public Button inventoryOpenButton;
    public Joystick j;

    private final BitmapFont debug;
    private boolean isTouched;

    public BitmapFont Log = new BitmapFont(Gdx.files.internal("ttf.fnt"));

    public String logText = "Lol";
    public int R = 0, G = 0, B = 0;

    private final Layout mainLayout;
    private final Layout bottomLayout;
    private final Layout topLayout;
    private final Layout midLayout;

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
        debug = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        debug.setScale(SCREEN_WIDTH / 64 / debug.getCapHeight());

        j = new Joystick(TEXTURES.j_0, TEXTURES.j_1);
        j.setSizeInBlocks(3f, 3f);
        j.setTranslationX(0.5f);

        hotbar = new Hotbar();
        hotbar.setTranslationY(0.25f);

        health = new Health(TEXTURES.health_0, TEXTURES.health_1);

        attackButton = new Button("ATTACK_BUTTON", 0, 0, 0, 0, TEXTURES.button_0, TEXTURES.button_1);
        attackButton.setSizeInBlocks(1.5f, 1.5f);

        dropButton = new Button("DROP_BUTTON", 0, 0, 0, 0, TEXTURES.button_0, TEXTURES.button_1);
        dropButton.setSizeInBlocks(1.5f, 1.5f);

        interactionButton = new Button("INTERACTION_BUTTON", 0, 0, 0, 0, TEXTURES.button_0, TEXTURES.button_1);
        interactionButton.setSizeInBlocks(1.5f, 1.5f);

        Layout buttonsLayout = new Layout(Layout.Orientation.HORIZONTAL);
        buttonsLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        buttonsLayout.setGravity(Layout.Gravity.LEFT);
        buttonsLayout.addElements(attackButton, dropButton, interactionButton);

        consoleOpenButton = new Button("CONSOLE_OPEN_BUTTON", 0, 0, 0, 0, TEXTURES.console_button_0, TEXTURES.console_button_1);
        consoleOpenButton.setSizeInBlocks(1f, 1f);

        craftingButton = new Button("CRAFTING_MENU_OPEN_BUTTON", 0, 0, 0, 0, TEXTURES.crafting_button_0, TEXTURES.crafting_button_1);
        craftingButton.setSizeInBlocks(1f, 1f);

        pauseButton = new Button("PAUSE_BUTTON", 0, 0, 0, 0);
        pauseButton.setSizeInBlocks(1f, 1f);

        inventoryOpenButton = new Button("INVENTORY_OPEN_BUTTON", 0, 0, 0, 0, TEXTURES.extraButton_0, TEXTURES.extraButton_1);
        inventoryOpenButton.setSizeInBlocks(1f, 1f);

        mainLayout = new Layout(Layout.Orientation.VERTICAL);
        mainLayout.setGravity(Layout.Gravity.TOP);
        mainLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        mainLayout.setSizeInBlocks(16f, SCREEN_HEIGHT / BLOCK_SIZE);

        topLayout = new Layout(Layout.Orientation.HORIZONTAL);
        topLayout.setGravity(Layout.Gravity.LEFT);
        topLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.FIXED_SIZE);
        topLayout.setSizeInBlocks(0, 1f);

        midLayout = new Layout(Layout.Orientation.HORIZONTAL);
        midLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        midLayout.setSizeInBlocks(16, SCREEN_HEIGHT / BLOCK_SIZE - 2f);

        bottomLayout = new Layout(Layout.Orientation.HORIZONTAL);
        bottomLayout.setGravity(Layout.Gravity.LEFT);
        bottomLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);

        topLayout.addElements(pauseButton, consoleOpenButton);
        midLayout.addElements(j);
        bottomLayout.addElements(hotbar, inventoryOpenButton, craftingButton);

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
        inventoryOpenButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                INVENTORY_INTERFACE.open();
            }
        });

        mainLayout.addElements(topLayout, midLayout, bottomLayout);
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void resize() {
        mainLayout.setSizeInBlocks(16, SCREEN_HEIGHT / BLOCK_SIZE);
        midLayout.setSizeInBlocks(16, SCREEN_HEIGHT / BLOCK_SIZE - 2f);
        mainLayout.resize();
    }

    public void update() {
        mainLayout.hide(isAnyInterfaceOpen());
        mainLayout.update(INTERFACE_CAMERA);
        isTouched = consoleOpenButton.isTouched() || dropButton.isTouched() || attackButton.isTouched() || interactionButton.isTouched() || j.isTouched() || hotbar.isTouched();
        Interfaces.updateInterfaces();
    }

    public void render(boolean debug) {
        INTERFACE_CAMERA.update(BATCH);
        BATCH.setColor(1f, 1f, 1f, 0.7f);
        mainLayout.render(BATCH, INTERFACE_CAMERA);
        BATCH.setColor(1f, 1f, 1f, 1f);
        renderInterfaces();
        if (!isAnyInterfaceOpen() && debug)
            drawDebugString();
    }

    public void drawDebugString() {
        String Main = " Version : " + Version.VERSION +
                "\n FPS : " + Gdx.graphics.getFramesPerSecond() +
                "\n " + (Gdx.app.getJavaHeap() + Gdx.app.getNativeHeap()) / 1024 / 1024 + " Mb" +
                "\n Camera X : " + GAME_CAMERA.X +
                "\n Camera Y : " + GAME_CAMERA.Y +
                "\n UI_ELEMENTS Count : " + Elements.UI_ELEMENTS.size();
        String Player = "\n Hunger:" + PLAYER.hunger + "/20" +
                "\n X : " + PLAYER.getCurrentTileX() +
                "\n Y : " + PLAYER.getCurrentTileY();
        this.debug.setColor(0f, 0f, 0f, 1f);
        this.debug.drawMultiLine(BATCH, Main, INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - SCREEN_WIDTH / 16 + INTERFACE_CAMERA.Y);
        this.debug.setColor(0.25f, 0.25f, 1f, 1f);
        this.debug.drawMultiLine(BATCH, "\n Player", INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - SCREEN_WIDTH / 16 + INTERFACE_CAMERA.Y - this.debug.getMultiLineBounds(Main).height);
        this.debug.setColor(0f, 0f, 0f, 1f);
        this.debug.drawMultiLine(BATCH, Player, INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - SCREEN_WIDTH / 16 + INTERFACE_CAMERA.Y - this.debug.getMultiLineBounds(Main + "\n Player").height);
    }
}
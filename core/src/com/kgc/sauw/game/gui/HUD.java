package com.kgc.sauw.game.gui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.Version;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.gui.elements.*;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.game.skins.Skins;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.core.graphic.Graphic.*;
import static com.kgc.sauw.game.gui.Interfaces.*;

public class HUD {
    public Hotbar hotbar;

    public Button interactionButton;
    public Button dropButton;
    public Button attackButton;
    public Button consoleOpenButton;
    public Button pauseButton;
    public Button craftingButton;
    public Button inventoryOpenButton;
    public Joystick joystick;

    private final BitmapFont debug;
    private boolean isTouched;

    public BitmapFont log = new BitmapFont(Gdx.files.internal("ttf.fnt"));

    public String logText = "Lol";
    public int r = 0, g = 0, b = 0;

    private final Layout mainLayout;
    private final Layout separatorLayout0;
    private final Text healthText;
    private final Text hungerText;
    private final Text thirstText;
    private final Text timeText;
    private final Layout midLayout;

    public void consolePrint(String txt) {
        this.logText = txt;
    }

    public void setConsoleTextColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        Settings.consoleTextColorRed = r;
        Settings.consoleTextColorGreen = g;
        Settings.consoleTextColorBlue = b;
        Settings.saveSettings();
    }

    public HUD() {
        setConsoleTextColor(Settings.consoleTextColorRed, Settings.consoleTextColorGreen, Settings.consoleTextColorBlue);
        debug = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        debug.getData().setScale(SCREEN_WIDTH / 64 / debug.getCapHeight());

        joystick = new Joystick(Resource.getTexture("Interface/Joystick_0.png"), Resource.getTexture("Interface/Joystick_1.png"));
        joystick.setSizeInBlocks(3f, 3f);
        joystick.setTranslationX(0.5f);

        hotbar = new Hotbar();
        hotbar.setTranslationY(0.25f);

        Layout headerBar = new Layout(Layout.Orientation.HORIZONTAL);
        headerBar.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        headerBar.setSizeInBlocks(16f, 0.5f);
        headerBar.setGravity(Layout.Gravity.LEFT);
        headerBar.setStandardBackground(true);

        healthText = new Text();
        healthText.setSizeInBlocks(1.25f, 0.5f);
        healthText.setBackground(null);
        healthText.setScalable(false);

        hungerText = new Text();
        hungerText.setSizeInBlocks(1.25f, 0.5f);
        hungerText.setBackground(null);
        hungerText.setScalable(false);

        thirstText = new Text();
        thirstText.setSizeInBlocks(1.25f, 0.5f);
        thirstText.setBackground(null);
        thirstText.setScalable(false);

        Image healthImage = new Image();
        healthImage.setSizeInBlocks(0.4f, 0.4f);
        healthImage.setImg(Resource.getTexture("Interface/health_icon.png"));

        Image hungerImage = new Image();
        hungerImage.setSizeInBlocks(0.4f, 0.4f);
        hungerImage.setImg(Resource.getTexture("Interface/hunger_icon.png"));

        Image thirstImage = new Image();
        thirstImage.setSizeInBlocks(0.4f, 0.4f);
        thirstImage.setImg(Resource.getTexture("Interface/thirst_icon.png"));

        timeText = new Text();
        timeText.setSizeInBlocks(1.25f, 0.5f);
        timeText.setBackground(null);

        Layout notificationLayout = new Layout(Layout.Orientation.HORIZONTAL);
        notificationLayout.setGravity(Layout.Gravity.LEFT);
        notificationLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        notificationLayout.setSizeInBlocks(8.8f, 0.5f);

        attackButton = new Button("ATTACK_BUTTON", 0, 0, 0, 0, Skins.game_button_up, Skins.game_button_down);
        attackButton.setSizeInBlocks(1.5f, 1.5f);

        dropButton = new Button("DROP_BUTTON", 0, 0, 0, 0, Skins.game_button_up, Skins.game_button_down);
        dropButton.setSizeInBlocks(1.5f, 1.5f);

        interactionButton = new Button("INTERACTION_BUTTON", 0, 0, 0, 0, Skins.game_button_up, Skins.game_button_down);
        interactionButton.setSizeInBlocks(1.5f, 1.5f);
        interactionButton.setTranslationX(0.125f);

        Layout buttonsLayout = new Layout(Layout.Orientation.VERTICAL);
        buttonsLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        buttonsLayout.setGravity(Layout.Gravity.TOP);

        Layout BtnLyt1 = new Layout(Layout.Orientation.HORIZONTAL);
        BtnLyt1.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        BtnLyt1.setGravity(Layout.Gravity.LEFT);
        BtnLyt1.addElements(attackButton, interactionButton);

        buttonsLayout.addElements(BtnLyt1, dropButton);

        consoleOpenButton = new Button("CONSOLE_OPEN_BUTTON", 0, 0, 0, 0);
        consoleOpenButton.setSizeInBlocks(0.5f, 0.5f);
        consoleOpenButton.setIcon(Resource.getTexture("Interface/console_button_0.png"));

        craftingButton = new Button("CRAFTING_MENU_OPEN_BUTTON", 0, 0, 0, 0);
        craftingButton.setSizeInBlocks(1f, 1f);
        craftingButton.setIcon(Resource.getTexture("Interface/crafting_button_0.png"));

        pauseButton = new Button("PAUSE_BUTTON", 0, 0, 0, 0);
        pauseButton.setSizeInBlocks(0.5f, 0.5f);
        pauseButton.setIcon(Resource.getTexture("Interface/pause_icon.png"));

        inventoryOpenButton = new Button("INVENTORY_OPEN_BUTTON", 0, 0, 0, 0);
        inventoryOpenButton.setSizeInBlocks(1f, 1f);
        inventoryOpenButton.setIcon(Resource.getTexture("Interface/extraButton_0.png"));

        mainLayout = new Layout(Layout.Orientation.VERTICAL);
        mainLayout.setGravity(Layout.Gravity.TOP);
        mainLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        mainLayout.setSizeInBlocks(16f, SCREEN_HEIGHT / BLOCK_SIZE);

        separatorLayout0 = new Layout(Layout.Orientation.VERTICAL);
        separatorLayout0.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        separatorLayout0.setSizeInBlocks(16f, SCREEN_HEIGHT / BLOCK_SIZE - 6.5f);

        midLayout = new Layout(Layout.Orientation.HORIZONTAL);
        midLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        midLayout.setSizeInBlocks(16, 4.5f);

        Layout bottomLayout = new Layout(Layout.Orientation.HORIZONTAL);
        bottomLayout.setGravity(Layout.Gravity.LEFT);
        bottomLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);

        Layout separatorLayout1 = new Layout(Layout.Orientation.HORIZONTAL);
        separatorLayout1.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        separatorLayout1.setSizeInBlocks(8.875f, 1f);

        headerBar.addElements(pauseButton, healthText, healthImage, hungerText, hungerImage, thirstText, thirstImage, notificationLayout, timeText, consoleOpenButton);

        midLayout.addElements(joystick, separatorLayout1, buttonsLayout);
        bottomLayout.addElements(hotbar, inventoryOpenButton, craftingButton);

        craftingButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                CRAFTING_INTERFACE.open();
            }
        });
        consoleOpenButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                CONSOLE_INTERFACE.open();
            }
        });
        pauseButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                PAUSE_INTERFACE.open();
            }
        });
        inventoryOpenButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                INVENTORY_INTERFACE.open();
            }
        });

        mainLayout.addElements(headerBar, separatorLayout0, midLayout, bottomLayout);
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void resize() {
        mainLayout.setSizeInBlocks(WIDTH_IN_BLOCKS, HEIGHT_IN_BLOCKS);
        separatorLayout0.setSizeInBlocks(WIDTH_IN_BLOCKS, HEIGHT_IN_BLOCKS - 6.5f);
        mainLayout.resize();
    }

    public void update() {
        mainLayout.hide(isAnyInterfaceOpen());
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            midLayout.hide(true);
        }
        mainLayout.update(INTERFACE_CAMERA);
        healthText.setText((int) PLAYER.health + "%");
        hungerText.setText((int) PLAYER.hunger + "%");
        thirstText.setText((int) PLAYER.thirst + "%");
        isTouched = consoleOpenButton.isTouched() || dropButton.isTouched() || attackButton.isTouched() || interactionButton.isTouched() || joystick.isTouched() || hotbar.isTouched();
        Interfaces.updateInterfaces();
        timeText.setText(getWorld().getTime().getTimeString());
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
                "\n Dimension name : " + getWorld().getWorldName() +
                "\n World time : " + getWorld().getTime().getTimeString() +
                "\n FPS : " + Gdx.graphics.getFramesPerSecond() +
                "\n " + (Gdx.app.getJavaHeap() + Gdx.app.getNativeHeap()) / 1024 / 1024 + " Mb" +
                "\n Camera X : " + GAME_CAMERA.X +
                "\n Camera Y : " + GAME_CAMERA.Y +
                "\n UI_ELEMENTS Count : " + Elements.UI_ELEMENTS.size();
        String Player = "\n Hunger:" + PLAYER.hunger + "/20" +
                "\n X : " + PLAYER.getCurrentTileX() +
                "\n Y : " + PLAYER.getCurrentTileY();
        this.debug.setColor(0f, 0f, 0f, 1f);
        this.debug.draw(BATCH, Main + "\n Player \n" + Player, INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - SCREEN_WIDTH / 16 + INTERFACE_CAMERA.Y);
        this.debug.setColor(0.25f, 0.25f, 1f, 1f);
    }
}
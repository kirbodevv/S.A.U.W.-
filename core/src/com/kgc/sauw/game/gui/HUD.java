package com.kgc.sauw.game.gui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.Version;
import com.kgc.sauw.core.callbacks.Callback;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.gui.OnClickListener;
import com.kgc.sauw.core.gui.elements.*;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.sauw.core.utils.Resource;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.core.graphic.Graphic.*;
import static com.kgc.sauw.game.gui.Interfaces.*;

public class HUD {
    public Hotbar hotbar;

    private Button interactionButton;
    private Button dropButton;
    private Button attackButton;
    private Button consoleOpenButton;
    private Button pauseButton;
    private Button craftingButton;
    private Button inventoryOpenButton;
    public Joystick joystick;

    private final BitmapFont debug;
    private boolean isTouched;

    public BitmapFont log = new BitmapFont(Gdx.files.internal("ttf.fnt"));

    public int r = 0, g = 0, b = 0;

    private final Layout mainLayout;
    private final Layout separatorLayout0;
    private final TextView healthTextView;
    private final TextView hungerTextView;
    private final TextView thirstTextView;
    private final TextView timeTextView;
    private final Layout midLayout;


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

        healthTextView = new TextView();
        healthTextView.setSizeInBlocks(1.25f, 0.5f);
        healthTextView.setTranslationY(0.125f);
        healthTextView.setBackground(null);
        healthTextView.setScalable(false);

        hungerTextView = new TextView();
        hungerTextView.setSizeInBlocks(1.25f, 0.5f);
        hungerTextView.setBackground(null);
        hungerTextView.setScalable(false);

        thirstTextView = new TextView();
        thirstTextView.setSizeInBlocks(1.25f, 0.5f);
        thirstTextView.setBackground(null);
        thirstTextView.setScalable(false);

        Image healthImage = new Image();
        healthImage.setSizeInBlocks(0.4f, 0.4f);
        healthImage.setImg(Resource.getTexture("Interface/health_icon.png"));

        Image hungerImage = new Image();
        hungerImage.setSizeInBlocks(0.4f, 0.4f);
        hungerImage.setImg(Resource.getTexture("Interface/hunger_icon.png"));

        Image thirstImage = new Image();
        thirstImage.setSizeInBlocks(0.4f, 0.4f);
        thirstImage.setImg(Resource.getTexture("Interface/thirst_icon.png"));

        timeTextView = new TextView();
        timeTextView.setSizeInBlocks(1.25f, 0.5f);
        timeTextView.setBackground(null);

        Layout notificationLayout = new Layout(Layout.Orientation.HORIZONTAL);
        notificationLayout.setGravity(Layout.Gravity.LEFT);
        notificationLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        notificationLayout.setSizeInBlocks(8.3f, 0.5f);

        attackButton = new Button("ATTACK_BUTTON", 0, 0, 0, 0, Skins.game_button_up, Skins.game_button_down);
        attackButton.setSizeInBlocks(1.5f, 1.5f);

        dropButton = new Button("DROP_BUTTON", 0, 0, 0, 0, Skins.game_button_up, Skins.game_button_down);
        dropButton.setSizeInBlocks(1.5f, 1.5f);

        interactionButton = new Button("INTERACTION_BUTTON", 0, 0, 0, 0, Skins.game_button_up, Skins.game_button_down);
        interactionButton.setSizeInBlocks(1.5f, 1.5f);
        interactionButton.setTranslationX(0.125f);
        interactionButton.addEventListener(new OnClickListener() {
            @Override
            public void onClick() {
                Callback.executeInteractionButtonClickedCallback();
            }
        });


        Layout buttonsLayout = new Layout(Layout.Orientation.VERTICAL);
        buttonsLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        buttonsLayout.setGravity(Layout.Gravity.TOP);

        Layout BtnLyt1 = new Layout(Layout.Orientation.HORIZONTAL);
        BtnLyt1.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        BtnLyt1.setGravity(Layout.Gravity.LEFT);
        BtnLyt1.addElements(attackButton, interactionButton);

        buttonsLayout.addElements(BtnLyt1, dropButton);

        consoleOpenButton = new Button("CONSOLE_OPEN_BUTTON", 0, 0, 0, 0);
        consoleOpenButton.setSizeInBlocks(0.75f, 0.75f);
        consoleOpenButton.setTranslationY(-0.125f);
        consoleOpenButton.setIcon(Resource.getTexture("Interface/console_button_0.png"));

        craftingButton = new Button("CRAFTING_MENU_OPEN_BUTTON", 0, 0, 0, 0);
        craftingButton.setSizeInBlocks(1f, 1f);
        craftingButton.setIcon(Resource.getTexture("Interface/crafting_button_0.png"));

        pauseButton = new Button("PAUSE_BUTTON", 0, 0, 0, 0);
        pauseButton.setSizeInBlocks(0.75f, 0.75f);
        pauseButton.setTranslationY(-0.125f);
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

        headerBar.addElements(pauseButton, healthTextView, healthImage, hungerTextView, hungerImage, thirstTextView, thirstImage, notificationLayout, timeTextView, consoleOpenButton);

        midLayout.addElements(joystick, separatorLayout1, buttonsLayout);
        bottomLayout.addElements(hotbar, inventoryOpenButton, craftingButton);

        craftingButton.addEventListener(new OnClickListener() {
            @Override
            public void onClick() {
                CRAFTING_INTERFACE.open();
            }
        });
        consoleOpenButton.addEventListener(new OnClickListener() {
            @Override
            public void onClick() {
                CONSOLE_INTERFACE.open();
            }
        });
        pauseButton.addEventListener(new OnClickListener() {
            @Override
            public void onClick() {
                PAUSE_INTERFACE.open();
            }
        });
        inventoryOpenButton.addEventListener(new OnClickListener() {
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
            pauseButton.hide(true);
            consoleOpenButton.hide(true);
        }
        mainLayout.update(INTERFACE_CAMERA);
        healthTextView.setText((int) PLAYER.health + "%");
        hungerTextView.setText((int) PLAYER.hunger + "%");
        thirstTextView.setText((int) PLAYER.thirst + "%");
        isTouched = consoleOpenButton.isTouched() || dropButton.isTouched() || attackButton.isTouched() || interactionButton.isTouched() || joystick.isTouched() || hotbar.isTouched();
        Interfaces.updateInterfaces();
        timeTextView.setText(getWorld().getTime().getTimeString());
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
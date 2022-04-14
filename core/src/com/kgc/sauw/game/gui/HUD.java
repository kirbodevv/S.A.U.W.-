package com.kgc.sauw.game.gui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.Version;
import com.kgc.sauw.core.callbacks.Callback;
import com.kgc.sauw.core.gui.OnClickListener;
import com.kgc.sauw.core.gui.elements.*;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.sauw.core.utils.DebugStringBuilder;
import com.kgc.sauw.game.mechanics.Building;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.core.graphic.Graphic.*;
import static com.kgc.sauw.core.gui.Interfaces.isAnyInterfaceOpen;
import static com.kgc.sauw.game.gui.GameInterfaces.*;

public class HUD {
    public Hotbar hotbar;

    private final Button interactionButton;
    private final Button dropButton;
    private final Button attackButton;
    public Joystick joystick;

    private final BitmapFont debug;
    private boolean isTouched;

    public int r = 0, g = 0, b = 0;

    private final Layout separatorLayout1;
    private final Layout separatorBottomBar;
    private final Layout bottomBar;
    private final Layout midLayout;

    private final TextView healthTextView;
    private final TextView hungerTextView;
    private final TextView thirstTextView;
    private final TextView timeTextView;

    public HUD() {
        debug = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        debug.getData().setScale(SCREEN_WIDTH / 64 / debug.getCapHeight());

        joystick = new Joystick(Resource.getTexture("interface/Joystick_0.png"), Resource.getTexture("interface/Joystick_1.png"));
        joystick.setSizeInBlocks(3f, 3f);
        joystick.setTranslationX(0.5f);

        hotbar = new Hotbar();

        bottomBar = new Layout(Layout.Orientation.HORIZONTAL);
        bottomBar.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        bottomBar.setSizeInBlocks(16f, 0.75f);
        bottomBar.setGravity(Layout.Gravity.LEFT);
        bottomBar.setBackground(Skins.round_up);

        separatorBottomBar = new Layout(Layout.Orientation.HORIZONTAL);
        separatorBottomBar.setGravity(Layout.Gravity.LEFT);
        separatorBottomBar.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);

        /*Player params*/
        healthTextView = new TextView();
        healthTextView.setSizeInBlocks(1.25f, 0.5f);
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
        healthImage.setImg(Resource.getTexture("interface/health_icon.png"));

        Image hungerImage = new Image();
        hungerImage.setSizeInBlocks(0.4f, 0.4f);
        hungerImage.setImg(Resource.getTexture("interface/hunger_icon.png"));

        Image thirstImage = new Image();
        thirstImage.setSizeInBlocks(0.4f, 0.4f);
        thirstImage.setImg(Resource.getTexture("interface/thirst_icon.png"));
        /*Player params end*/

        timeTextView = new TextView();
        timeTextView.setSizeInBlocks(1.25f, 0.5f);
        timeTextView.setBackground(null);

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
                Callback.executeInteractionButtonClickedCallbacks();
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


        Button craftingButton = new Button("CRAFTING_MENU_OPEN_BUTTON", 0, 0, 0, 0);
        craftingButton.setSizeInBlocks(0.75f, 0.75f);
        craftingButton.setIcon(Resource.getTexture("interface/crafting_button_0.png"));

        Button buildButton = new Button("building_button", 0, 0, 0, 0);
        buildButton.setSizeInBlocks(0.75f, 0.75f);
        buildButton.setIcon(Resource.getTexture("item/hammer.png"));
        buildButton.addEventListener(() -> {
            if (!Building.building) {
                Building.startBuilding();
                buildButton.setIcon(Resource.getTexture("interface/closeButton.png"));
            } else {
                Building.stopBuilding();
                buildButton.setIcon(Resource.getTexture("item/hammer.png"));
            }

        });

        Button pauseButton = new Button("PAUSE_BUTTON", 0, 0, 0, 0);
        pauseButton.setSizeInBlocks(0.75f, 0.75f);
        pauseButton.setIcon(Resource.getTexture("interface/pause_icon.png"));

        Button inventoryOpenButton = new Button("INVENTORY_OPEN_BUTTON", 0, 0, 0, 0);
        inventoryOpenButton.setSizeInBlocks(0.75f, 0.75f);
        inventoryOpenButton.setIcon(Resource.getTexture("interface/extraButton_0.png"));

        midLayout = new Layout(Layout.Orientation.HORIZONTAL);
        midLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);

        separatorLayout1 = new Layout(Layout.Orientation.HORIZONTAL);
        separatorLayout1.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        separatorLayout1.setSizeInBlocks(8.875f, 1f);

        bottomBar.addElements(
                pauseButton,
                healthTextView, healthImage,
                hungerTextView, hungerImage,
                thirstTextView, thirstImage,
                hotbar, inventoryOpenButton,
                buildButton, craftingButton,
                separatorBottomBar, timeTextView);

        midLayout.addElements(joystick, separatorLayout1, buttonsLayout);

        craftingButton.addEventListener(new OnClickListener() {
            @Override
            public void onClick() {
                CRAFTING_INTERFACE.open();
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
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void resize() {
        separatorLayout1.setSizeInBlocks(WIDTH_IN_BLOCKS - 7f, 1);
        midLayout.setPositionInBlocks(0, 1f);
        bottomBar.setSizeInBlocks(WIDTH_IN_BLOCKS, 0.75f);
        separatorBottomBar.setSizeInBlocks(WIDTH_IN_BLOCKS - 15.95f, 0.5f);
        bottomBar.resize();
        midLayout.resize();
    }

    public void update() {
        midLayout.hide(isAnyInterfaceOpen());
        bottomBar.hide(isAnyInterfaceOpen());
        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            midLayout.hide(true);
        midLayout.update(INTERFACE_CAMERA);
        bottomBar.update(INTERFACE_CAMERA);
        healthTextView.setText((int) PLAYER.health + "%");
        hungerTextView.setText((int) PLAYER.hunger.hunger + "%");
        thirstTextView.setText((int) PLAYER.thirst + "%");
        isTouched = dropButton.isTouched() || attackButton.isTouched() || interactionButton.isTouched() || joystick.isTouched() || hotbar.isTouched();
        timeTextView.setText(getWorld().getTime().getTimeString());
    }

    public void render(boolean debug) {
        float alpha = COLOR_ALPHA;
        COLOR_ALPHA = 0.75f;
        INTERFACE_CAMERA.update(BATCH);
        bottomBar.render(BATCH, INTERFACE_CAMERA);
        midLayout.render(BATCH, INTERFACE_CAMERA);
        COLOR_ALPHA = alpha;
        if (debug)
            drawDebugString();
    }

    private final DebugStringBuilder debugStringBuilder = new DebugStringBuilder();

    public void drawDebugString() {
        String debug = debugStringBuilder
                .header("Game")
                .parameter("Version", Version.VERSION)
                .parameter("FPS", Gdx.graphics.getFramesPerSecond())
                .header("World")
                .parameter("Dimension name", getWorld().getName())
                .parameter("Time", getWorld().getTime().getTimeString())
                .header("Camera")
                .parameter("X", GAME_CAMERA.X)
                .parameter("Y", GAME_CAMERA.Y)
                .header("Player")
                .parameter("X", PLAYER.getCurrentTileX())
                .parameter("Z", PLAYER.getCurrentTileZ())
                .build();

        BITMAP_FONT.draw(BATCH, debug, INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - SCREEN_WIDTH / 16 + INTERFACE_CAMERA.Y);
    }
}
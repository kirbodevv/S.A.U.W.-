package com.kgc.sauw.game.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.kgc.sauw.UpdatesChecker;
import com.kgc.sauw.Version;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.graphic.Graphic;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.Image;
import com.kgc.sauw.core.gui.elements.Layout;
import com.kgc.sauw.core.gui.elements.TextView;
import com.kgc.sauw.core.sound.Music;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.core.world.WorldLoader;
import com.kgc.sauw.game.MainGame;
import com.kgc.sauw.game.gui.interfaces.SelectWorldInterface;

import static com.kgc.sauw.core.graphic.Graphic.*;
import static com.kgc.sauw.game.gui.Interfaces.UPDATES_INTERFACE;

public class MenuScreen implements Screen {
    private final Button startButton;
    private final Button settingsButton;
    private final Button modsButton;
    private final Button exitButton;
    private final Image sauwLogo;
    private final Button update;
    private final Layout coinsLayout;

    public int SAUW_coins;

    private final SelectWorldInterface selectWorldInterface;

    public SettingsScreen SettingsScreen;
    public ModsScreen ModsScreen;

    private final Layout mainLayout;

    public MenuScreen() {
        SAUW_coins = MainGame.getData().getInt("SAUW_Coins");

        WorldLoader.updateWorldsList();

        mainLayout = new Layout(Layout.Orientation.VERTICAL);
        mainLayout.setId("MENU_MAIN_LAYOUT");
        mainLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        mainLayout.setSizeInBlocks(WIDTH_IN_BLOCKS, HEIGHT_IN_BLOCKS);
        mainLayout.setGravity(Layout.Gravity.TOP);

        /*SettingsScreen = new SettingsScreen(this);
        ModsScreen = new ModsScreen(this);*/

        sauwLogo = new Image();
        sauwLogo.setImg(Resource.getTexture("Interface/SAUW_Logo.png"));

        update = new Button("sauw.button.menu_screen.update", 0, 0, 0, 0);
        update.setIcon(Resource.getTexture("Interface/update_icon.png"));
        update.addEventListener(UPDATES_INTERFACE::open);
        startButton = new Button("sauw.button.menu_screen.start", 0, 0, 0, 0);
        settingsButton = new Button("sauw.button.menu_screen.settings", 0, 0, 0, 0);
        modsButton = new Button("sauw.button.menu_screen.mods", 0, 0, 0, 0);
        exitButton = new Button("sauw.button.menu_screen.exit", 0, 0, 0, 0);

        sauwLogo.setSizeInBlocks(6, 3);
        startButton.setSizeInBlocks(6, 1);
        settingsButton.setSizeInBlocks(6, 1);
        modsButton.setSizeInBlocks(6, 1);
        exitButton.setSizeInBlocks(6, 1);
        update.setSizeInBlocks(1, 1);

        startButton.setMarginBottom(0.05f);
        settingsButton.setMarginBottom(0.05f);
        modsButton.setMarginBottom(0.05f);
        exitButton.setMarginBottom(0.05f);

        selectWorldInterface = new SelectWorldInterface();


        mainLayout.addElements(sauwLogo, startButton, settingsButton, modsButton, exitButton);

        startButton.addEventListener(selectWorldInterface::open);
        /*settingsButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                MainGame.getGame().setScreen(SettingsScreen);
            }
        });
        modsButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                MainGame.getGame().setScreen(ModsScreen);
            }
        });*/
        exitButton.addEventListener(() -> Gdx.app.exit());

        startButton.setDefaultText();
        settingsButton.setDefaultText();
        modsButton.setDefaultText();
        exitButton.setDefaultText();

        coinsLayout = new Layout(Layout.Orientation.HORIZONTAL);
        coinsLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        coinsLayout.setSizeInBlocks(3, 1f);
        coinsLayout.setStandardBackground(true);

        Image coinIcon = new Image();
        coinIcon.setSizeInBlocks(0.75f, 0.75f);
        coinIcon.setImg(Resource.getTexture("SAUW_Coin.png"));
        coinIcon.setTranslationX(0.125f);
        coinIcon.setMarginRight(0.125f);

        TextView coinsTextView = new TextView();
        coinsTextView.setStandardBackground(false);
        coinsTextView.setSizeInBlocks(1, 0.75f);
        coinsTextView.setText(SAUW_coins + "");

        coinsLayout.addElements(coinIcon, coinsTextView);
        coinsLayout.setPositionInBlocks(0.25f, HEIGHT_IN_BLOCKS - 1f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Music.setVolume(Settings.musicVolume);
        Music.play("music/main.mp3");
        Music.setLooping(true);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        BATCH.begin();

        MENU_CAMERA.update(BATCH);

        BATCH.setColor(0.6f, 0.6f, 0.6f, 1);
        drawBackground();
        BATCH.setColor(1, 1, 1, 1);

        mainLayout.hide(false);
        coinsLayout.hide(false);
        update.hide(!UpdatesChecker.newVersionAvailable(Version.CODE_VERSION));
        if (!selectWorldInterface.isOpen) {
            coinsLayout.update(MENU_CAMERA);
            mainLayout.update(MENU_CAMERA);
            update.update(MENU_CAMERA);
            coinsLayout.render(BATCH, MENU_CAMERA);
            mainLayout.render(BATCH, MENU_CAMERA);
            update.render(BATCH, MENU_CAMERA);
        }

        selectWorldInterface.update();
        selectWorldInterface.render();

        BATCH.end();
    }

    private void drawBackground() {
        for (int x = 0; x < WIDTH_IN_BLOCKS; x++) {
            for (int y = 0; y < HEIGHT_IN_BLOCKS; y++) {
                BATCH.draw(Resource.getTexture("Blocks/grass_1.png"), BLOCK_SIZE * x, BLOCK_SIZE * y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        Graphic.resize(width, height);
        mainLayout.setSizeInBlocks(WIDTH_IN_BLOCKS, HEIGHT_IN_BLOCKS);
        mainLayout.resize();
        coinsLayout.resize();
        update.resize();
        coinsLayout.setPositionInBlocks(0.25f, HEIGHT_IN_BLOCKS - 1.25f);
        update.setPositionInBlocks(WIDTH_IN_BLOCKS - 1.25f, HEIGHT_IN_BLOCKS - 1.25f);
        selectWorldInterface.resize();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
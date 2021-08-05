package com.kgc.sauw.game.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.WorldLoader;
import com.kgc.sauw.core.gui.elements.*;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.languages.Languages;
import com.kgc.sauw.game.MainGame;
import com.kgc.sauw.core.graphic.Graphic;
import com.kgc.sauw.game.gui.interfaces.SelectWorldInterface;
import com.kgc.sauw.resource.Music;

import static com.kgc.sauw.core.graphic.Graphic.*;

public class MenuScreen implements Screen {
    private final Button startButton;
    private final Button settingsButton;
    private final Button modsButton;
    private final Button exitButton;
    private final Image sauwLogo;
    private final Layout coinsLayout;

    public int SAUW_coins;

    private final SelectWorldInterface selectWorldInterface;

    private final Music music;

    public SettingsScreen SettingsScreen;
    public ModsScreen ModsScreen;

    private final Layout mainLayout;

    public MenuScreen() {
        SAUW_coins = MainGame.getData().getInt("SAUW_Coins");

        WorldLoader.updateWorldsList();

        mainLayout = new Layout(Layout.Orientation.VERTICAL);
        mainLayout.setID("MENU_MAIN_LAYOUT");
        mainLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        mainLayout.setSizeInBlocks(WIDTH_IN_BLOCKS, HEIGHT_IN_BLOCKS);
        mainLayout.setGravity(Layout.Gravity.TOP);

        /*SettingsScreen = new SettingsScreen(this);
        ModsScreen = new ModsScreen(this);*/

        sauwLogo = new Image(0, 0, 0, 0);
        sauwLogo.setImg(Resource.getTexture("Interface/SAUW_Logo.png"));

        startButton = new Button("MENU_SCREEN_START_BUTTON", 0, 0, 0, 0);
        settingsButton = new Button("MENU_SCREEN_SETTINGS_BUTTON", 0, 0, 0, 0);
        modsButton = new Button("MENU_SCREEN_MODS_BUTTON", 0, 0, 0, 0);
        exitButton = new Button("MENU_SCREEN_EXIT_BUTTON", 0, 0, 0, 0);

        sauwLogo.setSizeInBlocks(6, 3);
        startButton.setSizeInBlocks(6, 1);
        settingsButton.setSizeInBlocks(6, 1);
        modsButton.setSizeInBlocks(6, 1);
        exitButton.setSizeInBlocks(6, 1);

        startButton.setMarginBottom(0.05f);
        settingsButton.setMarginBottom(0.05f);
        modsButton.setMarginBottom(0.05f);
        exitButton.setMarginBottom(0.05f);

        selectWorldInterface = new SelectWorldInterface();


        mainLayout.addElements(sauwLogo, startButton, settingsButton, modsButton, exitButton);

        startButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                selectWorldInterface.open();
            }
        });
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
        exitButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                Gdx.app.exit();
            }
        });

        startButton.setText(Languages.getString("startGame"));
        settingsButton.setText(Languages.getString("settings") + "(Закрыто на ремонт)");
        modsButton.setText(Languages.getString("mods") + "(Закрыто на ремонт)");
        exitButton.setText(Languages.getString("exit"));

        coinsLayout = new Layout(Layout.Orientation.HORIZONTAL);
        coinsLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        coinsLayout.setSizeInBlocks(3, 1f);
        coinsLayout.setStandardBackground(true);

        Image coinIcon = new Image(0, 0, 0, 0);
        coinIcon.setSizeInBlocks(0.75f, 0.75f);
        coinIcon.setImg(Resource.getTexture("SAUW_Coin.png"));
        coinIcon.setTranslationX(0.125f);
        coinIcon.setMarginRight(0.125f);

        Text coinsText = new Text();
        coinsText.setStandardBackground(false);
        coinsText.setSizeInBlocks(1, 0.75f);
        coinsText.setText(SAUW_coins + "");

        coinsLayout.addElements(coinIcon, coinsText);
        coinsLayout.setPositionInBlocks(0.25f, HEIGHT_IN_BLOCKS - 1f);

        music = Music.getMusic();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        music.setMusicVolume(Settings.musicVolume);
        music.update(true);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        BATCH.begin();

        MENU_CAMERA.update(BATCH);

        BATCH.setColor(0.6f, 0.6f, 0.6f, 1);
        drawBackground();
        BATCH.setColor(1, 1, 1, 1);

        mainLayout.hide(false);
        coinsLayout.hide(false);
        if (!selectWorldInterface.isOpen) {
            coinsLayout.update(MENU_CAMERA);
            mainLayout.update(MENU_CAMERA);
            coinsLayout.render(BATCH, MENU_CAMERA);
            mainLayout.render(BATCH, MENU_CAMERA);
        }

        selectWorldInterface.update(false);
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
        coinsLayout.setPositionInBlocks(0.25f, HEIGHT_IN_BLOCKS - 1.25f);
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
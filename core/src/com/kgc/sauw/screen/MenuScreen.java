package com.kgc.sauw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.kgc.sauw.WorldLoader;
import com.kgc.sauw.game.MainGame;
import com.kgc.sauw.graphic.Graphic;
import com.kgc.sauw.gui.elements.Button;
import com.kgc.sauw.gui.elements.Layout;
import com.kgc.sauw.gui.interfaces.SelectWorldInterface;
import com.kgc.sauw.resource.Music;
import org.json.JSONObject;

import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class MenuScreen implements Screen {
    Button startButton;
    Button settingsButton;
    Button modsButton;
    Button exitButton;

    boolean StartGameMenu = false;
    int SAUW_coins = 0;

    SelectWorldInterface selectWorldInterface;

    JSONObject data;

    private final Music music;

    public SettingsScreen SettingsScreen;
    public ModsScreen ModsScreen;

    private Layout mainLayout;

    public MenuScreen() {
        try {
            FileHandle data = Gdx.files.external("S.A.U.W./User/data.json");
            String result = data.readString();
            this.data = new JSONObject(result);

            SAUW_coins = this.data.getInt("SAUW_Coins");
        } catch (Exception e) {
            Gdx.app.log("error", e.toString());
        }
        final MainGame game = MainGame.getGame();

        WorldLoader.updateWorldsList();

        SettingsScreen = new SettingsScreen(game, TEXTURES, this);
        ModsScreen = new ModsScreen(game, TEXTURES, this);
        startButton = new Button("MENU_SCREEN_START_BUTTON", BLOCK_SIZE * 5, SCREEN_HEIGHT - BLOCK_SIZE * 5 + SCREEN_WIDTH / 128, BLOCK_SIZE * 6, BLOCK_SIZE);
        startButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                selectWorldInterface.open();
            }
        });
        settingsButton = new Button("MENU_SCREEN_SETTINGS_BUTTON", BLOCK_SIZE * 5, SCREEN_HEIGHT - BLOCK_SIZE * 6, BLOCK_SIZE * 6, BLOCK_SIZE);
        settingsButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                game.setScreen(SettingsScreen);
            }
        });
        modsButton = new Button("MENU_SCREEN_MODS_BUTTON", BLOCK_SIZE * 5, SCREEN_HEIGHT - BLOCK_SIZE * 7 - SCREEN_WIDTH / 128, BLOCK_SIZE * 6, BLOCK_SIZE);
        modsButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                game.setScreen(ModsScreen);
            }
        });
        exitButton = new Button("MENU_SCREEN_EXIT_BUTTON", BLOCK_SIZE * 5, SCREEN_HEIGHT - BLOCK_SIZE * 8 - SCREEN_WIDTH / 128 * 2, BLOCK_SIZE * 6, BLOCK_SIZE);
        exitButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                Gdx.app.exit();
            }
        });

        selectWorldInterface = new SelectWorldInterface();
        selectWorldInterface.open();

        startButton.setText(LANGUAGES.getString("startGame"));
        settingsButton.setText(LANGUAGES.getString("settings"));
        modsButton.setText(LANGUAGES.getString("mods"));
        exitButton.setText(LANGUAGES.getString("exit"));

        music = Music.getMusic();
    }

    public void loadGame(String worldName) {
        SettingsScreen.dispose();
        dispose();
        //game.setScreen(new SAUW(worldName));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        music.setMusicVolume(SETTINGS.musicVolume);
        music.update(true);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        BATCH.begin();
        BATCH.setColor(0.6f, 0.6f, 0.6f, 1);
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < Graphic.SCREEN_HEIGHT / BLOCK_SIZE; y++) {
                BATCH.draw(TEXTURES.grass0, BLOCK_SIZE * x, BLOCK_SIZE * y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
        BATCH.setColor(1, 1, 1, 1);
        BATCH.draw(TEXTURES.logo, MENU_CAMERA.X + BLOCK_SIZE * 5, MENU_CAMERA.Y + SCREEN_HEIGHT - BLOCK_SIZE * 4, BLOCK_SIZE * 6, BLOCK_SIZE * 3);
        if (!selectWorldInterface.isOpen) {
            startButton.update(MENU_CAMERA);
            settingsButton.update(MENU_CAMERA);
            modsButton.update(MENU_CAMERA);
            exitButton.update(MENU_CAMERA);
            startButton.render(BATCH, MENU_CAMERA);
            settingsButton.render(BATCH, MENU_CAMERA);
            modsButton.render(BATCH, MENU_CAMERA);
            exitButton.render(BATCH, MENU_CAMERA);
            BATCH.draw(TEXTURES.SAUWCoin, MENU_CAMERA.X + BLOCK_SIZE / 2f, MENU_CAMERA.Y + SCREEN_HEIGHT - BLOCK_SIZE, BLOCK_SIZE / 2f, BLOCK_SIZE / 2f);
            BITMAP_FONT.getData().setScale(BLOCK_SIZE / 2 / BITMAP_FONT_CAP_HEIGHT);
            BITMAP_FONT.draw(BATCH, SAUW_coins + "", MENU_CAMERA.X + BLOCK_SIZE + SCREEN_WIDTH / 64, MENU_CAMERA.Y + SCREEN_HEIGHT - BLOCK_SIZE / 2f);
        }
        selectWorldInterface.update(false);

        selectWorldInterface.render();

        BATCH.end();
    }

    @Override
    public void resize(int width, int height) {

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
package com.kgc.sauw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.game.MainGame;
import com.kgc.sauw.game.SAUW;
import com.kgc.sauw.resource.Music;
import com.kgc.sauw.gui.elements.Button;
import com.kgc.sauw.gui.interfaces.CreateNewWorldInterface;
import org.json.JSONObject;

import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class MenuScreen implements Screen {
    MainGame game;
    int w, h;
    int WIDTH;
    Button startButton;
    Button settingsButton;
    Button modsButton;
    Button exitButton;

    Button closeButton;
    boolean StartGameMenu = false;
    int SAUW_coins = 0;
    Button sel_0;
    Button sel_1;
    Button sel_2;
    Button createNewWorld;
    Button up;
    Button down;
    CreateNewWorldInterface createWorldInterface;
    JSONObject data;
    private Music music;
    public SettingsScreen SettingsScreen;
    public ModsScreen ModsScreen;
    BitmapFont bf = new BitmapFont(Gdx.files.internal("ttf.fnt"));

    private int worldSelIndex = 0;

    public MenuScreen(final MainGame game) {
        WIDTH = Gdx.graphics.getWidth();
        FileHandle settings = Gdx.files.external("S.A.U.W./User/settings.json");
        if (!settings.exists()) {
            try {
                settings.mkdirs();
                settings.writeString(Gdx.files.internal("json/settings.json").readString(), false);
            } catch (Exception e) {

            }
        }
        try {
            FileHandle data = Gdx.files.external("S.A.U.W./User/data.json");
            String result = data.readString();
            this.data = new JSONObject(result);

            SAUW_coins = this.data.getInt("SAUW_Coins");
        } catch (Exception e) {
            Gdx.app.log("error", e.toString());
        }
        this.game = game;
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        SettingsScreen = new SettingsScreen(game, TEXTURES, this);
        ModsScreen = new ModsScreen(game, TEXTURES, this);
        startButton = new Button("MENU_SCREEN_START_BUTTON", BLOCK_SIZE * 5, h - BLOCK_SIZE * 5 + w / 128, BLOCK_SIZE * 6, BLOCK_SIZE);
        startButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                StartGameMenu = true;
            }
        });
        settingsButton = new Button("MENU_SCREEN_SETTINGS_BUTTON", BLOCK_SIZE * 5, h - BLOCK_SIZE * 6, BLOCK_SIZE * 6, BLOCK_SIZE);
        settingsButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                game.setScreen(SettingsScreen);
            }
        });
        modsButton = new Button("MENU_SCREEN_MODS_BUTTON", BLOCK_SIZE * 5, h - BLOCK_SIZE * 7 - w / 128, BLOCK_SIZE * 6, BLOCK_SIZE);
        modsButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                game.setScreen(ModsScreen);
            }
        });
        exitButton = new Button("MENU_SCREEN_EXIT_BUTTON", BLOCK_SIZE * 5, h - BLOCK_SIZE * 8 - w / 128 * 2, BLOCK_SIZE * 6, BLOCK_SIZE);
        exitButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                Gdx.app.exit();
            }
        });

        createWorldInterface = new CreateNewWorldInterface();
        createWorldInterface.create.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                for (String name : createWorldInterface.worldNames) {
                    if (createWorldInterface.worldName.input.equals(name)) return;
                }
                loadGame(createWorldInterface.worldName.input);
            }
        });

        sel_0 = new Button("MENU_SCREEN_WORLD_SELECTOR_1", BLOCK_SIZE * 5, h - BLOCK_SIZE * 5 + w / 128, BLOCK_SIZE * 6, BLOCK_SIZE);
        sel_0.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                loadGame(createWorldInterface.worldNames[worldSelIndex]);
            }
        });
        sel_1 = new Button("MENU_SCREEN_WORLD_SELECTOR_2", BLOCK_SIZE * 5, h - BLOCK_SIZE * 6, BLOCK_SIZE * 6, BLOCK_SIZE);
        sel_1.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                loadGame(createWorldInterface.worldNames[worldSelIndex + 1]);
            }
        });
        sel_2 = new Button("MENU_SCREEN_WORLD_SELECTOR_3", BLOCK_SIZE * 5, h - BLOCK_SIZE * 7 - w / 128, BLOCK_SIZE * 6, BLOCK_SIZE);
        sel_2.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                loadGame(createWorldInterface.worldNames[worldSelIndex + 2]);
            }
        });
        closeButton = new Button("MENU_SCREEN_CLOSE_WORLD_SELECTOR", 0, (h - BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE, TEXTURES.button_left_0, TEXTURES.button_left_1);
        closeButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                StartGameMenu = false;
            }
        });

        createNewWorld = new Button("CREATE_NEW_WORLD_BUTTON", BLOCK_SIZE / 2f, BLOCK_SIZE / 2f, BLOCK_SIZE * 6, BLOCK_SIZE);
        createNewWorld.setText(LANGUAGES.getString("createNewWorld"));
        createNewWorld.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                createWorldInterface.open();
            }
        });
        HideButtonsIfNeed();
        setSelectButtonsText();
        up = new Button("MENU_SCREEN_WORLD_SELECTOR_UP_BUTTON", BLOCK_SIZE / 2f * 23, sel_0.Y, BLOCK_SIZE, BLOCK_SIZE, TEXTURES.button_up_0, TEXTURES.button_up_1);
        up.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                worldSelIndex--;
                if (worldSelIndex < 0) worldSelIndex = 0;
                HideButtonsIfNeed();
                setSelectButtonsText();
            }
        });
        down = new Button("MENU_SCREEN_WORLD_SELECTOR_DOWN_BUTTON", BLOCK_SIZE / 2f * 23, sel_2.Y, BLOCK_SIZE, BLOCK_SIZE, TEXTURES.button_down_0, TEXTURES.button_down_1);
        down.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                worldSelIndex++;
                if (worldSelIndex >= createWorldInterface.worldNames.length)
                    worldSelIndex = createWorldInterface.worldNames.length - 1;
                HideButtonsIfNeed();
                setSelectButtonsText();
            }
        });
        startButton.setText(LANGUAGES.getString("startGame"));
        settingsButton.setText(LANGUAGES.getString("settings"));
        modsButton.setText(LANGUAGES.getString("mods"));
        exitButton.setText(LANGUAGES.getString("exit"));

        music = new Music();
    }

    public void loadGame(String worldName) {
        SettingsScreen.dispose();
        dispose();
        game.setScreen(new SAUW(game, music, worldName));
    }

    public void setSelectButtonsText() {
        if (!sel_0.isHidden() && worldSelIndex < createWorldInterface.worldNames.length)
            sel_0.setText(createWorldInterface.worldNames[worldSelIndex]);
        if (!sel_1.isHidden() && worldSelIndex + 1 < createWorldInterface.worldNames.length)
            sel_1.setText(createWorldInterface.worldNames[worldSelIndex + 1]);
        if (!sel_2.isHidden() && worldSelIndex + 2 < createWorldInterface.worldNames.length)
            sel_2.setText(createWorldInterface.worldNames[worldSelIndex + 2]);
    }

    public void HideButtonsIfNeed() {
        sel_0.hide(worldSelIndex >= createWorldInterface.worldNames.length);
        sel_1.hide(worldSelIndex + 1 >= createWorldInterface.worldNames.length);
        sel_2.hide(worldSelIndex + 2 >= createWorldInterface.worldNames.length);
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
            for (int y = 0; y < SCREEN_HEIGHT / BLOCK_SIZE; y++) {
                BATCH.draw(TEXTURES.grass0, BLOCK_SIZE * x, BLOCK_SIZE * y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
        BATCH.setColor(1, 1, 1, 1);
        BATCH.draw(TEXTURES.logo, MENU_CAMERA.X + BLOCK_SIZE * 5, MENU_CAMERA.Y + h - BLOCK_SIZE * 4, BLOCK_SIZE * 6, BLOCK_SIZE * 3);
        if (!StartGameMenu) {
            startButton.update(MENU_CAMERA);
            settingsButton.update(MENU_CAMERA);
            modsButton.update(MENU_CAMERA);
            exitButton.update(MENU_CAMERA);
            startButton.render(BATCH, MENU_CAMERA);
            settingsButton.render(BATCH, MENU_CAMERA);
            modsButton.render(BATCH, MENU_CAMERA);
            exitButton.render(BATCH, MENU_CAMERA);
            BATCH.draw(TEXTURES.SAUWCoin, MENU_CAMERA.X + BLOCK_SIZE / 2f, MENU_CAMERA.Y + h - BLOCK_SIZE, BLOCK_SIZE / 2f, BLOCK_SIZE / 2f);
            bf.setScale(w / 768f);
            bf.draw(BATCH, SAUW_coins + "", MENU_CAMERA.X + BLOCK_SIZE + w / 64, MENU_CAMERA.Y + h - BLOCK_SIZE / 2f);
        } else {
            if (!createWorldInterface.isOpen) {
                sel_0.update(MENU_CAMERA);
                sel_1.update(MENU_CAMERA);
                sel_2.update(MENU_CAMERA);
                closeButton.update(MENU_CAMERA);
                createNewWorld.update(MENU_CAMERA);
                up.update(MENU_CAMERA);
                down.update(MENU_CAMERA);
            }
            createWorldInterface.update(false);

            if (!createWorldInterface.isOpen) {
                sel_0.render(BATCH, MENU_CAMERA);
                sel_1.render(BATCH, MENU_CAMERA);
                sel_2.render(BATCH, MENU_CAMERA);
                closeButton.render(BATCH, MENU_CAMERA);
                createNewWorld.render(BATCH, MENU_CAMERA);
                up.render(BATCH, MENU_CAMERA);
                down.render(BATCH, MENU_CAMERA);
            }
            createWorldInterface.render();
        }
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
